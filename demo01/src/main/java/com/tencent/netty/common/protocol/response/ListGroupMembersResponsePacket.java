package com.tencent.netty.common.protocol.response;

import com.tencent.netty.common.protocol.Packet;
import com.tencent.netty.common.protocol.command.Command;
import com.tencent.netty.common.session.Session;

import java.util.List;

/**
 * @author v_xiangbluo
 * @date 2018/10/9 19:25
 */
public class ListGroupMembersResponsePacket extends Packet {
    private Boolean success;
    private String reason;
    private String groupId;
    private List<Session> sessionList;

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

    public List<Session> getSessionList() {
        return sessionList;
    }

    public void setSessionList(List<Session> sessionList) {
        this.sessionList = sessionList;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }


    @Override
    public Byte getCommand() {
        return Command.LIST_GROUP_MEMBERS_RESPONSE;
    }
}
