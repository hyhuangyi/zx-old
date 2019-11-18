package cn.common.util.file;

import org.apache.commons.lang.ArrayUtils;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangy on 2018-8-18.
 */
public class CheckoutFileType {
	// 记录各个文件头信息及对应的文件类型
	private static final Map<String, String> M_FILE_TYPES = new HashMap<>();

	// 所有合法的文件后缀
	private static String resFileType = ".jpg .jpeg .png .gif .bmp .tif .txt .pptx .xlsx .docx .ppt .xls .doc .wma .mov .wav .html .aac .pdf";

	private static Logger logger = LoggerFactory.getLogger(CheckoutFileType.class);

	static {
		// images
		M_FILE_TYPES.put("FFD8FFE0", ".jpg");
		M_FILE_TYPES.put("FFD8FFE1", ".jpg");
		M_FILE_TYPES.put("89504E47", ".png");
		M_FILE_TYPES.put("47494638", ".gif");
		M_FILE_TYPES.put("49492A00", ".tif");
		M_FILE_TYPES.put("424D", ".bmp");

		// PS和CAD
		M_FILE_TYPES.put("38425053", ".psd");
		M_FILE_TYPES.put("41433130", ".dwg"); // CAD
		M_FILE_TYPES.put("252150532D41646F6265", ".ps");

		// 办公文档类
		M_FILE_TYPES.put("D0CF11E0", ".doc"); // ppt、doc、xls
		M_FILE_TYPES.put("504B0304", ".docx");// pptx、docx、xlsx

		/** 注意由于文本文档录入内容过多，则读取文件头时较为多变-START **/
		M_FILE_TYPES.put("0D0A0D0A", ".txt");// txt
		M_FILE_TYPES.put("0D0A2D2D", ".txt");// txt
		M_FILE_TYPES.put("0D0AB4B4", ".txt");// txt
		M_FILE_TYPES.put("B4B4BDA8", ".txt");// 文件头部为汉字
		M_FILE_TYPES.put("73646673", ".txt");// txt,文件头部为英文字母
		M_FILE_TYPES.put("32323232", ".txt");// txt,文件头部内容为数字
		M_FILE_TYPES.put("0D0A09B4", ".txt");// txt,文件头部内容为数字
		M_FILE_TYPES.put("3132330D", ".txt");// txt,文件头部内容为数字
		/** 注意由于文本文档录入内容过多，则读取文件头时较为多变-END **/

		M_FILE_TYPES.put("7B5C727466", ".rtf"); // 日记本

		M_FILE_TYPES.put("255044462D312E", ".pdf");

		// 视频或音频类
		M_FILE_TYPES.put("FFF16C80", ".aac");
		M_FILE_TYPES.put("3026B275", ".wma");
		M_FILE_TYPES.put("52494646", ".wav");
		M_FILE_TYPES.put("41564920", ".avi");
		M_FILE_TYPES.put("4D546864", ".mid");
		M_FILE_TYPES.put("2E524D46", ".rm");
		M_FILE_TYPES.put("000001BA", ".mpg");
		M_FILE_TYPES.put("000001B3", ".mpg");
		M_FILE_TYPES.put("6D6F6F76", ".mov");
		M_FILE_TYPES.put("3026B2758E66CF11", ".asf");

		// 压缩包
		M_FILE_TYPES.put("52617221", ".rar");
		M_FILE_TYPES.put("1F8B08", ".gz");

		// 程序文件
		M_FILE_TYPES.put("3C3F786D6C", ".xml");
		M_FILE_TYPES.put("68746D6C3E", ".html");
		M_FILE_TYPES.put("7061636B", ".java");
		M_FILE_TYPES.put("3C254020", ".jsp");
		M_FILE_TYPES.put("4D5A9000", ".exe");

		M_FILE_TYPES.put("44656C69766572792D646174653A", ".eml"); // 邮件
		M_FILE_TYPES.put("5374616E64617264204A", ".mdb");// Access数据库文件

		M_FILE_TYPES.put("46726F6D", ".mht");
		M_FILE_TYPES.put("4D494D45", ".mhtml");

	}

	public static String getResFileType() {
		return resFileType;
	}

	public static void setResFileType(String resFileType) {
		CheckoutFileType.resFileType = resFileType;
	}

