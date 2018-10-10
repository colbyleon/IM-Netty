package com.tencent.netty.common.protocol;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;

/**
 * @author v_xiangbluo
 * @date 2018/9/26 19:49
 */
public abstract class Packet {
    /**
     * 协议版本
     */
    private Byte version = 1;
    /**
     * 指令
     */
    public abstract Byte getCommand();

    public Byte getVersion() {
        return version;
    }

}
