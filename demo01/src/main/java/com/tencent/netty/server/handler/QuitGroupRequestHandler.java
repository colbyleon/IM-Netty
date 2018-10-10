package com.tencent.netty.server.handler;

import com.tencent.netty.common.protocol.request.QuitGroupRequestPacket;
import com.tencent.netty.common.protocol.response.MessageResponsePacket;
import com.tencent.netty.common.protocol.response.QuitGroupResponsePacket;
import com.tencent.netty.common.session.Session;
import com.tencent.netty.common.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * @author v_xiangbluo
 * @date 2018/10/9 18:52
 */
@ChannelHandler.Sharable
public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {
    public static final QuitGroupRequestHandler INSTANCE = new QuitGroupRequestHandler();
    protected QuitGroupRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRequestPacket requestPacket) throws Exception {
        String groupId = requestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);

        boolean isRemove = channelGroup.remove(ctx.channel());
        QuitGroupResponsePacket responsePacket = new QuitGroupResponsePacket();
        responsePacket.setGroupId(groupId);

        if (isRemove) {
            responsePacket.setSuccess(true);
            // 没有人在群聊中时删除群聊组
            if (channelGroup.isEmpty()) {
                SessionUtil.deleteChannelGroup(groupId);
                System.out.println("【删除群聊】: " +groupId);
            }

            // 通知其它群内客户端xx退出群聊
            Session session = SessionUtil.getSession(ctx.channel());
            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            messageResponsePacket.setFromUserId("000000");
            messageResponsePacket.setFromUserName("系统通知");
            messageResponsePacket.setMessage(session + " 退出群聊 " + groupId);
            // 自己不再通知
            channelGroup.writeAndFlush(messageResponsePacket, channel -> channel != ctx.channel());

        }else {
            responsePacket.setSuccess(false);
            responsePacket.setReason("不在此群聊中");
        }

        ctx.channel().writeAndFlush(responsePacket);
    }
}
