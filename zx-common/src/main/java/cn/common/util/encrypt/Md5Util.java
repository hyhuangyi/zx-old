package cn.common.util.encrypt;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 摘要加密（digest）MD5
 * Created by huangYi on 2018/3/10
 */
public class Md5Util {
	/**
	 * 利用MD5进行加密
	 * 
	 * @param str
	 *            待加密的字符串
	 * @return 加密后的字符串
	 * @throws NoSuchAlgorithmException
	 *             没有这种产生消息摘要的算法
	 * @throws UnsupportedEncodingException
	 */
	public static String EncoderByMd5(String str) {
		String newstr;
		try {
			// 确定计算方法
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			BASE64Encoder base64en = new BASE64Encoder();
			// 加密后的字符串
			newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("MD5加密出现错误");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("MD5加密出现错误");
		}
		return newstr;
	}
	/**
	 * 对字符串md5加密
	 *
	 * @param str
	 * @return
	 */
	public static String getMD5(String str) {
		String re;
		byte encrypt[];
		try {
			byte[] tem = str.getBytes("utf-8");
			MessageDigest md5 = MessageDigest.getInstance("md5");
			md5.reset();
			md5.update(tem);
			encrypt = md5.digest();
			StringBuilder sb = new StringBuilder();
			for (byte t : encrypt) {
				String s = Integer.toHexString(t & 0xFF);
				if (s.length() == 1) {
					s = "0" + s;
				}
				sb.append(s);
			}
			re = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("MD5加密出现错误");
		}catch (UnsupportedEncodingException e){
			throw new RuntimeException("System doesn't support your  EncodingException");
		}
		return re;
	}

	public static void main(String[] args){
		String str="123";
		System.out.println(getMD5(str));
		System.out.println(EncoderByMd5(str));

	}
}
