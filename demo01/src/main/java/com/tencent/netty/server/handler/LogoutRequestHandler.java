package com.tencent.netty.server.handler;

import com.tencent.netty.protocol.request.LogoutRequestPacket;
import com.tencent.netty.protocol.response.LogoutResponsePacket;
import com.tencent.netty.session.Session;
import com.tencent.netty.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author v_xiangbluo
 * @date 2018/10/9 16:12
 */
@ChannelHandler.Sharable
public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestPacket> {
    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();
    protected LogoutRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutRequestPacket msg) throws Exception {
        LogoutResponsePacket logoutResponsePacket = new LogoutResponsePacket();
        logoutResponsePacket.setSuccess(true);

        Session session = SessionUtil.getSession(ctx.channel());
        System.out.println("【退出登陆】: " + session.getUserName());
        SessionUtil.unBindSession(ctx.channel());

        // 退出登陆后重新加入校验
        ctx.writeAndFlush(logoutResponsePacket);
        ctx.pipeline().addAfter("login", "auth", AuthHandler.INSTANCE);
    }
}
