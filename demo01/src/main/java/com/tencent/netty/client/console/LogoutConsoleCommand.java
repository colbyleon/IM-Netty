package com.tencent.netty.client.console;

import com.tencent.netty.common.protocol.request.LogoutRequestPacket;
import com.tencent.netty.common.util.SessionUtil;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author v_xiangbluo
 * @date 2018/10/9 13:04
 */
public class LogoutConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        LogoutRequestPacket logoutRequestPacket = new LogoutRequestPacket();
        SessionUtil.unBindSession(channel);

        channel.writeAndFlush(logoutRequestPacket);
    }
}
