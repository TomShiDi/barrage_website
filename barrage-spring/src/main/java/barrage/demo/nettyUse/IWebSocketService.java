package barrage.demo.nettyUse;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

public interface IWebSocketService {
    void handleFrame(ChannelHandlerContext ctx, WebSocketFrame frame);
}
