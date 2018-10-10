package com.tencent.netty.client.handler;

import com.tencent.netty.protocol.response.GroupMessageRespPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author v_xiangbluo
 * @date 2018/10/10 10:26
 */
public class GroupMessageResponseHandler extends SimpleChannelInboundHandler<GroupMessageRespPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRespPacket msg) throws Exception {
        if (msg.isSuccess()) {
            System.out.println(msg.getMessage());
        } else {
            System.out.println(msg.getReason());
        }
    }
}
