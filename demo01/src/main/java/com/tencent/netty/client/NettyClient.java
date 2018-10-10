package com.tencent.netty.client;

import com.tencent.netty.Handler.IMIdleStateHandler;
import com.tencent.netty.Handler.PacketCodecHandler;
import com.tencent.netty.client.console.ConsoleCommandManager;
import com.tencent.netty.client.console.LoginConsoleCommand;
import com.tencent.netty.client.handler.*;
import com.tencent.netty.codec.Spliter;
import com.tencent.netty.util.SessionUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author v_xiangbluo
 * @date 2018/9/26 11:48
 */
public class NettyClient {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8000;
    private static final Bootstrap BOOTSTRAP;

    static {
        BOOTSTRAP =  new Bootstrap();
    }
    /**
     * 重连
     */
    private final static Integer MAX_RETRY = 5;

    public static void main(String[] args) throws InterruptedException {

        NioEventLoopGroup group = new NioEventLoopGroup();

        BOOTSTRAP
                // 1.指定线程模型
                .group(group)
                // 2.指定 IO 类型为 NIO
                .channel(NioSocketChannel.class)
                // 3.IO 处理逻辑
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast(new Spliter()) // 检查 magic num 以及拆包
                                .addLast(new IMIdleStateHandler())  // 心跳检测
                                .addLast(PacketCodecHandler.INSTANCE) // 包解码 ByteToMsg
                                .addLast(new LoginResponseHandler())   // 登陆
                                .addLast(new LogoutResponseHandler()) // 登出
                                .addLast(new MessageResponseHandler()) // 消息
                                .addLast(new CreateGroupResponseHandler()) // 创建群聊
                                .addLast(new JoinGroupResponseHandler()) // 加入群聊
                                .addLast(new QuitGroupResponseHandler()) // 退出群聊
                                .addLast(new ListGroupMembersResponseHandler()) // 获取群成员列表
                                .addLast(new GroupMessageResponseHandler()) // 群聊消息处理

                                .addLast(new HeartBeatTimerHandler()); // 心跳定时器

                    }
                })
//                .attr(AttributeKey.newInstance("clientName"), "nettyClient")
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true);


        // 4.建立连接
        connect();
    }

    /**
     * 用于暴露重新连接
     */
    public static void connect() {
        connect(BOOTSTRAP, HOST, PORT, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功");

                Channel channel = ((ChannelFuture) future).channel();
                // 连接成功之后，启动控制台线程
                startConsoleThread(channel);
            } else {
                if (retry > 0) {
                    // 第几次重连
                    int order = (MAX_RETRY - retry) + 1;
                    // 本次重连的间隔
                    int delay = 1 << order < 3 ? order : 3;
                    System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                    bootstrap.config().group().schedule(() ->
                            connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS
                    );
                } else {
                    System.out.println("重试失败...");
                }
            }
        });
    }

    private static void startConsoleThread(Channel channel) {
        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        Scanner sc = new Scanner(System.in);

        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (!SessionUtil.hasLogin(channel)) {
                    loginConsoleCommand.exec(sc, channel);
                } else {
                    consoleCommandManager.exec(sc, channel);
                }
            }
        }).start();
    }

}
