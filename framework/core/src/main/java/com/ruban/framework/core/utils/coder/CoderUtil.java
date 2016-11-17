package com.ruban.framework.core.utils.coder;

/**
 * 进制编码工具类
 * 
 * @author yjwang
 *
 */
public class CoderUtil {

    /**
     * 转换为十六进制格式（小写）
     * 
     * @param byteArr
     * @return
     */
    public static final String bytes2HexStr(byte byteArr[]) {
        Coder coder = new HexCoder();
        return coder.encode(byteArr);
    }

    /**
     * 转换为十六进制
     * 
     * @param byt
     * @return
     */
    public static final String byte2HexStr(byte byt) {
        Coder coder = new HexCoder();
        return coder.encode(new byte[] { byt });
    }
}
