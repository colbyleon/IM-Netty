package com.tencent.netty.protocol.request;

import com.tencent.netty.protocol.Packet;
import com.tencent.netty.protocol.command.Command;

/**
 * @author v_xiangbluo
 * @date 2018/10/10 15:14
 */
public class HeartBeatRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.HEART_BEAT_REQUEST;
    }
}
