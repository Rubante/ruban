package com.ruban.framework.core.utils.format;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.ruban.framework.core.utils.commons.StringUtil;

/**
 * 格式化工具类
 * 
 * @author yjwang
 *
 */
public abstract class FormatUtil {

    private static final int DEFAULT_SCALE = 2;

    /**
     * 格式化百分比。
     * 
     * @param source
     * @return
     */
    public static String fmtPercent(BigDecimal source) {
        return fmtPercent(source, DEFAULT_SCALE);
    }

    /**
     * 格式化百分比。
     * 
     * @param source
     * @param scale
     * @return
     */
    public static String fmtPercent(BigDecimal source, int scale) {
        if (source == null)
            source = BigDecimal.ZERO;
        return internalFmtPercent(source, scale);
    }

    /**
     * 格式化百分比。
     * 
     * @param source
     * @return
     */
    public static String fmtPercent(String source) {
        return fmtPercent(source, DEFAULT_SCALE);
    }

    public static String fmtPercent(String source, int scale) {
        if (source == null || source.trim().length() == 0)
            source = "0.00";
        BigDecimal bigDecimal = new BigDecimal(source);
        return internalFmtPercent(bigDecimal, scale);
    }

    /**
     * 格式化百分比。
     * 
     * @param source
     * @param scale
     * @return
     */
    private static String internalFmtPercent(Object source, int scale) {
        if (scale < 0)
            scale = DEFAULT_SCALE;

        StringBuilder fp = new StringBuilder();
        fp.append("#0.");
        for (int i = 0; i < scale; i++) {
            fp.append("0");
        }
        fp.append("%");
        NumberFormat nf = new DecimalFormat(fp.toString());
        String result = nf.format(source);
        return result;
    }

    /**
     * 按千分位分割格式格式化数字
     * 
     * @param value
     * @param scale
     * @return
     */
    public static String fmtMicrometerByScale(Object value, int scale) {
        String temp = "###,###,###,###,###,###,###,##0";
        if (scale > 0)
            temp += ".";
        for (int i = 0; i < scale; i++)
            temp += "0";

        DecimalFormat format = new DecimalFormat(temp);

        if (value instanceof String) {
            return format.format(new BigDecimal((String) value)).toString();
        } else {
            return format.format(value).toString();
        }
    }

    /**
     * 格式化数字为千分位显示 <br />
     * 保留小数点后两位有效数字 <br />
     * "1088903.635656"-->"1,088,903.64"<br />
     * "1088903.63"-->"1,088,903.63"<br />
     * "1088903.6"-->"1,088,903.60"<br />
     * "1088903"-->"1,088,903.00"<br />
     * "1"-->"1.00"
     * 
     * @param text
     *            要格式化数字
     * @return 格式化后的字符串
     */
    public static String fmtMicrometer(String amount) {
        DecimalFormat df = null;
        df = new DecimalFormat("###,##0.00");
        double number = 0.0;
        try {
            number = Double.parseDouble(amount);
        } catch (Exception e) {
            number = 0.0;
        }
        return df.format(number);
    }

    /**
     * 格式化数字为千分位显示 <br />
     * 保留小数点后两位有效数字 <br />
     * "1088903.635656"-->"1,088,903.64"<br />
     * "1088903.63"-->"1,088,903.63"<br />
     * "1088903.6"-->"1,088,903.60"<br />
     * "1088903"-->"1,088,903.00"<br />
     * "1"-->"1.00"
     * 
     * @param amount
     *            要格式化数字(BigDecimal型)
     * @return 格式化后的字符串
     */
    public static String fmtMicrometer(BigDecimal amount) {
        if (amount == null) {
            amount = BigDecimal.ZERO;
        }
        DecimalFormat df = new DecimalFormat("###,##0.00");
        return df.format(amount);
    }

    /**
     * 将number格式为固定长度的字符串，不足补0
     * 
     * @param number
     * @param length
     * @return
     */
    public static String fmtNumber(Object number, int length) {
        if (number == null) {
            number = BigDecimal.ZERO;
        }

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append("0");
        }
        DecimalFormat df = new DecimalFormat(sb.toString());
        return df.format(number);
    }

    /**
     * 在正数前加上正号。
     * 
     * @param number
     * @return
     */
    public static String addPlusIf(String number) {
        if (StringUtil.isDigit(number)) {
            number = "+" + number;
        }

        return number;
    }

}
