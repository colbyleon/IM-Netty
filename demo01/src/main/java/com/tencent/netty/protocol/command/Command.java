package com.tencent.netty.protocol.command;

/**
 * @author v_xiangbluo
 * @date 2018/9/26 19:52
 */
public interface Command {
    Byte LOGIN_REQUEST = 1;
    Byte LOGIN_RESPONSE = 2;

    Byte LOGOUT_REQUEST = 3;
    Byte LOGOUT_RESPONSE = 4;

    Byte MESSAGE_REQUEST = 5;
    Byte MESSAGE_RESPONSE = 6;

    Byte CREATE_GROUP_REQUEST = 7;
    Byte CREATE_GROUP_RESPONSE = 8;

    Byte JOIN_GROUP_REQUEST = 9;
    Byte JOIN_GROUP_RESPONSE = 10;

    Byte QUIT_GROUP_REQUEST = 11;
    Byte QUIT_GROUP_RESPONSE = 12;

    Byte LIST_GROUP_MEMBERS_REQUEST = 13;
    Byte LIST_GROUP_MEMBERS_RESPONSE = 14;

    Byte GROUP_MESSAGE_REQUEST = 15;
    Byte GROUP_MESSAGE_RESPONSE = 16;

    Byte HEART_BEAT_REQUEST = 17;
    Byte HEART_BEAT_RESPONSE = 18;
}
