/*
 * Created on 2006-3-14
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ruban.framework.web.image.validation;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.font.LineMetrics;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;

/**
 * @author cschen
 * 
 */
public class ValidationImageHandler {

    /** 字符长度 */
    private static final int LENGTH = 4;

    /** 最大角度 */
    private static final int MAX_ANGLE = 20;// +-10

    /** 缩放比例 */
    private static final int MAX_SCALE = 5;// 1/5 = +-10%

    /** 剪切比例 */
    private static final int MAX_SHEAR = 2;// 1/4 = +-12.5%

    /** 色度范围 */
    private static final int[] MAX_COLOR = new int[] { 128, 128, 128 };

    /** 干扰圆直径 */
    private static final int MAX_R = 4;

    /** 干扰圆数量 */
    private static final int[] COUNT_R = new int[] { 8, 16 };

    /** 干扰线长度 */
    private static final int MAX_LEN = 8;

    /** 干扰线数量 */
    private static final int[] COUNT_RANGE = new int[] { 8, 16 };

    /** 字符间空 */
    private static final int LETTER_SPACE = 10;

    /** 字符大小 */
    private static final int FONT_BASE_SIZE = 20;

    /** 左右两端空 */
    private static final int HORIZONTAL_MARGIN_SPACE = 5;

    /** 字符顶端空间 */
    private static final int EXTRAHIGHT = 0;

    /** 图片高度 */
    private static final int IMAGEHEIGHT = 25;

    /** 图片高度 */
    private static final int IMAGEWIDTH = 100;

    private static final Font[] FONTS = new Font[] {
            // new Font("serif", Font.BOLD, FONT_BASE_SIZE),
            new Font("sanserif", Font.BOLD, FONT_BASE_SIZE), new Font("Monospaced", Font.BOLD, FONT_BASE_SIZE),
            new Font("Dialog", Font.BOLD, FONT_BASE_SIZE), new Font("DialogInput", Font.BOLD, FONT_BASE_SIZE) };

    private static final char[] ALLOWABLE_CHARACTERS = "2346789ABDEFHJKMNPRTUVWXYZ".toCharArray();

