package com.tencent.netty.server.handler;

import com.tencent.netty.common.protocol.request.MessageReqPacket;
import com.tencent.netty.common.protocol.response.MessageResponsePacket;
import com.tencent.netty.common.session.Session;
import com.tencent.netty.common.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author v_xiangbluo
 * @date 2018/9/27 17:41
 */
@ChannelHandler.Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageReqPacket> {
    public static final MessageRequestHandler INSTANCE = new MessageRequestHandler();
    protected MessageRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageReqPacket msg) throws Exception {
        // 1. 拿到消息发送方的会话信息
        Session session = SessionUtil.getSession(ctx.channel());

        // 2. 通过消息发送方的会话信息构造要发送的消息
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();

        // 3. 拿到消息接收方的 channel
        Channel toChannel = SessionUtil.getChannel(msg.getToUserId());

        // 4. 将消息发送给消息接收方,接收方存在而且在线
        if (toChannel != null && SessionUtil.hasLogin(toChannel)) {
            messageResponsePacket.setFromUserId(session.getUserId());
            messageResponsePacket.setFromUserName(session.getUserName());
            messageResponsePacket.setMessage(msg.getMessage());
            toChannel.writeAndFlush(messageResponsePacket);
        }else {
            String userId = msg.getToUserId();
            messageResponsePacket.setFromUserId(session.getUserId());
            messageResponsePacket.setFromUserName(session.getUserName());
            messageResponsePacket.setMessage("【"+userId+"】 不在线，发送失败！请稍候重新发送！");
            ctx.channel().writeAndFlush(messageResponsePacket);
        }
    }
}
