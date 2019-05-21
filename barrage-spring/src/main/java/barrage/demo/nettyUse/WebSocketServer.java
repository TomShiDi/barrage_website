package barrage.demo.nettyUse;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketServer implements IWebSocketService,HttpService{

    private static final String HN_HTTP_CODEC = "HN_HTTP_CODEC";
    private static final String HN_HTTP_AGGREGATOR = "HN_HTTP_AGGREGATOR";
    private static final String HN_HTTP_CHUNK = "HN_HTTP_CHUNK";
    private static final String HN_SERVER = "HN_LOGIC";
    private static final String WEBSOCKET_UPGRADE = "websocket";
    private static final String WEBSOCKET_CONNECTION = "Upgrade";
    private int MAX_CONTENT_LENGTH = 65536;

    private static final AttributeKey<WebSocketServerHandshaker> ATTR_HANDSHAKER = AttributeKey.newInstance("ATTR_KEY_CHANNELID");
    private static final String WEBSOCKET_URI_ROOT_PATTERN = "ws://%s:%d";
    private final String WEBSOCKET_URI_ROOT;


    private String host;
    private int port;

    private Map<ChannelId, Channel> channelMap = new ConcurrentHashMap<>();
    private ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public WebSocketServer(int port) {
        this("localhost", port);
    }

    public WebSocketServer(String host, int port) {
        this.host = host;
        this.port = port;
        WEBSOCKET_URI_ROOT = String.format(WEBSOCKET_URI_ROOT_PATTERN, host, port);
    }

    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ChannelPipeline channelPipeline = ch.pipeline();
                        channelGroup.add(ch);
                        channelMap.put(ch.id(), ch);
                        ch.closeFuture().addListener((ChannelFutureListener) future -> {
                            channelMap.remove(future.channel().id());
                        });

                        channelPipeline.addLast(HN_HTTP_CODEC, new HttpServerCodec());
                        channelPipeline.addLast(HN_HTTP_AGGREGATOR, new HttpObjectAggregator(MAX_CONTENT_LENGTH));
                        channelPipeline.addLast(HN_HTTP_CHUNK, new ChunkedWriteHandler());
                        channelPipeline.addLast(HN_SERVER, new WebSocketServerHandler(WebSocketServer.this, WebSocketServer.this));
                    }
                });
        try {
            ChannelFuture channelFuture = serverBootstrap.bind(host, port).addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    System.out.println("socket success");
                }
            }).sync();
            channelFuture.channel().closeFuture().addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    System.out.println("socket channel closed {}" + future.channel());
                }
            }).sync();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
        if (isWebSocketUpgrade(req)) { // 该请求是不是websocket upgrade请求
            System.out.println("upgrade to websocket protocol");

            String subProtocols = req.headers().get(HttpHeaderNames.SEC_WEBSOCKET_PROTOCOL);

            WebSocketServerHandshakerFactory factory = new WebSocketServerHandshakerFactory(WEBSOCKET_URI_ROOT, subProtocols, false);
            WebSocketServerHandshaker handshaker = factory.newHandshaker(req);

            if (handshaker == null) {// 请求头不合法, 导致handshaker没创建成功
                WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
            } else {
                // 响应该请求
                handshaker.handshake(ctx.channel(), req);
                // 把handshaker 绑定给Channel, 以便后面关闭连接用
                ctx.channel().attr(ATTR_HANDSHAKER).set(handshaker);// attach handshaker to this channel
            }
            return;
        }

        // TODO 忽略普通http请求
        System.out.println("ignoring normal http request");
    }

    @Override
    public void handleFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        // text frame
        if (frame instanceof TextWebSocketFrame) {
            String text = ((TextWebSocketFrame) frame).text();
//            TextWebSocketFrame rspFrame = new TextWebSocketFrame(text);
            System.out.println("recieve TextWebSocketFrame from channel {}" + ctx.channel());
            // 发给其他所有channel
//            for (Channel ch : channelMap.values()) {
//                if (ctx.channel().equals(ch)) {
//                    continue;
//                }
//                TextWebSocketFrame rspFrame = new TextWebSocketFrame(text);
//                ch.writeAndFlush(rspFrame);
//                System.out.println("write text[{}] to channel {}"+ text+ ch);
//            }
            TextWebSocketFrame rspFrame = new TextWebSocketFrame(text);
            channelGroup.writeAndFlush(rspFrame);
            return;
        }
        // ping frame, 回复pong frame即可
        if (frame instanceof PingWebSocketFrame) {
            System.out.println("recieve PingWebSocketFrame from channel {}" + ctx.channel());
            ctx.channel().writeAndFlush(new PongWebSocketFrame(frame.content().retain()));
            return;
        }

        if (frame instanceof PongWebSocketFrame) {
            System.out.println("recieve PongWebSocketFrame from channel {}" + ctx.channel());
            return;
        }
        // close frame,
        if (frame instanceof CloseWebSocketFrame) {
            System.out.println("recieve CloseWebSocketFrame from channel {}" + ctx.channel());
            WebSocketServerHandshaker handshaker = ctx.channel().attr(ATTR_HANDSHAKER).get();
            if (handshaker == null) {
                System.out.println("channel {} have no HandShaker" + ctx.channel());
                return;
            }
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }
        // 剩下的是binary frame, 忽略
        System.out.println("unhandle binary frame from channel {}" + ctx.channel());
    }

    //三者与：1.GET? 2.Upgrade头 包含websocket字符串?  3.Connection头 包含 Upgrade字符串?
    private boolean isWebSocketUpgrade(FullHttpRequest req) {
        HttpHeaders headers = req.headers();
        return req.getMethod().equals(HttpMethod.GET)
                && headers.get(HttpHeaderNames.UPGRADE).contains(WEBSOCKET_UPGRADE)
                && headers.get(HttpHeaderNames.CONNECTION).contains(WEBSOCKET_CONNECTION);
    }
}
