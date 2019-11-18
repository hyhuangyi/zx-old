package cn.common.util.math;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * BigDecimal计算工具类
 *
 * @author zhufeng
 * @since loan_v1.0
 */
public final class BigDecimalUtils
{
    public static final int DEF_DIV_SCALE = 2; // 默认精确的小数位

    private BigDecimalUtils()
    {
    }

    /**
     * 提供精确的加法运算。
     * 
     * @param params
     *            参数数组
     * @return 和
     */
    public static BigDecimal add(Number... params)
    {
        BigDecimal b = BigDecimal.ZERO;
        for (Number param : params)
        {
            BigDecimal b2 = new BigDecimal(param.toString());
            b = b.add(b2);
        }
        return b;
    }

    /**
     * 提供精确的减法运算。
     * 
     * @param b1
     *            被减数
     * @param b2
     *            减数
     * @return 两个参数的差
     */
    public static BigDecimal sub(Object b1, Object b2)
    {
        return new BigDecimal(b1.toString()).subtract(new BigDecimal(b2.toString()));
    }

    /**
     * 提供精确的乘法运算。
     * 
     * @param params
     *            参数数组
     * @return 动态参数的积
     */
    public static BigDecimal mul(Object... object)
    {
        BigDecimal b1 = BigDecimal.ONE;
        for (Object param : object)
        {
            BigDecimal b2 = new BigDecimal(param.toString());
            b1 = b1.multiply(b2);
        }
        BigDecimal one = BigDecimal.ONE;
        return b1.divide(one, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 提供精确的乘法运算。由scale参数指 定精度
     * 
     * @param params
     *            参数数组
     * @return 动态参数的积
     */
    public static BigDecimal mulFool(int scale, Object... params)
    {
        BigDecimal b1 = BigDecimal.ONE;
        for (Object param : params)
        {
            BigDecimal b2 = new BigDecimal(param.toString());
            b1 = b1.multiply(b2);
        }
        BigDecimal one = BigDecimal.ONE;
        return b1.divide(one, scale, BigDecimal.ROUND_HALF_UP);
    }
	
	/**
     * 提供精确的乘法加除法运算。
     * 
     * @param divisor
     *            被除数
     * @param params
     *            参数数组
     * @return 动态参数的积
     */
    public static BigDecimal mulAndDivScale(Number divisor, int scale, Number... params) {
        BigDecimal b1 = BigDecimal.ONE;
        for (Number param : params) {
            BigDecimal b2 = new BigDecimal(param.toString());
            b1 = b1.multiply(b2);
        }
        return b1.divide(new BigDecimal(divisor.toString()), scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 提供精确的乘法加除法运算。
     * 
     * @param divisor
     *            被除数
     * @param params
     *            参数数组
     * @return 动态参数的积
     */
    public static BigDecimal mulAndDiv(Number divisor, Number... params)
    {
        BigDecimal b1 = BigDecimal.ONE;
        for (Number param : params)
        {
            BigDecimal b2 = new BigDecimal(param.toString());
            b1 = b1.multiply(b2);
        }
        return b1.divide(new BigDecimal(divisor.toString()), DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal mulAndDivFloor(Number dividend, Number... params)
    {
        BigDecimal b1 = BigDecimal.ONE;
        for (Number param : params)
        {
            BigDecimal b2 = new BigDecimal(param.toString());
            b1 = b1.multiply(b2);
        }
        return b1.divide(new BigDecimal(dividend.toString()), DEF_DIV_SCALE, BigDecimal.ROUND_FLOOR);
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
     * 
     * @param v1
     *            被除数
     * @param v2
     *            除数
     * @return 两个参数的商
     */
    public static BigDecimal div(Number v1, Number v2)
    {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     * 
     * @param b1
     *            被除数
     * @param b2
     *            除数
     * @param scale
     *            表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static BigDecimal div(Object b1, Object b2, int scale)
    {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }

        if("0".equals(b1.toString()) || "0".equals(b2.toString())){
            return new BigDecimal("0");
        }

        return new BigDecimal(b1.toString()).divide(new BigDecimal(b2.toString()), scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 得到BigDecimal对象负数形式
     * 
     * @param param
     * @return BigDecimal
     * @since p2p_cloud_v1.0
     */
    public static BigDecimal getNegativeNumber(BigDecimal param)
    {
        return new BigDecimal("-" + param.doubleValue());
    }

    /**
     * 得到BigDecimal对象
     * 
     * @param obj
     * @return BigDecimal
     * @since p2p_cloud_v1.0
     */
    public static BigDecimal getBigDecimal(Object obj)
    {
        return new BigDecimal(obj.toString());
    }

    /**
     * 
     * BigDecimal==0
     * 
     * @param param
     * @return BigDecimal
     * @since p2p_cloud_v1.0
     */
    public static boolean eqZero(BigDecimal param)
    {
        if(param == null) return false;
        int r = param.compareTo(BigDecimal.ZERO); // 和0，Zero比较
        return r == 0;
    }

    /**
     * 
     * BigDecimal>0
     * 
     * @param param
     * @return BigDecimal
     * @since p2p_cloud_v1.0
     */
    public static boolean gtZero(BigDecimal param)
    {
        if(param == null) return false;
        int r = param.compareTo(BigDecimal.ZERO); // 和0，Zero比较
        return r == 1;
    }

    /**
     * 
     * BigDecimal<0
     * 
     * @param param
     * @return BigDecimal
     * @since p2p_cloud_v1.0
     */
    public static boolean ltZero(BigDecimal param)
    {
        if(param == null) return false;
        int r = param.compareTo(BigDecimal.ZERO); // 和0，Zero比较
        return r == -1;
    }

    /**
     * 得到BigDecimal对象相反数
     * 
     * @param param
     * @return BigDecimal
     * @since p2p_cloud_v1.0
     */
    public static BigDecimal getOppositeNumber(BigDecimal param)
    {
        return param.multiply(new BigDecimal("-1"));
    }

    public static String toDefaultScaleString(BigDecimal input)
    {
        if (input == null)
        {
            return "";
        }
        return input.setScale(DEF_DIV_SCALE, RoundingMode.HALF_UP).toPlainString();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     * 
     * @param v
     *            需要四舍五入的数字
     * @param scale
     *            小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static BigDecimal round(BigDecimal v, int scale)
    {
        if (scale < 0)
        {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal one = new BigDecimal("1");
        return v.divide(one, scale, BigDecimal.ROUND_HALF_UP);
    }

    // 小写数字转大写
    public static String change(double v)
    {
        final String UNIT = "万千佰拾亿千佰拾万千佰拾元角分";

        final String DIGIT = "零壹贰叁肆伍陆柒捌玖";

        final double MAX_VALUE = 9999999999999.99D;

        if (v < 0 || v > MAX_VALUE)
        {
            return "参数非法!";
        }
        long l = Math.round(v * 100);
        if (l == 0)
        {
            return "零元整";
        }
        String strValue = l + "";
        // i用来控制数
        int i = 0;
        // j用来控制单位
        int j = UNIT.length() - strValue.length();
        String rs = "";
        boolean isZero = false;
        for (; i < strValue.length(); i++, j++)
        {
            char ch = strValue.charAt(i);

            if (ch == '0')
            {
                isZero = true;
                if (UNIT.charAt(j) == '亿' || UNIT.charAt(j) == '万' || UNIT.charAt(j) == '元')
                {
                    rs = rs + UNIT.charAt(j);
                    isZero = false;
                }
            }
            else
            {
                if (isZero)
                {
                    rs = rs + "零";
                    isZero = false;
                }
                rs = rs + DIGIT.charAt(ch - '0') + UNIT.charAt(j);
            }
        }

        if (!rs.endsWith("分"))
        {
            rs = rs + "整";
        }
        rs = rs.replaceAll("亿万", "亿");
        return rs;
    }
}
