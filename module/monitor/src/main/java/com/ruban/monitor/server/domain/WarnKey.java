package com.ruban.monitor.server.domain;

public interface WarnKey {

    public static final String PREFIX = "smsTemplate:";

    public static final String CONFIG = "config";

    public static final String SMS_TEMPLATE_LIST = "templateList";

    public static final String ID = "id";

    /** 应用场景 **/
    public static final String SCENE = "scene";
    /** 短信签名 **/
    public static final String SIGNATURE = "signature";
    /** 模板ID **/
    public static final String TEMPLATE_ID = "templateId";
    /** 模板内容 **/
    public static final String CONTENT = "content";

    /** 短信平台 **/
    public static final String APP_KEY = "appKey";

    public static final String SECRET_KEY = "secretKey";

    /** 邮箱服务器 **/
    public static final String PREFIX_EMAIL = "email:";

    public static final String EMAIL_SMTP = "smtp";

    public static final String EMAIL_PORT = "port";

    public static final String EMAIL_ACCOUNT = "account";

    public static final String EMAIL_PASSWORD = "password";

    /** 测试接受账号 **/
    public static final String EMAIL_TEST = "testEmail";

    /** 微信配置 **/
    public static final String PREFIX_WECHAT = "wechat:";
}
