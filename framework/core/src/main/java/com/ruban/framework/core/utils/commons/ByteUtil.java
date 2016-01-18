package com.ruban.framework.core.utils.commons;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ByteUtil {

    private final static Logger logger = LoggerFactory.getLogger(ByteUtil.class);

    private static char[] HexCode = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    /**
     * 把整数转为字节
     * 
     * @param number
     *            0-255
     * @return
     */
    public static byte int2byte(int number) {
        return (byte) (number & 0xff);
    }

    /**
     * 把短整数转为双字节数组
     * 
     * @param number
     * @return
     */
    public static byte[] short2bytes(short number) {
        int i = number;
        byte[] bytes = new byte[2];
        bytes[1] = (byte) (i & 0xff);
        i = i >> 8;
        bytes[0] = (byte) (i & 0xff);
        return bytes;
    }

    /**
     * 把整数转为字节数组
     * 
     * @param number
     * @return
     */
    public static byte[] int2bytes(final int number) {
        String numberStringByHex = Integer.toHexString(number);
        numberStringByHex = ByteUtil.getDoubleStringByHex(numberStringByHex);
        return ByteUtil.hexString2Bytes(numberStringByHex);
    }

    /**
     * 把整数转为给定长度的字节数组
     * 
     * @param number
     * @param chars
     * @return
     */
    public static byte[] int2bytes(final int number, final int chars) {
        String numberStringByHex = Integer.toHexString(number);
        numberStringByHex = ByteUtil.getDoubleStringByHex(numberStringByHex);
        byte[] bytes = new byte[chars];
        byte[] srcBytes = ByteUtil.hexString2Bytes(numberStringByHex);
        int srcBytesLength = srcBytes.length;
        int pos = 0;
        if (srcBytesLength <= chars) {
            for (int i = 0; i < chars - srcBytesLength; i++) {
                if (number >= 0) {
                    bytes[pos] = (byte) 0x00;
                } else {
                    bytes[pos] = (byte) 0xff;
                }
                pos++;
            }
            System.arraycopy(srcBytes, 0, bytes, pos, srcBytes.length);
        } else {
            System.arraycopy(srcBytes, srcBytes.length - bytes.length, bytes, 0, bytes.length);
        }
        return bytes;
    }

    /**
     * 把长整数转为字节数组
     * 
     * @param number
     * @return
     */
    public static byte[] long2bytes(final long number) {
        String numberStringByHex = Long.toHexString(number);
        numberStringByHex = ByteUtil.getDoubleStringByHex(numberStringByHex);
        return ByteUtil.hexString2Bytes(numberStringByHex);
    }

    /**
     * 把长整数转为给定长度的字节数组
     * 
     * @param number
     * @param chars
     * @return
     */
    public static byte[] long2bytes(final long number, final int chars) {
        String numberStringByHex = Long.toHexString(number);
        numberStringByHex = ByteUtil.getDoubleStringByHex(numberStringByHex);
        byte[] bytes = new byte[chars];
        byte[] srcBytes = ByteUtil.hexString2Bytes(numberStringByHex);
        int srcBytesLength = srcBytes.length;
        int pos = 0;
        if (srcBytesLength <= chars) {
            for (int i = 0; i < chars - srcBytesLength; i++) {
                if (number >= 0) {
                    bytes[pos] = (byte) 0x00;
                } else {
                    bytes[pos] = (byte) 0xff;
                }
                pos++;
            }
            System.arraycopy(srcBytes, 0, bytes, pos, srcBytes.length);
        } else {
            System.arraycopy(srcBytes, srcBytes.length - bytes.length, bytes, 0, bytes.length);
        }
        return bytes;
    }

    /**
     * @param bytes
     * @return
     */
    public static int bytes2int(byte[] bytes) {
        if (bytes.length > 4) {
            throw new RuntimeException("the length of bytes cannot be greater than four.");
        }
        int i = 0;
        for (int j = 0; j < 4; j++) {
            i <<= 8;
            i |= bytes[j] & 0xff;
        }
        return i;
    }

    /**
     * bytes2long
     * 
     * @param bytes
     * @return
     */
    public static long bytes2long(byte[] bytes) {
        if (bytes.length > 8) {
            throw new RuntimeException("the length of bytes cannot be greater than eight.");
        }
        long l = 0;
        for (int i = 0; i < bytes.length; i++) {
            l <<= 8;
            l |= bytes[i] & 0xff;
        }
        return l;
    }

    /**
     * 把大整型数转为字节数组
     * 
     * @param number
     * @return
     */
    public static byte[] BigInteger2bytes(final BigInteger number) {
        String numberStringByHex = number.toString(Constants.HEX);
        numberStringByHex = ByteUtil.getDoubleStringByHex(numberStringByHex);
        return ByteUtil.hexString2Bytes(numberStringByHex);
    }

    /**
     * 把大整型数转为给定长度字节数组
     * 
     * @param number
     * @param chars
     * @return
     */
    public static byte[] BigInteger2bytes(final BigInteger number, final int chars) {
        String numberStringByHex = number.toString(Constants.HEX);
        byte[] bytes = new byte[chars];
        numberStringByHex = ByteUtil.getDoubleStringByHex(numberStringByHex);
        byte[] srcBytes = ByteUtil.hexString2Bytes(numberStringByHex);
        int srcBytesLength = srcBytes.length;
        int pos = 0;
        if (srcBytesLength <= chars) {
            for (int i = 0; i < chars - srcBytesLength; i++) {
                bytes[pos] = (byte) 0x00;
                pos++;
            }
            System.arraycopy(srcBytes, 0, bytes, pos, srcBytes.length);
        } else {
            System.arraycopy(srcBytes, srcBytes.length - bytes.length, bytes, 0, bytes.length);
        }
        return bytes;
    }

    /**
     * 把字符串转为字节数组
     * 
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    public static byte[] string2bytes(final String str) throws UnsupportedEncodingException {
        return ByteUtil.string2bytes(str, str.getBytes(Constants.ENCODING).length);
    }

    /**
     * 把字符串转为给定长度的字节数组
     * 
     * @param str
     * @param length
     * @return byte[]
     * @throws UnsupportedEncodingException
     */
    public static byte[] string2bytes(final String str, final int length) throws UnsupportedEncodingException {
        String string = str;
        if (string == null) {
            string = Constants.EMPTY;
        }
        byte[] bytes = new byte[length];
        byte[] strBytes = string.getBytes(Constants.ENCODING);
        int strLength = strBytes.length;
        for (int i = 0; i < length; i++) {
            if (i < strLength) {
                bytes[i] = strBytes[i];
            } else {
                bytes[i] = (byte) 0x00;
            }
        }
        return bytes;
    }

    /**
     * 把日期型转为字节数组
     * 
     * @param date
     * @return
     */
    public static byte[] date2bytes(final Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar2bytes(calendar);
    }

    /**
     * 把日历型转字节数组
     * 
     * @param calendar
     * @return
     */
    public static byte[] calendar2bytes(final Calendar calendar) {
        long timeInMillis = calendar.getTimeInMillis();
        return long2bytes(timeInMillis);
    }

    /**
     * 查询原字节数组中包含子字节数组的位置
     * 
     * @param srcBytes
     * @param subBytes
     * @return
     */
    public static int indexOf(final byte[] srcBytes, final byte[] subBytes) {
        String srcHexString = ByteUtil.bytes2HexString(srcBytes);
        if (logger.isDebugEnabled()) {
            logger.debug(srcHexString);
        }
        String subHexString = ByteUtil.bytes2HexString(subBytes);
        if (logger.isDebugEnabled()) {
            logger.debug(subHexString);
        }
        // 查找包含的位置
        int pos = srcHexString.indexOf(subHexString);
        if (logger.isDebugEnabled()) {
            logger.debug(String.valueOf(pos));
        }
        while ((pos % 2 == 1) && (pos < srcHexString.length())) {// 错位包含时，则从下一个继续计算
            pos = srcHexString.indexOf(subHexString, pos + 1);
            if (logger.isDebugEnabled()) {
                logger.debug(String.valueOf(pos));
            }
        }
        // 如果hexString中包含
        if (pos > 0) {
            pos = pos / 2;
        }
        return pos;
    }

    /**
     * 查询原字节数组中是否包含子字节数组
     * 
     * @param srcBytes
     * @param subBytes
     * @return
     */
    public static boolean contains(final Byte[] srcBytes, final Byte[] subBytes) {
        return Arrays.asList(srcBytes).containsAll(Arrays.asList(subBytes));
    }

    /**
     * 查询原字节数组中是否包含子字节数组
     * 
     * @param srcBytes
     * @param subBytes
     * @return
     */
    public static boolean contains(final byte[] srcBytes, final byte[] subBytes) {
        return contains(toBytes(srcBytes), toBytes(subBytes));
    }

    /**
     * 把整数转为字符
     * 
     * @param i
     * @return
     */
    public static char int2char(final int i) {
        return (char) i;
    }

    /**
     * 把字节转为字符
     * 
     * @param b
     * @return char
     */
    public static char byte2char(final byte b) {
        return (char) b;
    }

    /**
     * 把字符转为整数
     * 
     * @param c
     * @return int
     */
    public static int char2int(final char c) {
        return c & 0xff;
    }

    /**
     * 把字节数组转为浮点数
     * 
     * @param bytes
     * @return
     */
    public static float bytes2float(final byte[] bytes) {
        byte[] zeroBytes = { 0x00 };
        byte[] tbytes = bytes;
        for (int i = 0; i < 4 - tbytes.length; i++) {
            tbytes = ByteUtil.merge(zeroBytes, tbytes);
        }
        int accum = 0;
        accum = accum | (tbytes[0] & 0xff) << 0;
        accum = accum | (tbytes[1] & 0xff) << 8;
        accum = accum | (tbytes[2] & 0xff) << 16;
        accum = accum | (tbytes[3] & 0xff) << 24;
        return Float.intBitsToFloat(accum);
    }

    /**
     * 把浮点数转为字节数组
     * 
     * @param f
     * @return
     */
    public static byte[] float2bytes(final float f) {
        int i = Float.floatToIntBits(f);
        return int2bytes(i);
    }

    /**
     * 把双精度转为字节数组
     * 
     * @param d
     * @return
     */
    public static byte[] double2bytes(final double d) {
        long l = Double.doubleToLongBits(d);
        return long2bytes(l);
    }

    /**
     * 字符数组转双精度数
     * 
     * @param bytes
     * @return
     */
    public static double bytes2double(final byte[] bytes) {
        byte[] zeroBytes = { 0x00 };
        byte[] tbytes = bytes;
        for (int i = 0; i < 8 - tbytes.length; i++) {
            tbytes = ByteUtil.merge(zeroBytes, tbytes);
        }
        long l;
        l = tbytes[0];
        l &= 0xff;
        l |= ((long) tbytes[1] << 8);
        l &= 0xffff;
        l |= ((long) tbytes[2] << 16);
        l &= 0xffffff;
        l |= ((long) tbytes[3] << 24);
        l &= 0xffffffffL;
        l |= ((long) tbytes[4] << 32);
        l &= 0xffffffffffL;
        l |= ((long) tbytes[5] << 40);
        l &= 0xffffffffffffL;
        l |= ((long) tbytes[6] << 48);
        l &= 0xffffffffffffffL;
        l |= ((long) tbytes[7] << 56);
        return Double.longBitsToDouble(l);
    }

    /**
     * 字节转十六进制字符串
     * 
     * @param b
     * @return
     */
    public static String byte2HexString(final byte b) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(HexCode[(b >>> 4) & 0x0f]);
        buffer.append(HexCode[b & 0x0f]);
        return buffer.toString();
    }

    /**
     * 字节数组转十六进制字符串
     * 
     * @param bytes
     * @return
     */
    public static String bytes2HexString(final byte[] bytes) {

        StringBuffer buffer = new StringBuffer();
        if (bytes != null) {
            for (int i = 0; i < bytes.length; i++) {
                buffer.append(ByteUtil.byte2HexString(bytes[i]));
            }
        }
        return buffer.toString();
    }

    /**
     * 字节数组转转字符串
     * 
     * @param bytes
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String bytes2string(final byte[] bytes) throws UnsupportedEncodingException {
        return new String(bytes, Constants.ENCODING);
    }

    /**
     * 把十六进制字符串转字节数组
     * 
     * @param hexstr
     * @return byte[]
     */
    public static byte[] hexString2Bytes(final String hexstr) {
        byte[] bytes = null;
        String stringByHex = hexstr;
        if (stringByHex != null) {
            stringByHex = getDoubleStringByHex(stringByHex);
            bytes = new byte[stringByHex.length() / 2];
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) Integer.parseInt(stringByHex.substring(2 * i, 2 * i + 2), 16);
            }
        }
        return bytes;
    }

    /**
     * 给数组补元素
     * 
     * @param srcBytes
     *            原字节数组
     * @param destLength
     *            目标长度
     * @param beforePadding
     *            是否在前面补
     * @param paddingByte
     *            补充的字节
     * @return
     */
    public static byte[] paddingArray(final byte[] srcBytes, final int destLength, final boolean beforePadding,
            final byte paddingByte) {

        byte[] destBytes = new byte[destLength];

        if (srcBytes.length >= destLength) {
            System.arraycopy(srcBytes, 0, destBytes, 0, destLength);
        } else {
            int srcLength = srcBytes.length;
            byte[] paddingBytes = new byte[destLength - srcLength];
            for (int i = 0; i < paddingBytes.length; i++) {
                paddingBytes[i] = paddingByte;
            }
            if (beforePadding) {
                System.arraycopy(paddingBytes, 0, destBytes, 0, paddingBytes.length);
                System.arraycopy(srcBytes, 0, destBytes, paddingBytes.length, srcLength);
            } else {
                System.arraycopy(srcBytes, 0, destBytes, 0, srcLength);
                System.arraycopy(paddingBytes, 0, destBytes, srcLength, paddingBytes.length);
            }
        }
        return destBytes;
    }

    /**
     * 把Byte数组转成byte数组
     * 
     * @param bytes
     * @return
     */
    public static byte[] toBytes(Byte[] bytes) {
        byte[] tmpBytes = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            tmpBytes[i] = bytes[i];
        }
        return tmpBytes;
    }

    /**
     * 把byte数组转成Byte数组
     * 
     * @param bytes
     * @return
     */
    public static Byte[] toBytes(byte[] bytes) {
        Byte[] tmpBytes = new Byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            tmpBytes[i] = bytes[i];
        }
        return tmpBytes;
    }

    /**
     * 截断字节数组
     * 
     * @param srcBytes
     * @return
     */
    public static byte[] trim(final byte[] srcBytes) {
        return trimLeftPadding(trimRightPadding(srcBytes));
    }

    /**
     * 截断左边0x00字节
     * 
     * @param srcBytes
     * @return
     */
    public static byte[] trimLeftPadding(final byte[] srcBytes) {
        int paddingLength = 0;
        int srcLength = srcBytes.length;
        for (int i = 0; i < srcLength; i++) {
            if (srcBytes[i] == (byte) 0x00) {
                paddingLength++;
            } else {
                break;
            }
        }
        byte[] destBytes = new byte[srcLength - paddingLength];
        System.arraycopy(srcBytes, paddingLength, destBytes, 0, srcLength - paddingLength);

        return destBytes;
    }

    /**
     * 截断右边0x00字节
     * 
     * @param srcBytes
     * @return
     */
    public static byte[] trimRightPadding(final byte[] srcBytes) {
        int paddingLength = 0;
        int srcLength = srcBytes.length;
        for (int i = srcBytes.length - 1; i >= 0; i--) {
            if (srcBytes[i] == (byte) 0x00) {
                paddingLength++;
            } else {
                break;
            }
        }
        byte[] destBytes = new byte[srcLength - paddingLength];
        System.arraycopy(srcBytes, 0, destBytes, 0, srcLength - paddingLength);

        return destBytes;
    }

    /**
     * 合并字节数组
     * 
     * @param bytes
     * @param bytes1
     * @return
     */
    public static byte[] merge(final byte[] bytes, final byte[] bytes1) {
        byte[] array = new byte[bytes.length + bytes1.length];
        System.arraycopy(bytes, 0, array, 0, bytes.length);
        System.arraycopy(bytes1, 0, array, bytes.length, bytes1.length);
        return array;
    }

    /**
     * 往字节数组后面添加一个字节
     * 
     * @param bytes
     * @param b
     * @return
     */
    public static byte[] append(final byte[] bytes, final byte b) {
        int length = bytes.length;
        byte[] array = new byte[length + 1];
        System.arraycopy(bytes, 0, array, 0, length);
        array[length] = b;
        return array;
    }

    /**
     * 如果字符串长度不为偶数，则在前面加0
     * 
     * @param stringByHex
     * @return
     */
    private static String getDoubleStringByHex(final String stringByHex) {
        String strByHex = stringByHex;
        if (strByHex.length() % 2 == 1) {
            strByHex = 0 + strByHex;
        }
        return strByHex;
    }

}
