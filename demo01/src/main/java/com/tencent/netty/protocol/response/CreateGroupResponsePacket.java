package com.tencent.netty.protocol.response;

import com.tencent.netty.protocol.Packet;
import com.tencent.netty.protocol.command.Command;

import java.util.List;

/**
 * @author v_xiangbluo
 * @date 2018/10/9 14:52
 */
public class CreateGroupResponsePacket extends Packet {
    private Boolean success;
    private String groupId;
    private List<String> userNameList;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<String> getUserNameList() {
        return userNameList;
    }

    public void setUserNameList(List<String> userNameList) {
        this.userNameList = userNameList;
    }

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_RESPONSE;
    }
}
