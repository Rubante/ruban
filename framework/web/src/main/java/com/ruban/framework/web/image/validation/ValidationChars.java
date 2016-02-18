package com.ruban.framework.web.image.validation;

import java.io.Serializable;

/**
 * 验证码
 * 
 * @author ruban
 *
 */
public class ValidationChars implements Serializable {

    private static final long serialVersionUID = 5627572181881573960L;

    /** 验证码 */
    private String chars;
    /** 产生时间 */
    private long time;
    /** 错误次数 */
    private int count;

    public ValidationChars(String chars, long time, int count) {
        this.chars = chars;
        this.time = time;
        this.count = count;
    }

    /**
     * @return 返回 chars。
     */
    public String getChars() {
        return chars;
    }

    /**
     * @return 返回 time。
     */
    public long getTime() {
        return time;
    }

    /**
     * @return 返回 count。
     */
    public int getCount() {
        return count;
    }

    public void resetCount() {
        this.count = 0;
    }
}
