package com.tencent.netty.common.codec;

import com.tencent.netty.common.protocol.Packet;
import com.tencent.netty.common.protocol.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author v_xiangbluo
 * @date 2018/9/27 17:35
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
        PacketCodeC.INSTANCE.encode(out, msg);
    }
}
