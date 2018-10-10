package com.tencent.netty.client.console;

import com.tencent.netty.common.protocol.request.GroupMessageReqPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author v_xiangbluo
 * @date 2018/10/10 10:28
 */
public class SendToGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("发送消息给某个某个群组：");
        String toGroupId = scanner.next();
        System.out.print("message：");
        String message = scanner.next();
        channel.writeAndFlush(new GroupMessageReqPacket(toGroupId, message));
    }
}
