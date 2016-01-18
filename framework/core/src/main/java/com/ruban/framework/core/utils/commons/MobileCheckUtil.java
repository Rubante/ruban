package com.ruban.framework.core.utils.commons;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 手机号校验工具。
 * 
 * @author RZTAO
 */
public abstract class MobileCheckUtil {
	
	/**手机号正则表达式*/
	private static Pattern MOBILE_NO_PATTERN = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[0,0-9])|(18[0,0-9]))\\d{8}$");
	
	/**
	 * 验证手机号码
	 * 
	 * @param mobileNo 
	 * @return
	 */
	public static boolean validateMobileNo(String mobileNo){
		if(mobileNo==null || mobileNo.trim().length()==0 || mobileNo.trim().length()!=11)
			return false;
		try{
			Matcher m = MOBILE_NO_PATTERN.matcher(mobileNo);
			return m.matches(); 
		}catch(Exception e){  
		}  
		return false;  
	}
}
