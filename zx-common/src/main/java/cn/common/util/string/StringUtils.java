package cn.common.util.string;

import com.alibaba.fastjson.JSONObject;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author lijian
 */
public class StringUtils {

	public static String nullToEmpty(String src) {
		if ((src == null) || (src.equalsIgnoreCase("NULL")))
			return "";
		return src;
	}


	public static String lowerFirstChar(String s) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(Character.toLowerCase(s.charAt(0)))
		.append(s.substring(1));

		return buffer.toString();
	}

	public static String upperFirstChar(String s) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(Character.toUpperCase(s.charAt(0)))
		.append(s.substring(1));

		return buffer.toString();
	}

	/**
	 * 将str将多个分隔符进行切分，
	 * 
	 * 示例：StringTokenizerUtils.split("1,2;3 4"," ,;"); 返回: ["1","2","3","4"]
	 * 
	 * @param str
	 * @param seperators
	 * @return
	 */
	@SuppressWarnings("all")
	public static String[] split(String str, String seperators) {
		StringTokenizer tokenlizer = new StringTokenizer(str, seperators);
		List result = new ArrayList();

		while (tokenlizer.hasMoreElements()) {
			Object s = tokenlizer.nextElement();
			result.add(s);
		}
		return (String[]) result.toArray(new String[result.size()]);
	}



	/* 判断对象是否Empty(null或元素为0)<br>
	 * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
	 * 
	 * @param pObj
	 *            待检查对象
	 * @return boolean 返回的布尔值
	 */
	public static boolean isEmpty(Object pObj) {
		if (null == pObj || "".equals(pObj) ||  "null".equals( pObj))
			return true;
		if (pObj instanceof CharSequence) {
			if (((CharSequence) pObj).length() == 0) {
				return true;
			}
		} else if (pObj instanceof Collection) {
			if (((Collection<?>) pObj).isEmpty()) {
				return true;
			}
		} else if (pObj instanceof Map) {
			if (((Map<?,?>) pObj).isEmpty()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断对象是否为NotEmpty(!null或元素>0)<br>
	 * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
	 * 
	 * @param pObj
	 *            待检查对象
	 * @return boolean 返回的布尔值
	 */
	public static boolean isNotEmpty(Object pObj) {
		if (null == pObj || "".equals(pObj))
			return false;
		if (pObj instanceof String) {
			if (((String) pObj).trim().length() == 0) {
				return false;
			}
		} else if (pObj instanceof Collection) {
			if (((Collection<?>) pObj).isEmpty()) {
				return false;
			}
		} else if (pObj instanceof Map) {
			if (((Map<?,?>) pObj).isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public static boolean isEmpty(String pObj) {
		if (null == pObj || "".equals(pObj) ||  "null".equals(pObj))
			return true;
		return false;
	}

	public static boolean isEmptys(String ... strs) {
		for (String str : strs) {
			if (null == str || "".equals(str) ||  "null".equals(str))
				return true;
		}
		return false;
	}

	public static boolean isNotEmpty(String pObj) {
		if (null == pObj || "".equals(pObj) ||  "null".equals(pObj))
			return false;
		return true;
	}


	/**
	 * 
	 * @author Fan JiuYi
	 * @date：2016-5-29 下午01:26:16
	 * @Description: (判断数组内是否存在 该字符串)
	 * @version 1.0
	 * @param strArray 目标数组
	 * @param str 目标字符串
	 * @return boolean值
	 */
	public static boolean isExist(String[] strArray, String str){
		boolean isExist =false;
		if(!isNotEmpty(str)){
			return isExist;
		}
		for(String st : strArray){
			if(str.equals(st)){
				isExist = true;
				break;
			}
		}
		return isExist;
	}

	/**
	 * 检查指定的字符串列表是否不为空。
	 */
	public static boolean areNotEmpty(String... values) {
		boolean result = true;
		if (values == null || values.length == 0) {
			result = false;
		} else {
			for (String value : values) {
				result &= !isEmpty(value);
			}
		}
		return result;
	}


	/**
	 * Is exist empty boolean.
	 *
	 * @param values the values
	 * @return the boolean
	 */
	public static boolean isExistEmpty(String... values) {
		if (values == null || values.length == 0) {
			return true;
		} else {
			for (String value : values) {
				if(isEmpty(value)){
					return true;
				}
			}
		}
		return false;
	}

	// Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
	public static Map<String, String> transBean2Map(Object obj) {

		if(obj == null){
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);
					if(StringUtils.isNotEmpty(value)){
						map.put(key, String.valueOf(value));
					}
				}

			}
		} catch (Exception e) {
			System.out.println("transBean2Map Error " + e.getLocalizedMessage());
		}

		return map;
	}

	public static JSONObject transBean2JsonObj(Object obj) {
		if(obj == null){
			return null;
		}
		JSONObject map = new JSONObject();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);
					if(StringUtils.isNotEmpty(value)){
						map.put(key, String.valueOf(value));
					}
				}

			}
		} catch (Exception e) {
			System.out.println("transBean2JsonObj Error " + e.getLocalizedMessage());
		}

		return map;
	}

	/**
	 * 将map转换成url
	 * @param map
	 * @return
	 */
	public static String getUrlParamsByMap(Map<String, ?> map) {
		if (map == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, ?> entry : map.entrySet()) {
			String vStr = "";
			try {
				sb.append("&");
				if(StringUtils.isNotEmpty(entry.getValue())){
					vStr = String.valueOf(entry.getValue());
				}
				sb.append(entry.getKey() + "=" + urlEncoder(vStr));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String s = sb.deleteCharAt(0).toString();
		return s;
	}

	public static String urlEncoder(String s){
		try {
			return  URLEncoder.encode(s,"utf-8");
		} catch (UnsupportedEncodingException e) {
			//e.printStackTrace();
			return s;
		}
	}

	public static String urlDecoder(String s){
		try {
			return  URLDecoder.decode(s,"utf-8");
		} catch (UnsupportedEncodingException e) {
			//e.printStackTrace();
			return null;
		}
	}

	public static String trim(String str,String defaultVal) {
		return str != null ? str.trim() : defaultVal;
	}


	public static String trim(final String str) {
		return str == null ? null : str.trim();
	}

	/**
	 * 转换为Double类型
	 */
	public static Double toDouble(Object val){
		if (val == null){
			return 0D;
		}
		try {
			return Double.valueOf(trim(val.toString()));
		} catch (Exception e) {
			return 0D;
		}
	}

	/**
	 * 转换为Float类型
	 */
	public static Float toFloat(Object val){
		return toDouble(val).floatValue();
	}

	/**
	 * 转换为Long类型
	 */
	public static Long toLong(Object val){
		return toDouble(val).longValue();
	}

	/**
	 * 转换为Integer类型
	 */
	public static Integer toInteger(Object val){
		return toLong(val).intValue();
	}

	/**
	 * 转换为Integer类型
	 */
	public static int toInt(Object val){
		return toLong(val).intValue();
	}

	/**
	 * 获取去掉横线的长度为32的UUID串.
	 *
	 * @author WuShuicheng.
	 * @return uuid.
	 */
	public static String get32UUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 获取带横线的长度为36的UUID串.
	 *
	 * @author WuShuicheng.
	 * @return uuid.
	 */
	public static String get36UUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 功能：判断一个字符串是否包含特殊字符
	 * @param str 要判断的字符串
	 * @return true 提供的参数string不包含特殊字符
	 * @return false 提供的参数string包含特殊字符
	 */
	public static boolean isConSpeCharacters(String str) {
		if(str.replaceAll("[\u4e00-\u9fa5]*[a-z]*[A-Z]*\\d*-*_*\\s*", "").length()==0){
			//如果不包含特殊字符
			return true;
		}
		return false;
	}
	//去掉特殊字符
	public static String removeConSpeCharacters(String str) {
		if(isConSpeCharacters(str)==true) {
			return str=str.replaceAll("[^[\u4e00-\u9fa5]*[a-z]*[A-Z]*\\d*-*_*\\s*]", "");
		}
		return str;
	}

	//去掉Stringlist[]
	public static String removeListChar(String str) {
		if(str.startsWith("[")&&str.endsWith("]")) {
			return str.substring(1, str.length()-1);
		}
		return null;
	}


	/**
	 * Trim con spe characters string.
	 *
	 * @param str the str
	 * @return the string
	 */
	public static String trimConSpeCharacters(String str) {
		if(isNotEmpty(str)){// [`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]
			str = str.replaceAll("[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]", " ");
		}
		return str;
	}

	/**
	 *
	 * @param str
	 * @return
	 */
	public static boolean isContainChinese(String str) {
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			return true;
		}
		return false;
	}

	/**
	 * Is contains key boolean.
	 *
	 * @param str   the str
	 * @param infos the infos
	 * @return the boolean
	 */
	public static boolean isContainsKey(String str,String ... infos){
		if(StringUtils.isEmpty(str) || StringUtils.isEmpty(infos)){
			return false;
		}
		for (int i=0;i<infos.length;i++){
			if (str.indexOf(infos[i])>-1){
				return true;
			}
		}
		return false;
	}

	public static String urlReplaceEncoder(String str){
		/**
		 * +    URL 中+号表示空格                                 %2B
		 空格 URL中的空格可以用+号或者编码           %20
		 /   分隔目录和子目录                                     %2F
		 ?    分隔实际的URL和参数                             %3F
		 %    指定特殊字符                                          %25
		 #    表示书签                                                  %23
		 &    URL 中指定的参数间的分隔符                  %26
		 =    URL 中指定参数的值                                %3D
		 */
		if(StringUtils.isEmpty(str)){
			return str;
		}
		str = str.replace("+","%2B");
		//.replace(" ","%20")
		//.replace("/","%2F").replace("?","%3F")
		//.replace("%","%25").replace("#","%23")
		//.replace("&","%26").replace("=","%3D");
		return str;
	}

	public static String urlReplaceDecoder(String str){
		/**
		 * +    URL 中+号表示空格                                 %2B
		 空格 URL中的空格可以用+号或者编码           %20
		 /   分隔目录和子目录                                     %2F
		 ?    分隔实际的URL和参数                             %3F
		 %    指定特殊字符                                          %25
		 #    表示书签                                                  %23
		 &    URL 中指定的参数间的分隔符                  %26
		 =    URL 中指定参数的值                                %3D
		 */
		if(StringUtils.isEmpty(str)){
			return str;
		}
		str = str.replace("%2B","+");
		//.replace("%20"," ")
		//.replace("%2F","/").replace("%3F","?")
		//.replace("%25","%").replace("%23","#")
		//.replace("%26","&").replace("%3D","=");
		return str;
	}

	/**
	 *
	 * @param v
	 * @param len
	 * @return
	 */
	public static String getLeft(String v , int len){
		if(v ==null || v.length()==0){
			return "";
		}
		if(v.length()>len){
			v = v.substring(0,len);
		}
		return v;
	}


	/**
	 * @param inParamJO
	 * @param keys
	 * @return
	 */
	public static boolean areNotEmpty(JSONObject inParamJO, String... keys) {
		//--非空数据校验;
		for(int i=0; i<keys.length;i++){
			String key = keys[i];
			if(!inParamJO.containsKey(key)){
				return false;
			}
			if(StringUtils.isEmpty(inParamJO.getString(key))){
				return false;
			}
		}
		return true;
	}

	public static int lookIndex(String str,char c , int index){
		int number = 0;
		char arr[] = str.toCharArray();
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == c) {
				number++;
			}
			if (number == index) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * 是否有空白符 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}
	/**
	 * 是否没有空白符 
	 * @param str
	 * @return
	 */
	public static boolean isNotBlank(String str) {
		return !StringUtils.isBlank(str);
	}

	/**
	 *  截取字符串
	 * @param str
	 * @return
	 */
	public static String subStringStr(String str,String Intercept) {
		if(str!=null) {
			str=str.substring(str.indexOf(Intercept)+1);
			return str;
		}
		return null;
	}
	
	/**
	 *  截取字符串
	 * @param str
	 * @return
	 */
	public static <T> String getClassNameToLower(T classDO) {
	    String classString=classDO.getClass().getName();
	    int dotIndex = classString.lastIndexOf(".");
	    String lowerClassString=classString.substring(dotIndex+1).toLowerCase();
		return lowerClassString;
	}
	
	public static String arabNumToSimpChin(Integer arabNum) throws Exception{
		if (arabNum == null) {
			return "";
		}
		if (arabNum == 0) {
			return "零";
		}
		
		String[] unitArray= {"","十","百","千","万","十万","百万","千万","亿","十亿","百亿","千亿","万亿"}; 
		String[] numArray = {"零","一","二","三","四","五","六","七","八","九"};
		
		char[] charArray= String.valueOf(arabNum).toCharArray();
		int arrayLength = charArray.length;
		StringBuilder stringBuilder = new StringBuilder();
 
		for(int i = 0;i<arrayLength;i++	){
			int num = Integer.valueOf(charArray[i] + "");
			boolean isZero = num == 0;
			String unit = unitArray[(arrayLength - 1) - i];
			if (isZero) {
				continue;
			}else {
				if(i==0){
					stringBuilder.append(numArray[num]);
					stringBuilder.append(unit);
				}else{
					if(charArray[i-1]=='0'){
						stringBuilder.append("零");
						stringBuilder.append(numArray[num]);
						stringBuilder.append(unit);
					}else{
						stringBuilder.append(numArray[num]);
						stringBuilder.append(unit);
					}
				}
			}
		}
		String numString=stringBuilder.toString();
		if(numString.length()>1){
			if(numString.toCharArray()[0]=='一' && numString.toCharArray()[1]=='十'){
				return numString.substring(1);
			}
		}
		return numString;
	}

	

}
