package com.ruban.framework.core.utils.commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

import org.springframework.util.Base64Utils;

/**
 * 对文件进行base64处理
 * 
 * @author ruban
 *
 */
public class Base64FileUtil implements Serializable {

    private static final long serialVersionUID = 0xf0e1229454c07fc2L;

    /**
     * 覆盖文件
     */
    public static final int OVERWRITE = 1;
    /**
     * 追加在原有文件后面
     */
    public static final int APPEND = 2;

    /**
     * <p>
     * 将文件编码为BASE64字符串
     * </p>
     * <p>
     * 大文件慎用，可能会导致内存溢出
     * </p>
     * 
     * @param filename
     *            文件绝对路径
     * @return
     * @throws Exception
     */
    public static String encodeFile(final String filename) throws IOException {
        byte[] bytes = file2bytes(filename);
        return Base64Utils.encodeToString(bytes);
    }

    /**
     * <p>
     * BASE64字符串转回文件
     * </p>
     * 
     * @param filename
     *            文件绝对路径
     * @param contentByBase64
     *            编码字符串
     * @throws Exception
     */
    public static void decodeFile(final String filename, final String contentByBase64) throws IOException {
        byte[] bytes = Base64Utils.decodeFromString(contentByBase64);
        bytes2file(filename, bytes);
    }

    /**
     * 文件转换为二进制数组
     * 
     * @param file
     *            文件对象
     * @return
     * @throws IOException
     */
    public static byte[] file2bytes(final File file) throws IOException {
        byte[] data = null;
        if (file.exists()) {
            FileInputStream fileInputStream = new FileInputStream(file);
            int length = fileInputStream.available();
            data = new byte[length];
            fileInputStream.read(data);
            fileInputStream.close();
        }
        return data;
    }

    /**
     * 给定带全路径的文件名获得字节数组
     * 
     * @param filename
     *            带全路径的文件名
     * @return 字节数组 byte[]
     * @throws IOException
     */
    public static byte[] file2bytes(final String filename) throws IOException {
        File file = new File(filename);
        return file2bytes(file);
    }

    /**
     * 把二进制数组写入文件
     * 
     * @param bytes
     *            二进制数组
     * @param filename
     *            带全路径的文件名
     * @throws IOException
     */
    public static void bytes2file(final String filename, final byte[] bytes) throws IOException {
        File destFile = new File(filename);
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }
        destFile.createNewFile();
        OutputStream outputStream = new FileOutputStream(destFile);
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * @param bytes
     * @param filename
     * @param action
     * @throws IOException
     */
    public static void bytes2file(byte[] bytes, String filename, int action) throws IOException {
        File file;
        byte[] data;
        switch (action) {
        case OVERWRITE:
            file = new File(filename);
            if (file.exists()) {
                file.delete();
            }
            bytes2file(filename, bytes);
            break;

        case APPEND:
            file = new File(filename);
            if (file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(file);
                int length = fileInputStream.available();
                data = new byte[length];
                fileInputStream.read(data);
                data = ByteUtil.merge(data, bytes);
                fileInputStream.close();

                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(data);
                fileOutputStream.flush();
                fileOutputStream.close();
            }
            break;

        default:
            bytes2file(bytes, filename, OVERWRITE);
        }
    }
}
