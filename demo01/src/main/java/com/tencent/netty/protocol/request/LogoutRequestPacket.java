package com.tencent.netty.protocol.request;

import com.tencent.netty.protocol.Packet;
import com.tencent.netty.protocol.command.Command;

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
