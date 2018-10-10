package com.tencent.netty.serialize.impl;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.tencent.netty.serialize.Serializer;
import com.tencent.netty.serialize.SerializerAlgorithm;

import java.io.IOException;

/**
 * @author v_xiangbluo
 * @date 2018/9/27 10:00
 */
public class ProtoSerializer implements Serializer {
    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.PROTOBUF;
    }

    @Override
    public <T> byte[] serialize(T t) {

        Codec<T> codec = ProtobufProxy.create((Class<T>) t.getClass());

        try {
            return codec.encode(t);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {

        Codec<T> codec = ProtobufProxy.create(clazz);
        try {
            return codec.decode(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
