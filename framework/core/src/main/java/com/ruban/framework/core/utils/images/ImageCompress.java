package com.ruban.framework.core.utils.images;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 图片压缩
 * 
 * @author yjwang
 *
 */
public class ImageCompress {

    private static Logger logger = LoggerFactory.getLogger(ImageCompress.class);

    private static BufferedImage read(String srcImgPath) {
        BufferedImage srcImage = null;
        try {
            FileInputStream in = new FileInputStream(srcImgPath);
            srcImage = javax.imageio.ImageIO.read(in);
        } catch (IOException e) {
            logger.error("图片无法读取", e);
        }
        return srcImage;
    }

    /**
     * 将图片按照指定的图片尺寸压缩
     * 
     * @param srcImgPath
     *            源图片路径
     * @param outImgPath
     *            输出的压缩图片的路径
     * @param new_w
     *            压缩后的图片宽
     * @param new_h
     *            压缩后的图片高
     */
    public static void compress(String srcImgPath, String outImgPath, int new_w, int new_h) {
        BufferedImage src = read(srcImgPath);
        dispose(src, outImgPath, new_w, new_h);
    }

    /**
     * 等比例压缩
     * 
     * @param srcImgPath
     * @param outImgPath
     * @param scale
     */
    public static void scaleImage(String srcImgPath, String outImgPath, int scale) {
        // 得到图片
        BufferedImage src = read(srcImgPath);
        if (null != src) {
            // 旧宽
            int old_w = src.getWidth();
            // 旧高
            int old_h = src.getHeight();
            // 新宽
            int new_w = 0;
            // 新高
            int new_h = 0;

            // 等比例压缩
            new_h = (int) Math.round(old_h * ((float) scale / 100));
            new_w = (int) Math.round(old_w * ((float) scale / 100));

            dispose(src, outImgPath, new_w, new_h);
        }
    }

    /**
     * 指定宽度压缩来压缩图片
     * 
     * @param srcImgPath
     *            源图片路径
     * @param outImgPath
     *            输出的压缩图片的路径
     * @param width
     *            宽度
     */
    public static void compressByWidth(String srcImgPath, String outImgPath, int width) {
        // 得到图片
        BufferedImage src = read(srcImgPath);
        if (null != src) {
            // 旧宽
            int old_w = src.getWidth();
            // 旧高
            int old_h = src.getHeight();
            // 新宽
            int new_w = 0;
            // 新高
            int new_h = 0;

            new_w = width;
            // 等比例缩放
            new_h = (int) Math.round(old_h * ((float) width / old_w));

            dispose(src, outImgPath, new_w, new_h);
        }
    }

    /**
     * 指定宽度压缩来压缩图片
     * 
     * @param srcImgPath
     *            源图片路径
     * @param outImgPath
     *            输出的压缩图片的路径
     * @param width
     *            宽度
     */
    public static void compressByHeight(String srcImgPath, String outImgPath, int height) {
        // 得到图片
        BufferedImage src = read(srcImgPath);
        if (null != src) {
            // 旧宽
            int old_w = src.getWidth();
            // 旧高
            int old_h = src.getHeight();
            // 新宽
            int new_w = 0;
            // 新高
            int new_h = 0;

            new_h = height;
            // 等比例缩放
            new_w = (int) Math.round(old_w * ((float) height / old_h));

            dispose(src, outImgPath, new_w, new_h);
        }
    }

    /**
     * 指定长或者宽的最大值来压缩图片
     * 
     * @param srcImgPath
     *            源图片路径
     * @param outImgPath
     *            输出的压缩图片的路径
     * @param maxLength
     *            长或者宽的最大值
     */
    public static void compress(String srcImgPath, String outImgPath, int maxLength) {
        // 得到图片
        BufferedImage src = read(srcImgPath);
        if (null != src) {
            int old_w = src.getWidth();
            // 得到源图宽
            int old_h = src.getHeight();
            // 得到源图长
            int new_w = 0;
            // 新图的宽
            int new_h = 0;
            // 新图的长
            // 根据图片尺寸压缩比得到新图的尺寸
            if (old_w > old_h) {
                // 图片要缩放的比例
                new_w = maxLength;
                new_h = (int) Math.round(old_h * ((float) maxLength / old_w));
            } else {
                new_w = (int) Math.round(old_w * ((float) maxLength / old_h));
                new_h = maxLength;
            }
            dispose(src, outImgPath, new_w, new_h);
        }
    }

    /**
     * 处理图片
     * 
     * @param src
     * @param outImgPath
     * @param new_w
     * @param new_h
     */
    private synchronized static void dispose(BufferedImage src, String outImgPath, int new_w, int new_h) {
        // 得到图片
        int old_w = src.getWidth();
        // 得到源图宽
        int old_h = src.getHeight();
        // 得到源图长
        BufferedImage newImg = null;
        // 判断输入图片的类型
        switch (src.getType()) {
        case 13:
            newImg = new BufferedImage(new_w, new_h, BufferedImage.TYPE_4BYTE_ABGR);
            break;
        default:
            newImg = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
            break;
        }
        Graphics2D g = newImg.createGraphics();
        // 从原图上取颜色绘制新图
        g.drawImage(src, 0, 0, old_w, old_h, null);
        g.dispose();
        // 根据图片尺寸压缩比得到新图的尺寸
        newImg.getGraphics().drawImage(src.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0, 0, null);
        // 调用方法输出图片文件
        output(outImgPath, newImg);
    }

    /**
     * 输出图片到指定地方
     * 
     * @param outImgPath
     * @param newImg
     */
    private static void output(String outImgPath, BufferedImage newImg) {
        // 判断输出的文件夹路径是否存在，不存在则创建
        File file = new File(outImgPath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        // 输出到文件流
        try {
            ImageIO.write(newImg, outImgPath.substring(outImgPath.lastIndexOf(".") + 1), new File(outImgPath));
        } catch (FileNotFoundException e) {
            logger.error("image target file not exist", e);
        } catch (IOException e) {
            logger.error("image write error", e);
        }
    }
}
