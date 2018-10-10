package com.tencent.netty.server.handler;

import com.tencent.netty.protocol.request.ListGroupMembersRequestPacket;
import com.tencent.netty.protocol.response.ListGroupMembersResponsePacket;
import com.tencent.netty.session.Session;
import com.tencent.netty.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author v_xiangbluo
 * @date 2018/10/9 19:26
 */
@ChannelHandler.Sharable
public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {
    public static final ListGroupMembersRequestHandler INSTANCE = new ListGroupMembersRequestHandler();
    private ListGroupMembersRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersRequestPacket msg) throws Exception {
        // 1. 获取群的 ChannelGroup
        String groupId = msg.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);

        ListGroupMembersResponsePacket responsePacket = new ListGroupMembersResponsePacket();
        if (channelGroup != null) {
            // 2. 遍历群成员的channel ,对应的session,构造群成员的信息
            List<Session> sessionList = new ArrayList<>();
            channelGroup.stream()
                    .map(SessionUtil::getSession)
                    .forEach(sessionList::add);

            // 3. 构建获取成员列表响应写回到客户端

            responsePacket.setSuccess(true);
            responsePacket.setGroupId(groupId);
            responsePacket.setSessionList(sessionList);
        }else {
            responsePacket.setSuccess(false);
            responsePacket.setReason("no such group");
        }

        ctx.channel().writeAndFlush(responsePacket);
    }
}
