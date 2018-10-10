package com.tencent.netty.client.console;

import com.tencent.netty.common.protocol.request.CreateGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

/**
 * @author v_xiangbluo
 * @date 2018/10/9 13:04
 */
public class CreateGroupConsoleCommand implements ConsoleCommand {
    private static final String USER_ID_SPLITER = ",";

    @Override
    public void exec(Scanner scanner, Channel channel) {
        CreateGroupRequestPacket createGroupRequestPacket = new CreateGroupRequestPacket();

        System.out.print("【拉人群聊】输入 userId 列表，userId 之间英文逗号隔开：");
        String userIds = scanner.next();
        createGroupRequestPacket.setUserIdSet(new HashSet<>(Arrays.asList(userIds.split(USER_ID_SPLITER))));
        channel.writeAndFlush(createGroupRequestPacket);
    }
}
