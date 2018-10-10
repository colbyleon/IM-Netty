package com.tencent.netty.common.protocol.response;

import com.tencent.netty.common.protocol.Packet;
import com.tencent.netty.common.protocol.command.Command;

/**
 * @author v_xiangbluo
 * @date 2018/9/27 14:52
 */
public class LoginResponsePacket extends Packet {
    private String userId;

    private String userName;

    private Boolean success;
    private String reason;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
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
}
