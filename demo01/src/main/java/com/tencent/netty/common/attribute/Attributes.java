package com.tencent.netty.common.attribute;

import com.tencent.netty.common.session.Session;
import io.netty.util.AttributeKey;

/**
 * @author v_xiangbluo
 * @date 2018/9/27 15:30
 */
public interface Attributes {
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
