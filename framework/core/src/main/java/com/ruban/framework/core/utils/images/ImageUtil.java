package com.ruban.framework.core.utils.images;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import com.ruban.framework.core.utils.commons.RandomUtil;

/**
 * 图片工具类型
 * 
 * @author yjwang
 *
 */
public class ImageUtil {

    private static Logger logger = LoggerFactory.getLogger(ImageIO.class);

    /**
     * 判断文件是否为图片
     * 
     * @param file
     * @return
     */
    public static boolean isImage(File file) {
        boolean flag = false;
        try {
            BufferedImage bi = ImageIO.read(file);
            if (null == bi) {
                return flag;
            }
            flag = true;
        } catch (IOException e) {
            if (logger.isDebugEnabled()) {
                logger.debug("io exception", e);
            }
        }
        return flag;
    }

    /**
     * 判断文件是否是图片
     * 
     * @param pathname
     * @return
     */
    public static boolean isImage(String pathname) {
        File file = new File(pathname);
        return isImage(file);
    }

    /**
     * 将图片字节写入文件
     * 
     * @param b
     * @return
     */
    public static File getFileFromBytes(byte[] b) {
        File ret = null;
        BufferedOutputStream stream = null;
        try {
            // 无法确定图片类型，自定义扩展后缀标识为图片
            String tmp = RandomUtil.nextStringByDigit(10) + ".image";
            ret = new File(System.getProperty("java.io.tmpdir") + "/" + tmp);
            FileOutputStream fstream = new FileOutputStream(ret);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (IOException e) {
            if (logger.isDebugEnabled()) {
                logger.debug("io exception", e);
            }
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("io exception", e);
                    }
                }
            }
        }
        return ret;
    }

    /**
     * 把文件转化为字节数组
     * 
     * @param file
     * @return
     * @throws Exception
     */
    public static byte[] getBytesFromFile(File file) {
        InputStream in = null;
        ByteArrayOutputStream bos = null;
        byte[] byteArrray = null;
        try {
            in = new FileInputStream(file);
            byte[] buf = new byte[1024 * 10];
            bos = new ByteArrayOutputStream();
            int len;
            while ((len = in.read(buf)) != -1) {
                bos.write(buf, 0, len);
            }
            byteArrray = bos.toByteArray();
        } catch (IOException ex) {
            logger.error("file read error", ex);
        } finally {
            try {
                bos.close();
                in.close();
            } catch (IOException ex) {
                logger.error("close stream error", ex);
            }
        }

        return byteArrray;
    }

    /**
     * 获取图片的base64编码
     * 
     * @param imageFile
     * @return
     */
    public static String getBase64(String imageFile) {
        File file = new File(imageFile);
        byte[] imgBytes = getBytesFromFile(file);
        return Base64Utils.encodeToString(imgBytes);
    }

    /**
     * 获取图片的base64编码
     * 
     * @param imageFile
     * @return
     */
    public static String getBase64(File imageFile) {
        byte[] imgBytes = getBytesFromFile(imageFile);
        return Base64Utils.encodeToString(imgBytes);
    }
}
