package com.tencent.netty.common.protocol.request;

import com.tencent.netty.common.protocol.Packet;
import com.tencent.netty.common.protocol.command.Command;

/**
 * @author v_xiangbluo
 * @date 2018/10/9 14:29
 */
public class LogoutRequestPacket extends Packet {


    @Override
    public Byte getCommand() {
        return Command.LOGOUT_REQUEST;
    }
}
