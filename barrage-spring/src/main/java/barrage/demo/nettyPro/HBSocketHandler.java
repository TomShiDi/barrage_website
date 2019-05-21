package barrage.demo.nettyPro;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

public class HBSocketHandler extends SimpleChannelInboundHandler<Object> {

    private HttpService httpService;

    private SocketFrameService socketFrameService;

    public HBSocketHandler(HttpService httpService, SocketFrameService socketFrameService) {
        this.httpService = httpService;
        this.socketFrameService = socketFrameService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            httpService.handleHttpRequest(ctx, (FullHttpRequest) msg);
        }
        if (msg instanceof WebSocketFrame) {
            socketFrameService.handleFrame(ctx, (WebSocketFrame) msg);
        }
    }
}
