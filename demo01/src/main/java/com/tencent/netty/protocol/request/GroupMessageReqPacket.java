package com.tencent.netty.protocol.request;

import com.tencent.netty.protocol.Packet;
import com.tencent.netty.protocol.command.Command;

/**
 * @author v_xiangbluo
 * @date 2018/10/10 10:02
 */
public class GroupMessageReqPacket extends Packet {
    private String toGroupId;
    private String message;

    public GroupMessageReqPacket(String toGroupId, String message) {
        this.toGroupId = toGroupId;
        this.message = message;
    }

    public String getToGroupId() {
        return toGroupId;
    }

    public void setToGroupId(String toGroupId) {
        this.toGroupId = toGroupId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return Command.GROUP_MESSAGE_REQUEST;
    }
}
