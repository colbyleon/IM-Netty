package com.tencent.netty.client.console;

import com.tencent.netty.common.protocol.request.MessageReqPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author v_xiangbluo
 * @date 2018/10/9 13:04
 */
public class SendToUserConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("toUserId: ");
        String toUserId = scanner.next();
        System.out.println("message: ");
        String message = scanner.next();
        channel.writeAndFlush(new MessageReqPacket(toUserId,message));
    }
}
