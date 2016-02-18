package com.ruban.framework.web.image.validation;

import javax.servlet.http.HttpSession;
import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 图片验证码
 * 
 * @author ruban
 *
 */
public class ImageChecker {

    /** 图片验证码有效期 */
    private int expire;

    private final static Logger logger = LoggerFactory.getLogger(ImageChecker.class);

    public void check(HttpSession session, String chares) throws Exception {
        ValidationChars validationChars = null;
        try {
            // 得到验证图形对象（验证码，产生时间，错误次数）
            validationChars = (ValidationChars) session.getAttribute(ImageServlet.VALIDATION);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("Authentication Verification code execution error:", e);
            }
            throw new Exception("error_default");
        }

        // 判断超时
        if (validationChars.getTime() + expire * 1000L < System.currentTimeMillis()) {
            throw new ValidationException("validation.validationImage.timeout");
        }

        // 使用之后，从会话中删除
        session.removeAttribute(ImageServlet.VALIDATION);

        // 判断页面输入的验证码是否正确
        if (validationChars.getChars().equalsIgnoreCase(chares)) {
            validationChars.resetCount();
            return;
        }
        throw new Exception("imageChar.is.error");

    }

    /**
     * @param expire
     *            要设置的 expire。
     */
    public void setExpire(int expire) {
        this.expire = expire;
    }

}
