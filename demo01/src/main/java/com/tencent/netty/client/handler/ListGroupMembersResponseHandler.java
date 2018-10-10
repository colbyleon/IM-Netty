package com.tencent.netty.client.handler;

import com.tencent.netty.protocol.response.ListGroupMembersResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author v_xiangbluo
 * @date 2018/10/9 19:33
 */
public class ListGroupMembersResponseHandler extends SimpleChannelInboundHandler<ListGroupMembersResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersResponsePacket responsePacket) throws Exception {
        if (responsePacket.isSuccess()) {
            System.out.println("群[" + responsePacket.getGroupId() + "]中的人包括：" + responsePacket.getSessionList());
        }else{
            System.out.println(responsePacket.getReason());
        }
    }
}
