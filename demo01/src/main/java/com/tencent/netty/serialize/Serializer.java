package com.tencent.netty.serialize;


import com.tencent.netty.serialize.impl.JSONSerializer;

/**
 * @author v_xiangbluo
 * @date 2018/9/26 19:54
 */
public interface Serializer {
    /**
     * json序列化
     */
    byte JSON_SERIALIZER = 1;

    Serializer DEFAULT = new JSONSerializer();

    /**
     * 序列化算法
     */
    byte getSerializerAlgorithm();

    /**
     * java对象转换成二进制
     */
    <T> byte[] serialize(T t);

    /**
     * 二进制转换成 java 对象
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
