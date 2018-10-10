package com.tencent.netty.common.util;

import com.tencent.netty.common.attribute.Attributes;
import com.tencent.netty.common.session.Session;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author v_xiangbluo
 * @date 2018/10/8 16:46
 */
public class SessionUtil {
    // userId -> channel映射
    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();

    // groupId -> ChannelGroup映射
    private static final Map<String, ChannelGroup> channelGroupMap = new ConcurrentHashMap<>();

    public static void bindSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            userIdChannelMap.remove(getSession(channel).getUserId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    public static boolean hasLogin(Channel channel) {
        return channel.attr(Attributes.SESSION).get() != null;
    }

    public static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(String userId) {
        return userIdChannelMap.get(userId);
    }

    public static void addChannelGroup(String groupId, ChannelGroup group) {
        channelGroupMap.put(groupId, group);
    }

    public static ChannelGroup getChannelGroup(String groupId) {
        return channelGroupMap.get(groupId);
    }

    public static void deleteChannelGroup(String groupId) {
        channelGroupMap.remove(groupId);
    }

    /**
     * 查看此用户是否在改群里
     */
    public static boolean isInGroup(String userId, String groupId) {
        if (groupId != null && userId != null) {
            ChannelGroup channels = channelGroupMap.get(groupId);
            if (channels != null) {
                return channels.stream()
                        .map(SessionUtil::getSession)
                        .anyMatch(session -> session.getUserId().equals(userId));
            }
        }
        return false;
    }
}
