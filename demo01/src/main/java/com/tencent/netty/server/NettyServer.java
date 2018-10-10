package com.tencent.netty.server;

import com.tencent.netty.Handler.IMIdleStateHandler;
import com.tencent.netty.Handler.PacketCodecHandler;
import com.tencent.netty.codec.Spliter;
import com.tencent.netty.server.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.util.Date;

/**
 * @author v_xiangbluo
 * @date 2018/9/26 11:37
 */
public class NettyServer {
    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        serverBootstrap
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .handler(new ChannelInitializer<NioServerSocketChannel>() {
                    @Override
                    protected void initChannel(NioServerSocketChannel ch) throws Exception {
                        System.out.println(new Date() + ": 服务器启动...");
                    }
                })
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast("idleState",new IMIdleStateHandler())  // 假死连接检测
                                .addLast("split", new Spliter())
                                .addLast("codec", PacketCodecHandler.INSTANCE)
                                .addLast("heartBeat",HeartBeatRequestHandler.INSTANCE)  // 心跳回复
                                .addLast("login", LoginRequestHandler.INSTANCE)
                                .addLast("auth", AuthHandler.INSTANCE)     // 登陆验证，仅一次
                                .addLast("flatHandler",IMHandler.INSTANCE);    // 业务逻辑扁平化
                    }
                })
                .attr(AttributeKey.newInstance("serverName"), "nettyServer")
                .childAttr(AttributeKey.newInstance("clientKey"), "clientValue")
                .option(ChannelOption.SO_BACKLOG, 1024)
                // 开启TCP底层心跳机制
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true);

        bind(serverBootstrap, 8000);

    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(new Date() + ": 端口[" + port + "]绑定成功!");
            } else {
                System.out.println(new Date() + ": 端口[" + port + "]绑定失败!");
                bind(serverBootstrap, port + 1);
            }
        });
    }


}
