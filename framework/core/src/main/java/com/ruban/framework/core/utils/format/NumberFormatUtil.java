package com.ruban.framework.core.utils.format;

/**
 * 格式化数字。
 * 
 * @author RZTAO
 */
public abstract class NumberFormatUtil {
	
	/**
	 * 在正数前加上正号。
	 * 
	 * @param number
	 * @return
	 */
	public static String withSignIfPositive(String number){
		if(number!= null && number.trim().length()>0 && !number.startsWith("-") && !number.startsWith("+"))
			number = "+"+number;
		
		return number;
	}

}
