package com.tencent.netty.protocol.response;

import com.tencent.netty.protocol.Packet;
import com.tencent.netty.protocol.command.Command;
import com.tencent.netty.session.Session;

/**
 * @author v_xiangbluo
 * @date 2018/10/10 10:05
 */
public class GroupMessageRespPacket extends Packet {
    private Boolean success;
    private String reason;

    private String fromGroupId;
    private Session fromUser;
    private String message;

    public String getFromGroupId() {
        return fromGroupId;
    }

    public void setFromGroupId(String fromGroupId) {
        this.fromGroupId = fromGroupId;
    }

    public Session getFromUser() {
        return fromUser;
    }

    public void setFromUser(Session fromUser) {
        this.fromUser = fromUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public Byte getCommand() {
        return Command.GROUP_MESSAGE_RESPONSE;
    }
}