	/**
	 * 根据文件的输入流获取文件头信息
	 *
	 * @param is
	 *            文件路径
	 * @return 文件头信息
	 */
	public static String getFileType(InputStream is) {
		byte[] b = new byte[4];
		if (is != null) {
			try {
				is.read(b, 0, b.length);
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		String suffix = M_FILE_TYPES.get(getFileHeader(b));
		if(!ArrayUtils.contains(resFileType.split(" "),suffix)){
			throw new RuntimeException();
		}
		return suffix;
	}

	/**
	 * 根据文件转换成的字节数组获取文件头信息
	 *
	 * @param b
	 *            文件路径
	 * @return 文件头信息
	 */
	public static String getFileHeader(byte[] b) {
		return bytesToHexString(b);
	}

	/**
	 * 将要读取文件头信息的文件的byte数组转换成string类型表示 下面这段代码就是用来对文件类型作验证的方法，
	 * 将字节数组的前四位转换成16进制字符串，并且转换的时候，要先和0xFF做一次与运算。
	 * 这是因为，整个文件流的字节数组中，有很多是负数，进行了与运算后，可以将前面的符号位都去掉，
	 * 这样转换成的16进制字符串最多保留两位，如果是正数又小于10，那么转换后只有一位，
	 * 需要在前面补0，这样做的目的是方便比较，取完前四位这个循环就可以终止了
	 * 
	 * @param src
	 * @return 文件头信息
	 */
	private static String bytesToHexString(byte[] src) {
		StringBuilder builder = new StringBuilder();
		if (src == null || src.length <= 0) {
			return null;
		}
		String hv;
		for (int i = 0; i < src.length; i++) {
			// 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式，并转换为大写
			hv = Integer.toHexString(src[i] & 0xFF).toUpperCase();
			if (hv.length() < 2) {
				builder.append(0);
			}
			builder.append(hv);
		}

		logger.info("获取文件头信息:{}", builder.toString());

		return builder.toString();
	}

	/**
	 * 判断上传的文件是否合法 （一）、第一：检查文件的扩展名， (二）、 第二：检查文件的MIME类型 。
	 * 
	 * @param attachDoc
	 * @return boolean
	 */
	public static boolean checkFileValid(FileItem attachDoc) {
		boolean upFlag = false;// 为真表示符合上传条件，为假表标不符合
		if (attachDoc != null) {
			String attachName = attachDoc.getName();

			logger.info("#######上传的文件:{}",attachName);

			if (!"".equals(attachName) && attachName != null) {

				/** 返回在此字符串中最右边出现的指定子字符串的索引 **/
				String sname = attachName.substring(attachName.lastIndexOf('.'));

				/** 统一转换为小写 **/
				sname = sname.toLowerCase();

				/** 第一步：检查文件扩展名，是否符合要求范围 **/
				if (resFileType.indexOf(sname) != -1) {
					upFlag = true;
				}

				/**
				 * 第二步：获取上传附件的文件头，判断属于哪种类型,并获取其扩展名 直接读取文件的前几个字节，来判断上传文件是否符合格式
				 * 防止上传附件变更扩展名绕过校验
				 ***/
				if (upFlag) {
					String reqFileType = null;
					try {
						reqFileType = getFileType(attachDoc.getInputStream());
					} catch (IOException e) {
						logger.error(e.getMessage());
					}
					logger.info("///////用户上传的文件类型///////////{}" , reqFileType);
					/** 第三步：检查文件扩展名，是否符合要求范围 **/
					if (reqFileType != null && !"".equals(reqFileType) && !"null".equals(reqFileType)) {
						/** 第四步：校验上传的文件扩展名，是否在其规定范围内 **/
						if (resFileType.indexOf(reqFileType) != -1) {
							upFlag = true;
						} else {
							upFlag = false;
						}
					} else {
						/** 特殊情况校验,如果用户上传的扩展名为,文本文件,则允许上传-START **/
						if (sname.indexOf(".txt") != -1) {
							upFlag = true;
						} else {
							upFlag = false;
						}
						/** 特殊情况校验,如果用户上传的扩展名为,文本文件,则允许上传-END **/
					}
				}
			}
		}
		return upFlag;
	}

	/**
	 * 主函数，测试用
	 * 
	 * @param args
	 * @throws Exception
	 */

	public static void main(String[] args) {
		String value = null;
		String filePath = "F:\\360downloads\\wpcache\\360wallpaper.jpg";
		try (FileInputStream is = new FileInputStream(filePath)){
			byte[] b = new byte[4];
			is.read(b, 0, b.length);
			value = bytesToHexString(b);
			System.out.println(M_FILE_TYPES.get(value));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		logger.info(value);
	}
}