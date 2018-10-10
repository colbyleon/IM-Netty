package com.tencent.netty.common.protocol.response;

import com.tencent.netty.common.protocol.Packet;
import com.tencent.netty.common.protocol.command.Command;

/**
 * @author v_xiangbluo
 * @date 2018/10/10 15:25
 */
public class HeartBeatResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.HEART_BEAT_RESPONSE;
    }
}
