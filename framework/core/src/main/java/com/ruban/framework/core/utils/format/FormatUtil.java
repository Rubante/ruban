package com.ruban.framework.core.utils.format;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 百分比处理。gai
 * 
 * @author RZTAO
 */
public abstract class PercentUtil {
	
	private static final int DEFAULT_SCALE = 2;
	/**
	 * 格式化百分比。
	 * 
	 * @param source
	 * @return
	 */
	public static String fmtPercent(BigDecimal source){
		return fmtPercent(source,DEFAULT_SCALE);
	}
	
	/**
	 * 格式化百分比。
	 * 
	 * @param source
	 * @param scale
	 * @return
	 */
	public static String fmtPercent(BigDecimal source, int scale){
		if(source == null)
			source = BigDecimal.ZERO;
		return internalFmtPercent(source,scale);
	}
	
	/**
	 * 格式化百分比。
	 * 
	 * @param source
	 * @return
	 */
	public static String fmtPercent(String source){
		return fmtPercent(source,DEFAULT_SCALE);
	}
	
	public static String fmtPercent(String source,int scale){
		if(source == null || source.trim().length() == 0)
			source = "0.00";
		BigDecimal bigDecimal = new BigDecimal(source);
		return internalFmtPercent(bigDecimal,scale);
	}
	
	/**
	 * 格式化百分比。
	 * 
	 * @param source
	 * @param scale
	 * @return
	 */
	private static String internalFmtPercent(Object source,int scale){
		if(scale < 0)
			scale = DEFAULT_SCALE;
		
		StringBuilder fp = new StringBuilder();
		fp.append("#0.");
		for(int i=0;i<scale;i++){
			fp.append("0");
		}
		fp.append("%");
		NumberFormat nf = new DecimalFormat(fp.toString());
		String result = nf.format(source);
		return result;
	}
}
