package com.tencent.netty.serialize;

/**
 * @author v_xiangbluo
 * @date 2018/9/26 19:58
 */
public interface SerializerAlgorithm {
    /**
     * json序列化标识
     */
    byte JSON = 1;

    byte PROTOBUF = 2;
}
