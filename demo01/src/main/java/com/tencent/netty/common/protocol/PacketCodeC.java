package com.tencent.netty.common.protocol;

import com.tencent.netty.common.protocol.request.*;
import com.tencent.netty.common.protocol.response.*;
import com.tencent.netty.common.serialize.Serializer;
import com.tencent.netty.common.serialize.SerializerAlgorithm;
import com.tencent.netty.common.serialize.impl.ProtoSerializer;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

import static com.tencent.netty.common.protocol.command.Command.*;

/**
 * @author v_xiangbluo
 * @date 2018/9/26 20:06
 */
public class PacketCodeC {
    // 魔法值
    public static final int MAGIC_NUMBER = 0x12345678;
    public static final PacketCodeC INSTANCE = new PacketCodeC();

    private static final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private static final Map<Byte, Serializer> serializerMap;

    static {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(LOGOUT_REQUEST, LogoutRequestPacket.class);
        packetTypeMap.put(LOGOUT_RESPONSE, LogoutResponsePacket.class);
        packetTypeMap.put(MESSAGE_REQUEST, MessageReqPacket.class);
        packetTypeMap.put(MESSAGE_RESPONSE, MessageResponsePacket.class);
        packetTypeMap.put(CREATE_GROUP_REQUEST, CreateGroupRequestPacket.class);
        packetTypeMap.put(CREATE_GROUP_RESPONSE, CreateGroupResponsePacket.class);
        packetTypeMap.put(JOIN_GROUP_REQUEST, JoinGroupRequestPacket.class);
        packetTypeMap.put(JOIN_GROUP_RESPONSE, JoinGroupResponsePacket.class);
        packetTypeMap.put(QUIT_GROUP_REQUEST, QuitGroupRequestPacket.class);
        packetTypeMap.put(QUIT_GROUP_RESPONSE, QuitGroupResponsePacket.class);
        packetTypeMap.put(LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestPacket.class);
        packetTypeMap.put(LIST_GROUP_MEMBERS_RESPONSE, ListGroupMembersResponsePacket.class);
        packetTypeMap.put(GROUP_MESSAGE_REQUEST, GroupMessageReqPacket.class);
        packetTypeMap.put(GROUP_MESSAGE_RESPONSE, GroupMessageRespPacket.class);
        packetTypeMap.put(HEART_BEAT_REQUEST , HeartBeatRequestPacket.class);
        packetTypeMap.put(HEART_BEAT_RESPONSE, HeartBeatResponsePacket.class);


        serializerMap = new HashMap<>();
        Serializer serializer = Serializer.DEFAULT;
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);

        ProtoSerializer protoSerializer = new ProtoSerializer();
        serializerMap.put(protoSerializer.getSerializerAlgorithm(), protoSerializer);

    }

    public void encode(ByteBuf byteBuf, Packet packet, byte algorithm) {
        // ioBuffer 会尽量分配直接内存
        // ByteBuf byteBuf = alloc.ioBuffer();

        // 序列化 Java 对象
//        Class<? extends Packet> clazz = packetTypeMap.get(packet.getCommand());

        byte[] bytes = serializerMap.get(algorithm).serialize(packet);

        // 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(algorithm);
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

    }

    public void encode(ByteBuf byteBuf, Packet packet) {
        encode(byteBuf, packet, SerializerAlgorithm.JSON);
    }

    public Packet decode(ByteBuf byteBuf) {
        // 1. 跳过magic_number
        byteBuf.skipBytes(4);
        // 2. 跳过协议版本号
        byteBuf.skipBytes(1);

        // 3. 序列化算法
        byte serializerAlgorithm = byteBuf.readByte();

        // 4. 指令
        byte command = byteBuf.readByte();

        // 5. 数据包长度
        int len = byteBuf.readInt();

        // 6. 数据
        byte[] bytes = new byte[len];
        byteBuf.readBytes(bytes);

        // 7. 根据指令选择类型,根据算法值选择算法
        Class<? extends Packet> requestPacket = getRequestType(command);
        Serializer serializer = getSerializer(serializerAlgorithm);

        return serializer.deserialize(requestPacket, bytes);
    }

    private Serializer getSerializer(byte serializerAlgorithm) {
        return serializerMap.get(serializerAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {
        return packetTypeMap.get(command);
    }

}
