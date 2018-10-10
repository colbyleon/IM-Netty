package com.tencent.netty.client.handler;

import com.tencent.netty.protocol.response.LoginResponsePacket;
import com.tencent.netty.session.Session;
import com.tencent.netty.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author v_xiangbluo
 * @date 2018/9/27 18:10
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    /*@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ": 客户端开始登陆");

        // 创建登录对象
        LoginRequestPacket reqPacket = new LoginRequestPacket();
        reqPacket.setUserId(UUID.randomUUID().toString().replaceAll("-",""));
        reqPacket.setUsername("flash");
        reqPacket.setPassword("123456");

        // 写数据
        ctx.channel().writeAndFlush(reqPacket);
    }*/

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {
        String userId = msg.getUserId();
        String userName = msg.getUserName();

        if (msg.isSuccess()) {
            System.out.println("[" + userName + "]登录成功，userId 为: " + msg.getUserId());
            SessionUtil.bindSession(new Session(userId,userName),ctx.channel());
        }else{
            System.out.println("[" + userName + "]登录失败，原因：" + msg.getReason());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
        System.out.println("客户端连接被关闭");
    }
}
