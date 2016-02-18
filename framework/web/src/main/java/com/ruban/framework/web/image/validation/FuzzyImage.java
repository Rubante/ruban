package com.ruban.framework.web.image.validation;

import java.awt.image.BufferedImage;

/**
 * 验证码图片及字符
 * 
 * @author ruban
 *
 */
public class FuzzyImage {

    /** 字符 */
    private char[] chars;
    /** 图片 */
    private BufferedImage image;

    /**
     * 构造函数
     * 
     * @param chars
     * @param image
     */
    public FuzzyImage(char[] chars, BufferedImage image) {
        super();
        this.chars = chars;
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public char[] getChars() {
        return chars;
    }

}
