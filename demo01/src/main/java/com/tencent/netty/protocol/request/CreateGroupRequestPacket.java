package com.tencent.netty.protocol.request;

import com.tencent.netty.protocol.Packet;
import com.tencent.netty.protocol.command.Command;

import java.util.List;
import java.util.Set;

/**
 * @author v_xiangbluo
 * @date 2018/10/9 14:13
 */
public class CreateGroupRequestPacket extends Packet {
    private Set<String> userIdSet;

    public Set<String> getUserIdSet() {
        return userIdSet;
    }

    public void setUserIdSet(Set<String> userIdSet) {
        this.userIdSet = userIdSet;
    }

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_REQUEST;
    }
}
