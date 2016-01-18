/**
 * File name：HexCoder.java
 * Creation date ：2012-11-01
 * Copyright (c) 2012 tendyron
 * All rights reserved.
 */

package com.ruban.framework.core.utils.commons;

public class HexCoder {

    private static final char hexchars[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
            'f' };

    public String encode(byte abyte0[]) {
        return encode(abyte0, null);
    }

    public String encode(byte abyte0[], String separator) {
        if (abyte0 == null)
            return null;
        int len = abyte0.length * 2;
        if (len == 0)
            return "";
        StringBuffer stringbuffer = new StringBuffer(len);
        for (int i = 0; i < abyte0.length; i++) {
            stringbuffer.append(hexchars[abyte0[i] >>> 4 & 0xf]);
            stringbuffer.append(hexchars[abyte0[i] & 0xf]);
            if (separator != null && i < abyte0.length - 1)
                stringbuffer.append(separator);
        }

        return stringbuffer.toString();
    }

    public byte[] decode(String string) {
        return decode(string, "\0");
    }

    public byte[] decode(String string, String separator) {
        if (string == null)
            return null;
        int idx;
        while ((idx = string.lastIndexOf(separator)) != -1) {
            string = string.substring(0, idx) + string.substring(idx + separator.length());
        }
        string.replaceAll(separator, "");
        char ac[] = string.toCharArray();
        int i = removeWhiteSpace(ac);
        if (i % 2 != 0)
            return null;
        int j = i / 2;
        if (j == 0)
            return new byte[0];
        byte[] data = new byte[j];
        int i1 = 0;
        for (int k = 0; k < j; k++) {
            int c;
            int c1;
            if ((c = toData(ac[i1++])) == -1 || (c1 = toData(ac[i1++])) == -1)
                return null;
            data[k] = (byte) ((c << 4) + c1);
        }
        return data;
    }

    protected static int toData(char c) {
        if (c >= '0' && c <= '9')
            return c - '0';
        else if (c >= 'a' && c <= 'f')
            return c - 'a' + 10;
        else if (c >= 'A' && c <= 'F')
            return c - 'A' + 10;
        return -1;
    }

    protected static boolean isWhiteSpace(char c) {
        return c == ' ' || c == '\r' || c == '\n' || c == '\t';
    }

    protected static int removeWhiteSpace(char ac[]) {
        if (ac == null)
            return 0;
        int i = 0;
        int j = ac.length;
        for (int k = 0; k < j; k++)
            if (!isWhiteSpace(ac[k]))
                ac[i++] = ac[k];
        return i;
    }
}
