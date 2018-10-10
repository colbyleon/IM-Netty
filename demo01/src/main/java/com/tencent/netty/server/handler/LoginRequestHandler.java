package com.tencent.netty.server.handler;

import com.tencent.netty.protocol.request.LoginRequestPacket;
import com.tencent.netty.protocol.response.LoginResponsePacket;
import com.tencent.netty.session.Session;
import com.tencent.netty.util.IDUtil;
import com.tencent.netty.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * @author v_xiangbluo
 * @date 2018/9/27 17:40
 */
// 1. 加上注解标识，表明该 handler 是可以多个 channel 共享的
@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    // 2. 构造单例
    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();
    protected LoginRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setUserName(loginRequestPacket.getUsername());

        // 登陆校验
        if (valid(loginRequestPacket)) {
            loginResponsePacket.setSuccess(true);
            String userId = IDUtil.randomId();
            loginResponsePacket.setUserId(userId);
            System.out.println("[" + loginRequestPacket.getUsername() + "]登录成功");
            SessionUtil.bindSession(new Session(userId, loginResponsePacket.getUserName()), ctx.channel());
            // 登录成功后去auth
            ctx.pipeline().remove("auth");
        } else {
            loginResponsePacket.setReason("账号密码校验失败");
            loginResponsePacket.setSuccess(false);
            System.out.println(new Date() + ": 登录失败！");
        }

        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
//        return loginRequestPacket.getUsername().equals("flash") && loginRequestPacket.getPassword().equals("123456");
    }
}
