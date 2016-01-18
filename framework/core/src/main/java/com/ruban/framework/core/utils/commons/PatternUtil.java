/**
 * File name：PatternUtil.java
 * Creation date ：2012-11-01
 * Copyright (c) 2012 tendyron
 * All rights reserved.
 */
package com.ruban.framework.core.utils.commons;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 正则表达式工具类
 * 
 * @author zjg
 * @see 2013-6-19
 */
public class PatternUtil {

    private final static Logger logger = LoggerFactory.getLogger(PatternUtil.class);

    /**
     * 正则表达式数据校验
     * 
     * @param source
     *            String 需要校验的数据
     * @param regEx
     *            String 校验规则(正则表达式)
     * @param isSentive
     *            boolean 大小写是否敏感 true-大小写敏感 false-大小写不敏感
     * @return int
     */
    public static boolean checkString(String source, String regEx, boolean isSentive) {
        if (StringUtil.isNullOrEmpty(source) || StringUtil.isNullOrEmpty(regEx)) {
            if (logger.isErrorEnabled()) {
                logger.error("source is " + source + ";" + "regEx is" + regEx);
            }
            throw new IllegalArgumentException("source or regEx is null!");
        }
        Pattern p = null;
        if (isSentive) { // 大小写敏感
            p = Pattern.compile(regEx);
        } else { // 不区分大小写
            p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        }

        Matcher m = p.matcher(source);
        boolean rs = m.find();
        if (rs) {
            if (logger.isDebugEnabled()) {
                logger.debug("Regular expression matching success!");
            }
        } else if (logger.isDebugEnabled()) {
            logger.debug("Regular expression matching fail!");
        }
        return rs;
    }

    /**
     * 返回正则表达式匹配器
     * 
     * @param source
     * @param regEx
     * @param isSentive
     * @return
     */
    public static Matcher matchString(String source, String regEx, boolean isSentive) {
        if (StringUtil.isNullOrEmpty(source) || StringUtil.isNullOrEmpty(regEx)) {
            if (logger.isErrorEnabled()) {
                logger.error("source is " + source + ";" + "regEx is" + regEx);
            }
            throw new IllegalArgumentException("source or regEx is null!");
        }
        Pattern p = null;
        if (isSentive) { // 大小写敏感
            p = Pattern.compile(regEx);
        } else { // 不区分大小写
            p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        }

        Matcher m = p.matcher(source);
        return m;
    }
}
