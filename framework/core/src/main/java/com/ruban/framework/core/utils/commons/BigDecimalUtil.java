package com.ruban.framework.core.utils.commons;

import java.math.BigDecimal;

/**
 * BigDecimal工具类型。
 * 
 * @author RZTAO
 */
public class BigDecimalUtil {
	
	/**金额的默认scale*/
	public static final int DEFAULT_SCALE_FOR_MONEY = 2;
	
	/**金额：0*/
	public static final BigDecimal MONEY_ZERO = new BigDecimal(0.00);
	/**一百*/
	public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
	
	static {
		MONEY_ZERO.setScale(DEFAULT_SCALE_FOR_MONEY);
	}
	
	private BigDecimalUtil(){}

	/**
	 * 配置Money。
	 * 
	 * @param money
	 * @return
	 */
	public static BigDecimal configMoney(BigDecimal money){
		if(money == null){
			money = MONEY_ZERO;
		}
		money.setScale(DEFAULT_SCALE_FOR_MONEY);
		return money;
	}
	
	/**
	 * 转换成字符串。
	 * 
	 * @param money
	 * @return
	 */
	public static String getMoney(BigDecimal money){
		if(money == null){
			money = MONEY_ZERO;
		}
		money.setScale(DEFAULT_SCALE_FOR_MONEY);
		return money.toString();
	}
	
	/**
	 * 将小数转换成百分比表示，比如0。01转换成1%，一般用于显示。
	 * 
	 * @param decimal
	 * @return
	 */
	public static BigDecimal getPercent(BigDecimal decimal){
		return decimal.multiply(ONE_HUNDRED);
	}
	
	/**
	 * 将百分比转成小数表示形式，比如1%转换成0.01,一般用于计算。
	 * 
	 * @param percent
	 * @return
	 */
	public static BigDecimal getDecimal(BigDecimal percent){
		return percent.divide(ONE_HUNDRED);
	}
}
