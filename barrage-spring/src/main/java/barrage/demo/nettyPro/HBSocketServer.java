package barrage.demo.nettyPro;

import barrage.demo.dao.CommunicationMessageDao;
import barrage.demo.enums.SocketResponseEnum;
import barrage.demo.nettyUse.WebSocketServerHandler;
import barrage.demo.utils.DateFormatUtil;
//import barrage.demo.utils.SSLUtil;
import com.google.gson.Gson;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelMatchers;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HBSocketServer implements HttpService,SocketFrameService{

    private String host;
    private int port;

    private static int MAX_CONTENT_LENGTH = 65536;
    private static final String SSL_HANDLER = "SSL_HANDLER";
    private static final String HTTP_SERVER_CODEC = "HTTP_SERVER_CODEC";
    private static final String HTTP_OBJECT_AGGREGATOR = "HTTP_OBJECT_AGGREGATOR";
    private static final String CHUNKED_WRITE_HANDLER = "CHUNKED_WRITE_HANDLER";
    private static final String MY_SERVICE = "MY_SERVICE";

    private static final String ONLINE_COUNT_MAP_KEY = "online_count";

    private static final String WEBSOCKET_UPGRADE = "websocket";
    private static final String WEBSOCKET_CONNECTION = "Upgrade";

    private static final String WEB_SOCKET_URL_PATTERN = "ws://%s:%d";
    private static String WEB_SOCKET_URL;

    private static final AttributeKey<WebSocketServerHandshaker> ATTRIBUTE_KEY = AttributeKey.newInstance("ATTR_KEY_CHANNELID");
    private Map<ChannelId, Channel> channelMap = new ConcurrentHashMap<>();
    private ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private static Map<String, Integer> onlineCountMap = new ConcurrentHashMap<>(2);

    public HBSocketServer(int port) {
        this("0.0.0.0", port);
    }

    public HBSocketServer(String host, int port) {
        this.host = host;
        this.port = port;
        WEB_SOCKET_URL = String.format(WEB_SOCKET_URL_PATTERN, host, port);
//        onlineCountMap.put(ONLINE_COUNT_MAP_KEY, 0);
    }

    public void start() throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
//        SSLContext sslContext = SSLUtil.createSSLContext("jks", "D:/gornix.jks", "654321");
        bootstrap
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        channelGroup.add(ch);
                        channelMap.put(ch.id(), ch);
                        ch.closeFuture().addListener((ChannelFutureListener) future -> channelMap.remove(future.channel().id()));

//                        SSLEngine sslEngine = sslContext.createSSLEngine();
//                        sslEngine.setUseClientMode(false);

//                        pipeline.addLast(SSL_HANDLER, new SslHandler(sslEngine));
                        pipeline.addLast(HTTP_SERVER_CODEC, new HttpServerCodec());
                        pipeline.addLast(HTTP_OBJECT_AGGREGATOR, new HttpObjectAggregator(MAX_CONTENT_LENGTH));
                        pipeline.addLast(CHUNKED_WRITE_HANDLER, new ChunkedWriteHandler());
                        pipeline.addLast(MY_SERVICE, new HBSocketHandler(HBSocketServer.this, HBSocketServer.this));
                    }
                });
        try {
            ChannelFuture channelFuture = bootstrap.bind(host, port).addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    System.out.println("socket success");
                }
            }).sync();

            channelFuture.channel().closeFuture().addListener((ChannelFutureListener) future ->
                    System.out.println("socket channel closed {} " + future.channel())).sync();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @see HttpService
     * @param context
     * @param request
     */
    @Override
    public void handleHttpRequest(ChannelHandlerContext context, FullHttpRequest request) {
        Gson gson = new Gson();
        if (isWebSocketUpgrade(request)) {
            String subProtocols = request.headers().get(HttpHeaderNames.SEC_WEBSOCKET_PROTOCOL);
            WebSocketServerHandshakerFactory factory = new WebSocketServerHandshakerFactory(WEB_SOCKET_URL, subProtocols, false);
            WebSocketServerHandshaker handshaker = factory.newHandshaker(request);
            if (handshaker == null) {
                WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(context.channel());
            }else{
                handshaker.handshake(context.channel(), request);
                context.channel().attr(ATTRIBUTE_KEY).set(handshaker);
//                increaseOnlineCount();
                channelGroup.writeAndFlush(new TextWebSocketFrame(gson.toJson(createOnlineResponse(channelMap.size()))));
            }
            return;
        }
        System.out.println("ignore normal http request");
    }

    /**
     * @see SocketFrameService
     * @param context
     * @param webSocketFrame
     */
    @Override
    public void handleFrame(ChannelHandlerContext context, WebSocketFrame webSocketFrame) {
        Gson gson = new Gson();
        if (webSocketFrame instanceof TextWebSocketFrame) {
            String text = ((TextWebSocketFrame) webSocketFrame).text();
            System.out.println("receive TextWebSocket from channel {} " + context.channel() + " is " + text);
            TextWebSocketFrame responseFrame = new TextWebSocketFrame(gson.toJson(createMessageResponse(text, DateFormatUtil.toChinaNormal(new Date()))));
//            channelGroup.writeAndFlush(responseFrame);
            channelGroup.writeAndFlush(responseFrame, ChannelMatchers.isNot(context.channel()));
            context.channel().writeAndFlush(new TextWebSocketFrame(gson.toJson(createSuccessResponse())));
            return;
        }
        if (webSocketFrame instanceof PingWebSocketFrame) {
            System.out.println("receive PingWebSocketFrame from channel {} " + context.channel());
            context.channel().writeAndFlush(new PongWebSocketFrame(webSocketFrame.content().retain()));
            return;
        }
        if (webSocketFrame instanceof PongWebSocketFrame) {
            System.out.println("receive PongWebSocketFrame from channel {} " + context.channel());
            return;
        }
        if (webSocketFrame instanceof CloseWebSocketFrame) {
            System.out.println("receive CloseWebSocketFrame from channel {} " + context.channel());
            WebSocketServerHandshaker handshaker = context.channel().attr(ATTRIBUTE_KEY).get();
            if (handshaker == null) {
                System.out.println(context.channel() + " does not has handShaker");
                return;
            }
//            decreaseOnlineCount();
//            channelMap.remove(context.channel().id());
            channelGroup.writeAndFlush(new TextWebSocketFrame(gson.toJson(createOnlineResponse(channelMap.size()))));
        }
        System.out.println("unHandle binary frame from channel {}" + context.channel());
    }

    /**
     * 判断是否是切换webSocket的请求
     * @param request 客户端的请求
     * @return 返回判断结果
     */
    private boolean isWebSocketUpgrade(FullHttpRequest request) {
        HttpHeaders httpHeaders = request.headers();
        System.out.println("httpHeader: " + httpHeaders.get(HttpHeaderNames.UPGRADE) + "  " + httpHeaders.get(HttpHeaderNames.CONNECTION));
        return request.method().equals(HttpMethod.GET)
                && httpHeaders.get(HttpHeaderNames.UPGRADE).contains(WEBSOCKET_UPGRADE)
                && httpHeaders.get(HttpHeaderNames.CONNECTION).contains(WEBSOCKET_CONNECTION);
    }

    private synchronized static void increaseOnlineCount() {
        onlineCountMap.computeIfPresent(ONLINE_COUNT_MAP_KEY, (k, v) -> ++v);
    }

    private synchronized static void decreaseOnlineCount() {
        onlineCountMap.computeIfPresent(ONLINE_COUNT_MAP_KEY, (k, v) -> --v);
    }

    private CommunicationMessageDao createOnlineResponse(Integer onlineCount) {
        CommunicationMessageDao communicationMessageDao = new CommunicationMessageDao();
        communicationMessageDao.setMethodCode(SocketResponseEnum.GET_ONLINE_COUNT.getCode());
        communicationMessageDao.setOnlineCount(onlineCount);
        return communicationMessageDao;
    }

    private CommunicationMessageDao createMessageResponse(String message,String currentDate) {
        CommunicationMessageDao communicationMessageDao = new CommunicationMessageDao();
        communicationMessageDao.setMethodCode(SocketResponseEnum.MESSAGE_RESPONSE.getCode());
        communicationMessageDao.setSendingMessage(message);
        communicationMessageDao.setCurrentDate(currentDate);
        return communicationMessageDao;
    }

    private CommunicationMessageDao createSuccessResponse() {
        CommunicationMessageDao communicationMessageDao = new CommunicationMessageDao();
        communicationMessageDao.setMethodCode(SocketResponseEnum.SUCCESS_RECEIVE_RESPONSE.getCode());
        return communicationMessageDao;
    }
}