    private static final Graphics GRAPHIC = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB).createGraphics();

    private final SecureRandom random = new SecureRandom();

    /**
     * Get the validation string
     * 
     * @param number
     * @return
     */
    private char[] getValidationString(int number) {
        char[] chars = new char[number];
        boolean exist = false;
        for (int index = 0; index < number; index++) {
            char tmpChar = ALLOWABLE_CHARACTERS[random.nextInt(ALLOWABLE_CHARACTERS.length)];
            exist = false;
            for (int subIndex = 0; subIndex < index; subIndex++) {
                if (chars[subIndex] == tmpChar) {
                    exist = true;
                    break;
                }
            }
            if (exist)
                index--;
            else
                chars[index] = tmpChar;
        }
        return chars;
    }

    /**
     * Get the random font
     * 
     * @return
     */
    private Font getRandomFont(char ch) {

        Font font;
        do {
            font = FONTS[random.nextInt(FONTS.length)];
        } while (!font.canDisplay(ch));
        AffineTransform fontAT = new AffineTransform();
        // scale
        if (random.nextBoolean())
            fontAT.scale(1 + (random.nextDouble() - 0.5) / MAX_SCALE, 1 + (random.nextDouble() - 0.5) / MAX_SCALE);
        // shear
        fontAT.shear((random.nextDouble() - 0.5) / MAX_SHEAR, (random.nextDouble() - 0.5) / MAX_SHEAR);
        // rotate
        fontAT.rotate(Math.toRadians(random.nextInt(MAX_ANGLE) - MAX_ANGLE / 2));
        font = font.deriveFont(fontAT);
        return font;
    }

    private Color getRandomColor() {
        int r, g, b;
        do {
            r = random.nextInt(MAX_COLOR[0] + 256) - MAX_COLOR[0];
            g = random.nextInt(MAX_COLOR[1] + 256) - MAX_COLOR[1];
            b = random.nextInt(MAX_COLOR[2] + 256) - MAX_COLOR[2];
            if (r < 0)
                r = 0;
            if (g < 0)
                g = 0;
            if (b < 0)
                b = 0;
        } while (r * 0.3 + g * 0.59 + b * 0.11 > 128);
        return new Color(r, g, b);
    }

    public FuzzyImage service() {

        // int imageHeight = 0;
        // int imageWidth = HORIZONTAL_MARGIN_SPACE * 2 + (length - 1) *
        // LETTER_SPACE;
        // for (int index = 0; index < length; index ++) {
        // fontMetrics = GRAPHIC.getFontMetrics(fonts[index]);
        // lineMetrics = fontMetrics.getLineMetrics(chars, index, 1, GRAPHIC);
        // if (imageHeight < lineMetrics.getHeight())
        // imageHeight = (int) lineMetrics.getHeight();
        // imageWidth += fontMetrics.charWidth(chars[index]);
        // }
        // if (imageHeight < 30) {
        // extraHeight = (30 - imageHeight) / 2;
        // imageHeight = 30;
        // }

        BufferedImage image = new BufferedImage(IMAGEWIDTH, IMAGEHEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();

        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, IMAGEWIDTH, IMAGEHEIGHT);
        // graphics.setColor(Color.black);
        // graphics.drawRect(0, 0, imageWidth - 1, imageHeight - 1);

        int count = random.nextInt(COUNT_R[1] - COUNT_R[0]) + COUNT_R[0];
        graphics.setColor(Color.gray);
        for (int i = 0; i < count; i++) {
            // graphics.setColor(getRandomColor());
            graphics.fillOval(random.nextInt(IMAGEWIDTH), random.nextInt(IMAGEHEIGHT), random.nextInt(MAX_R),
                    random.nextInt(MAX_R));
        }

        char[] chars = getValidationString(LENGTH);
        int x = HORIZONTAL_MARGIN_SPACE;
        for (int index = 0; index < LENGTH; index++) {
            Font font = getRandomFont(chars[index]);
            graphics.setFont(font);
            graphics.setColor(getRandomColor());
            FontMetrics fontMetrics = graphics.getFontMetrics(font);
            LineMetrics lineMetrics = fontMetrics.getLineMetrics(chars, index, index + 1, GRAPHIC);
            // ***Add by Maverick****
            // if (y < 17)
            // y = 17;
            // ***end of add*********
            graphics.drawString(String.valueOf(chars[index]), x, (int) lineMetrics.getAscent() + EXTRAHIGHT);
            x += LETTER_SPACE + fontMetrics.charWidth(chars[index]);
        }

        // ***For generate random line to protect image hard to
        count = random.nextInt(COUNT_RANGE[1] - COUNT_RANGE[0]) + COUNT_RANGE[0];
        graphics.setColor(Color.gray);
        for (int i = 0; i < count; i++) {
            // graphics.setColor(getRandomColor());
            int x0 = random.nextInt(IMAGEWIDTH);
            int y0 = random.nextInt(IMAGEHEIGHT);
            // int dtx = sign(dy);
            // int dty = - sign(dx);
            graphics.drawLine(x0, y0, x0 + random.nextInt(MAX_LEN) - MAX_LEN / 2, y0 + random.nextInt(MAX_LEN)
                    - MAX_LEN / 2);
            // graphics.drawLine(x - dtx, y, x + dx - dtx, y + dy);
            // graphics.drawLine(x, y - dty, x + dx, y + dy - dty);
            // graphics.drawLine(x - dtx, y - dty, x + dx - dtx, y + dy - dty);
        }

        graphics.dispose();
        image.flush();

        return new FuzzyImage(chars, image);
    }

    // private int sign(int d) {
    // if (d > 0)
    // return 1;
    // if (d < 0)
    // return -1;
    // return 0;
    // }

}
