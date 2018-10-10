package com.tencent.netty.server.handler;

import com.tencent.netty.protocol.request.CreateGroupRequestPacket;
import com.tencent.netty.protocol.response.CreateGroupResponsePacket;
import com.tencent.netty.util.IDUtil;
import com.tencent.netty.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author v_xiangbluo
 * @date 2018/10/9 14:34
 */
@ChannelHandler.Sharable
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {
    public static final CreateGroupRequestHandler INSTANCE = new CreateGroupRequestHandler();
    protected CreateGroupRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket createGroupRequestPacket) throws Exception {
        Set<String> userIdSet = createGroupRequestPacket.getUserIdSet();

        List<String> userNameList = new ArrayList<>();
        // 1. 创建一个 channel分组
        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
        ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());

        // 将自己加入群聊
        userIdSet.add(SessionUtil.getSession(ctx.channel()).getUserId());

        // 2. 筛选出待加入群聊的用户的channel和username
        userIdSet.forEach(userId -> {
            Channel channel = SessionUtil.getChannel(userId);
            channelGroup.add(channel);

            userNameList.add(SessionUtil.getSession(channel).getUserName());
        });

        // 3. 创建群聊创建结果的响应
        CreateGroupResponsePacket createGroupResponsePacket = new CreateGroupResponsePacket();
        String groupId = IDUtil.randomId();
        createGroupResponsePacket.setGroupId(groupId);
        createGroupResponsePacket.setUserNameList(userNameList);
        createGroupResponsePacket.setSuccess(true);

        // 4. 给每个客户端发送拉群通知
        channelGroup.writeAndFlush(createGroupResponsePacket);

        System.out.print("群创建成功，id 为[" + createGroupResponsePacket.getGroupId() + "], ");
        System.out.println("群里面有：" + createGroupResponsePacket.getUserNameList());

        // 5. 保存群组相关的信息
        SessionUtil.addChannelGroup(groupId, channelGroup);
    }
}
