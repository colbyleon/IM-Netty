package com.tencent.netty.attribute;

import com.tencent.netty.session.Session;
import io.netty.util.AttributeKey;

/**
 * @author v_xiangbluo
 * @date 2018/9/27 15:30
 */
public interface Attributes {
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
