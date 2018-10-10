package com.tencent.netty.client.console;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author v_xiangbluo
 * @date 2018/10/9 13:00
 */
public interface ConsoleCommand {
    void exec(Scanner scanner, Channel channel);
}
