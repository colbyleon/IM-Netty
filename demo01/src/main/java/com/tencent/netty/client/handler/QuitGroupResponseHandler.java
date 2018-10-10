package com.tencent.netty.client.handler;

import com.tencent.netty.common.protocol.response.QuitGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author v_xiangbluo
 * @date 2018/10/9 18:58
 */
public class QuitGroupResponseHandler extends SimpleChannelInboundHandler<QuitGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupResponsePacket msg) throws Exception {
        if (msg.isSuccess()) {
            System.out.println("退出群[" + msg.getGroupId() + "]成功!");
        }else {
            System.err.println("退出群[" + msg.getGroupId() + "]失败，原因为：" + msg.getReason());
        }
    }
}
