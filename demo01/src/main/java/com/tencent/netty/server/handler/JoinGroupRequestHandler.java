package com.tencent.netty.server.handler;

import com.tencent.netty.common.protocol.request.JoinGroupRequestPacket;
import com.tencent.netty.common.protocol.response.JoinGroupResponsePacket;
import com.tencent.netty.common.protocol.response.MessageResponsePacket;
import com.tencent.netty.common.session.Session;
import com.tencent.netty.common.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * @author v_xiangbluo
 * @date 2018/10/9 17:34
 */
@ChannelHandler.Sharable
public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {
    public static final JoinGroupRequestHandler INSTANCE = new JoinGroupRequestHandler();
    protected JoinGroupRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRequestPacket joinGroupRequestPacket) throws Exception {
        // 1. 获取群对应的 channelGroup,然后将当前用户的 channel 添加进去
        String groupId = joinGroupRequestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.add(ctx.channel());

        // 2. 构造加群响应发送给客户端
        JoinGroupResponsePacket joinGroupResponsePacket = new JoinGroupResponsePacket();
        joinGroupResponsePacket.setSuccess(true);
        joinGroupResponsePacket.setGroupId(groupId);

        ctx.channel().writeAndFlush(joinGroupResponsePacket);

        // 3. 通知其它群内客户端xx加入群聊
        Session session = SessionUtil.getSession(ctx.channel());
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId("000000");
        messageResponsePacket.setFromUserName("系统通知");
        messageResponsePacket.setMessage(session + " 加入群聊 " + groupId);
        // 自己不再通知
        channelGroup.writeAndFlush(messageResponsePacket, channel -> channel != ctx.channel());
    }
}
