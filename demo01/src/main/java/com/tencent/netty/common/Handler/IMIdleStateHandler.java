package com.tencent.netty.common.Handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author v_xiangbluo
 * @date 2018/10/10 14:34
 */
public class IMIdleStateHandler extends IdleStateHandler {
    private static final int READER_IDLE_TIME = 15;

    public IMIdleStateHandler(){
        super(READER_IDLE_TIME,0,0,TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        System.out.println("【连接超时】: "+READER_IDLE_TIME +"秒内未读到数据，关闭连接");
        ctx.channel().close();

    }
}
