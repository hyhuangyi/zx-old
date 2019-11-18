package cn.common.util.comm;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;

/**
 * 获取 项目/文件 路径
 * @author <a href="http://www.xdemo.org/">http://www.xdemo.org/</a>
 * 252878950@qq.com
 */
public class PathUtils {
	
	/**
	 * 获取项目的绝对路径
	 * @return Sting 获取项目的绝对路径 不同的容器返回的路径不同
	 */
	@Deprecated
	public static String getProjectDrivePath(){
		return System.getProperty("user.dir"); 
	}
	
	/**
	 * 获取所在的盘符
	 * @return String
	 */
	public static String getDrive() {
		  return new File("/").getAbsolutePath();
	}
	
	/**
	 * 获取指定类的路径
	 * @param clazz
	 * @return String
	 */
	public static String getClassDrivePath(Class<?> clazz) {
		  return clazz.getResource("").getPath().substring(1);
	}
	
	/**
	 * 获取项目
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static String getClassPath(){
		return Thread.currentThread().getContextClassLoader().getResource("").getPath();
	}

	public static void main(String[] args)throws Exception {
		System.out.println(getClassPath());
	}

}
