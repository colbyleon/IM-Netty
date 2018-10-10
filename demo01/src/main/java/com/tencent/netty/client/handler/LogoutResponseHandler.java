package com.tencent.netty.client.handler;

import com.tencent.netty.protocol.response.LogoutResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author v_xiangbluo
 * @date 2018/10/9 15:38
 */
public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutResponsePacket msg) throws Exception {
        if (msg.isSuccess()) {
            System.out.println("【退出登陆】 成功!");
        }else {
            System.out.println("【退出登陆】 失败!");
        }
    }
}
