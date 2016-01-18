package com.ruban.framework.core.utils.commons;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.<br/>
 * User: I8800<br/>
 * Date: 13-4-16<br/>
 * Time: 上午9:16<br/>
 * To change this template use File | Settings | File Templates.
 */
public class MessageDigestUtil {

    /**
     * 根据给定摘要算法和源字节数组计算摘要值
     * 
     * @param srcBytes
     *            源字节数组
     * @param algorithm
     *            摘要算法
     * @return 摘要值 字节数组
     * @throws NoSuchAlgorithmException
     *             摘要算法错误异常
     */
    public static byte[] digest(byte[] srcBytes, String algorithm) throws NoSuchAlgorithmException {

        MessageDigest digest = MessageDigest.getInstance(algorithm);
        digest.update(srcBytes);
        byte[] digestBytes = digest.digest();
        return digestBytes;
    }

    /**
     * 计算文件的摘要值
     * 
     * @param file
     *            文件路径
     * @param algorithm
     *            算法
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public static byte[] digest(String file, String algorithm) throws NoSuchAlgorithmException, IOException {
        MessageDigest digest = MessageDigest.getInstance(algorithm);

        FileInputStream fis = new FileInputStream(file);
        ByteBuffer buffer = fis.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
        digest.update(buffer);

        return digest.digest();

    }
}
