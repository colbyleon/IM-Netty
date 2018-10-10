package com.tencent.netty.common.protocol.request;

import com.tencent.netty.common.protocol.Packet;
import com.tencent.netty.common.protocol.command.Command;

/**
 * @author v_xiangbluo
 * @date 2018/10/9 18:52
 */
public class QuitGroupRequestPacket extends Packet {
    private String groupId;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public Byte getCommand() {
        return Command.QUIT_GROUP_REQUEST;
    }
}
