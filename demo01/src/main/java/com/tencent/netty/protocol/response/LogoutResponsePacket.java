package com.tencent.netty.protocol.response;

import com.tencent.netty.protocol.Packet;
import com.tencent.netty.protocol.command.Command;

/**
 * @author v_xiangbluo
 * @date 2018/10/9 15:36
 */
public class LogoutResponsePacket extends Packet {
    private Boolean success;

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @Override
    public Byte getCommand() {
        return Command.LOGOUT_RESPONSE;
    }
}
