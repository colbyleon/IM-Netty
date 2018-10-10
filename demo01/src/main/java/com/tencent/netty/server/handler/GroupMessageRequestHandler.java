package com.tencent.netty.server.handler;

import com.tencent.netty.protocol.request.GroupMessageReqPacket;
import com.tencent.netty.protocol.response.GroupMessageRespPacket;
import com.tencent.netty.session.Session;
import com.tencent.netty.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * @author v_xiangbluo
 * @date 2018/10/10 10:02
 */
@ChannelHandler.Sharable
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageReqPacket> {
    public static final GroupMessageRequestHandler INSTANCE = new GroupMessageRequestHandler();
    protected GroupMessageRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageReqPacket msg) throws Exception {
        // 1. 拿到 groupId 构造群聊消息的响应
        String groupId = msg.getToGroupId();
        Session fromSession = SessionUtil.getSession(ctx.channel());
        GroupMessageRespPacket respPacket = new GroupMessageRespPacket();

        if (SessionUtil.isInGroup(fromSession.getUserId(), groupId)) {
            respPacket.setSuccess(true);
            respPacket.setFromGroupId(groupId);
            respPacket.setMessage(msg.getMessage());
            respPacket.setFromUser(fromSession);

            // 2. 拿到群聊对应的channelGroup, 写到每个客户端
            ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
            channelGroup.writeAndFlush(respPacket);
        } else {
            respPacket.setSuccess(false);
            respPacket.setReason("【群聊失败】: 你不在当前群聊中[" + groupId + "]");
            ctx.channel().writeAndFlush(respPacket);
        }
    }
}
