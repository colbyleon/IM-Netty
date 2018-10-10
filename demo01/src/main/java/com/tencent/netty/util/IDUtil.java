package com.tencent.netty.util;

import java.util.UUID;

/**
 * @author v_xiangbluo
 * @date 2018/10/9 14:55
 */
public class IDUtil {
    public static String randomId() {
        return UUID.randomUUID().toString().split("-")[0];
    }
}
