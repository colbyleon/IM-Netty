package com.tencent.netty.protocol.request;

import com.tencent.netty.protocol.Packet;
import com.tencent.netty.protocol.command.Command;

/**
 * @author v_xiangbluo
 * @date 2018/9/26 19:53
 */
public class LoginRequestPacket extends Packet {
    private String  userId;
    private String username;
    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
