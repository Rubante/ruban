package com.ruban.monitor.server.domain;

public interface UserKey {

    public static final String PREFIX = "user:";

    public static final String USER_LIST = "list";

    public static final String ID = "id";

    public static final String ID_LIST = "ids";

    public static final String USERNAME_LIST = "usernames";

    /** 用户名 **/
    public static final String USERNAME = "username";

    /** 姓名 **/
    public static final String NAME = "name";

    /** 密码 **/
    public static final String PASSWORD = "password";
    /** 手机号 **/
    public static final String TEL = "tel";
    /** 短信报警 **/
    public static final String TELSTATE = "telState";
    /** 邮箱 **/
    public static final String EMAIL = "email";
    /** 邮箱报警 **/
    public static final String EMAILSTATE = "emailState";
    /** 微信 **/
    public static final String WECHAT = "wechat";
    /** 微信报警状态 **/
    public static final String WECHATSTATE = "wechatState";
    /** QQ **/
    public static final String QQ = "qq";

    /** 有效 **/
    public static int STATE_ENABLE = 1;
    /** 无效 **/
    public static int STATE_UNABLE = 0;

    /** 接受报警 **/
    public static int WARN_RECEIVE = 1;
    /** 不接受报警 **/
    public static int WARN_REJECT = 0;

    /** 用户报警项目 **/
    public static final String WARN_LIST = "warnList";

    /** CPU使用率 **/
    public static final String CPU_WARN = "cpuWarn";

    /** 命中率 **/
    public static final String HIT_WARN = "hitWarn";
}
