package com.tencent.netty.common.serialize.impl;

import com.alibaba.fastjson.JSON;
import com.tencent.netty.common.serialize.Serializer;
import com.tencent.netty.common.serialize.SerializerAlgorithm;

/**
 * @author v_xiangbluo
 * @date 2018/9/26 20:00
 */
public class JSONSerializer implements Serializer {
    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public <T> byte[] serialize(T t) {
        return JSON.toJSONBytes(t);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
