package cn.common.util.math;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class BigDecimalUtil {
	//接近零的舍入模式。
	//　　在丢弃某部分之前始终不增加数字(从不对舍弃部分前面的数字加1,即截短)。
	private static int ROUND_DOWN = BigDecimal.ROUND_DOWN;

	private static int ROUND_HALF_UP = BigDecimal.ROUND_HALF_UP;//四舍五入

	/**
	 * 将数字字符串转化为BigDecimal
	 * 
	 * @param str 数字字符串
	 * 
	 * @return BigDecimal
	 */
	public static BigDecimal getBigDecimalForStr(String str){
		return new BigDecimal(str);
	}

	/**
	 * 将数字字符串转化为double,并保留指定小数位
	 * 
	 * @param str 数字字符串
	 * @param scale 指定要保留的小数位(可为空)
	 * 
	 * @return double
	 */
	public static double getBigDecimalForStrReturnDouble(String str,Integer scale){
		BigDecimal one = getBigDecimalForStr(str);
		if(null != scale){
			return one.setScale(scale, ROUND_HALF_UP).doubleValue();
		}
		return one.doubleValue();
	}

	/**
	 * 将double类型数字转化为BigDecimal
	 * 
	 * @param str 数字字符串
	 * 
	 * @return BigDecimal
	 */
	public static BigDecimal getBigDecimalForDouble(double one){
		return getBigDecimalForStr(one + "");
	}

	/**
	 * 获取指定小数位的double
	 * 
	 * @param one 数字
	 * 
	 * @return double
	 */
	public static double getScaleDouble(double one,Integer scale){
		return getBigDecimalForStrReturnDouble(one + "", scale);
	}

	/**
	 * 获取2位小数点的double
	 * 
	 * @param one 数字
	 * @return double
	 */
	public static double getScaleDouble(double one){
		return getScaleDouble(one, 2);
	}

	/**
	 * 获取货币格式化字符串(￥#.###.##)
	 * 
	 * @param one 数字
	 * 
	 * @return String
	 */
	public static String getCurrencyFormat(BigDecimal one){
		NumberFormat currency = NumberFormat.getCurrencyInstance(); //建立货币格式化引用 
		return currency.format(one);
	}

	/**
	 * 获取货币格式化字符串(￥#.###.##)
	 * 
	 * @param one 数字
	 * 
	 * @return String
	 */
	public static String getCurrencyFormat(double one){
		return getCurrencyFormat(getBigDecimalForStr(one + ""));
	}

	/**
	 * 两个BigDecimal数字相加
	 * 
	 * @param one 第一个数字
	 * @param two 第二个数字
	 * 
	 * @return BigDecimal
	 */
	public static BigDecimal add(BigDecimal one,BigDecimal two){
		return one.add(two).setScale(4, ROUND_DOWN);//相加
	}

	/**
	 * 两个数字字符串相加
	 * 
	 * @param oneNumber 第一个数字字符串
	 * @param twoNumber 第二个数字字符串
	 * 
	 * @return BigDecimal
	 */
	public static BigDecimal add(String oneNumber,String twoNumber){
		BigDecimal one = new BigDecimal(oneNumber);
		BigDecimal two = new BigDecimal(twoNumber);
		return add(one, two);
	}

	/**
	 * 两个double数字相加
	 * 
	 * @param oneNumber 第一个数字
	 * @param twoNumber 第二个数字
	 * 
	 * @return BigDecimal
	 */
	public static BigDecimal add(double oneNumber,double twoNumber){
		return add(oneNumber + "", twoNumber + "");
	}

	/**
	 * 两个double数字相加并保留指定小数位
	 * 
	 * @param one 第一个数字
	 * @param two 第二个数字
	 * @param scale 指定要保留的小数位(可为空)
	 * 
	 * @return BigDecimal
	 */
	public static double add(double one,double two,Integer scale){
		BigDecimal b = add(one, two);
		if(null != scale){
			return b.setScale(scale, ROUND_HALF_UP).doubleValue();
		}
		return b.doubleValue();
	}

	/**
	 * 两个数字字符串相加并保留指定小数位
	 * 
	 * @param oneNumber 第一个数字字符串
	 * @param twoNumber 第二个数字字符串
	 * @param scale 指定要保留的小数位(可为空)
	 * 
	 * @return double
	 */
	public static double addReturnDouble(String oneNumber,String twoNumber,Integer scale){
		BigDecimal b = add(oneNumber, twoNumber);
		if(null != scale){
			return b.setScale(scale, ROUND_HALF_UP).doubleValue();
		}
		return b.doubleValue();
	}

	/**
	 * 两个BigDecimal数字相减
	 * 
	 * @param one 第一个数
	 * @param two 第二个数
	 * @param scale 指定小数位
	 * 
	 * @return BigDecimal
	 */
	public static BigDecimal subtract(BigDecimal one,BigDecimal two){        
		return one.subtract(two);//相减
	}

	/**
	 * 两个BigDecimal数字相减并保留指定小数位
	 * 
	 * @param one 第一个数
	 * @param two 第二个数
	 * @param scale 指定小数位(可为空)
	 * 
	 * @return double
	 */
	public static double subtractReturnDouble(BigDecimal one,BigDecimal two,Integer scale){
		BigDecimal b = subtract(one, two);
		if(null != scale){
			return b.setScale(scale, ROUND_HALF_UP).doubleValue();
		}
		return b.doubleValue();
	}

	/**
	 * 两个数字字符串相减
	 * 
	 * @param oneNumber 第一个数字字符串
	 * @param twoNumber 第二个数字字符串
	 * @param scale 指定小数位
	 * 
	 * @return BigDecimal
	 */
	public static BigDecimal subtract(String oneNumber,String twoNumber){
		BigDecimal one = new BigDecimal(oneNumber);
		BigDecimal two = new BigDecimal(twoNumber);
		return subtract(one, two);
	}

	/**
	 * 两个数字相减并保留指定小数位
	 * 
	 * @param oneNumber 第一个数字
	 * @param twoNumber 第二个数字
	 * @param scale 指定小数位(可为空)
	 * 
	 * @return double
	 */
	public static double subtractReturnDouble(double oneNumber,double twoNumber,Integer scale){
		BigDecimal one = new BigDecimal(oneNumber + "");
		BigDecimal two = new BigDecimal(twoNumber + "");
		return subtractReturnDouble(one, two, scale);
	}   


	 public static String decimalBlankFormat(double value) {  
	        return new DecimalFormat("0").format(value);  
	  }  
	 
	 public static double stringSub(String v1, String v2) {// 减法  
	        BigDecimal b1 = new BigDecimal(v1);  
	        BigDecimal b2 = new BigDecimal(v2);  
	        return b1.subtract(b2).doubleValue();  
	    }

	 public static double div(double v1, double v2) {// 除法  
	        BigDecimal b1 = new BigDecimal(Double.toString(v1));  
	        BigDecimal b2 = new BigDecimal(Double.toString(v2));  
	        return b1.divide(b2, 3, BigDecimal.ROUND_HALF_UP).doubleValue();  
	 }  

}
