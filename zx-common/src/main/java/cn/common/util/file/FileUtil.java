
package cn.common.util.file;

import cn.common.util.sys.SystemUtil;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.security.MessageDigest;
import java.util.regex.Pattern;

/**
 *
 * 操作文件工具类
 * Created by huangYi on 2018/3/10
 *
 */
public class FileUtil {
    private static Logger logger = Logger.getLogger(FileUtil.class);
    /**
     * 移动文件到指定目录
     * @param srcFile 被移动文件
     * @param targetDir 目标目录
     */
    public static void moveFile2Dir(File srcFile, String targetDir) {
        File targetFile = new File(targetDir + "/" + srcFile.getName());
        try {
            FileUtils.copyFile(srcFile, targetFile);
            FileUtils.forceDelete(srcFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 复制到指定目录
     * @param srcFile 被复制文件
     * @param targetDir 目标目录
     */
    public static void copyFile2Dir(File srcFile, String targetDir) {
        File targetFile = new File(targetDir + "/" + srcFile.getName());
        try {
            FileUtils.copyFile(srcFile, targetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void copyFile2Dir(File srcFile, String targetDir, String newName) {
        logger.info("bugcopy123123"+srcFile+","+targetDir+","+newName);
        File targetFile = new File(targetDir + "/" + newName);
        try {
        	if(targetFile.exists()){
        		targetFile.delete();
        	}
            FileUtils.copyFile(srcFile, targetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 创建路径
     * @param dir 目标路径
     */
    public static void createDir(String dir) {
        File f = new File(dir);
        if (!f.exists()) {
            f.mkdirs();
        }
    }
    
    /**
     * 得到文件名
     * @param fullFileName
     * @return
     */
    public static String getFileName(String fullFileName)
    {
        String path = fullFileName;
        int bit = 0;
        int n1 = path.lastIndexOf("/");
        if (n1 < 0) { n1 = path.lastIndexOf("\\"); bit = 1; }
        return path.substring(n1 + bit, path.length());
    }
    
    /**
     * 拷贝文件
     * @param sourceFile
     * @param newFile
     */
    public static void copyFile(String sourceFile, String newFile)
    {
    	File nf=new File(newFile);
    	File sf=new File(sourceFile);
    	copyFile(sf,nf);
    }
    
    /**
     * 拷贝文件
     * @param sf
     * @param nf
     */
    public static void copyFile(File sf, File nf)
    {
    	if(nf.exists())nf.delete();
		try {
			FileInputStream input = new FileInputStream(sf);
			BufferedInputStream inBuff=new BufferedInputStream(input);
			 
	        FileOutputStream output = new FileOutputStream(nf);
	        BufferedOutputStream outBuff=new BufferedOutputStream(output);
	        
	        byte[] b = new byte[1024 * 5]; 
	        int len; 
	        while ((len =inBuff.read(b)) != -1) { 
	            outBuff.write(b, 0, len); 
	        }
	        outBuff.flush(); 
	        inBuff.close(); 
	        outBuff.close(); 
	        output.close(); 
	        input.close(); 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
    }
    
    /**
	 * 得到Xml文件内容
	 * @param fullFileName
	 * @param encoding
	 * @return
	 */
	public static String getXmlFileString(String fullFileName, String encoding)
    {
    	String line;
		StringBuilder sb=new StringBuilder();
    	try {
	    	File file=new File(fullFileName);
	    	FileInputStream stream=new FileInputStream(file);
	    	InputStreamReader inputReader = new InputStreamReader(stream,encoding);
	    	BufferedReader reader=new BufferedReader(inputReader);
			while ((line = reader.readLine()) != null) {
				sb.append(line+"");
			}
			stream.close();
			inputReader.close();
			reader.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		String fieString=sb.toString();
		return (fieString.length()>0 && ((int)fieString.charAt(0)==65279))?fieString.substring(1,fieString.length()):fieString;
    }
	
	/**
     * 写入文件内容
     * @param fullFileName 文件全名称
     * @param content 文件内容
     */
    public static void writeFile(String fullFileName, String content)
    {
        writeFile(fullFileName, content,"utf-8",true);
    }
    
    /**
     * 写入文件内容
     * @param fullFileName 文件全名称
     * @param content 文件内容
     * @param encoding	文件编码
     */
    public static void writeFile(String fullFileName, String content, String encoding)
    {
        writeFile(fullFileName, content,encoding,true);
    }
    
    /**
     * 写入文件内容
     * @param fullFileName 文件全名称
     * @param content 文件内容
     * @param coverFlag 文件复盖标志
     */
    public static void writeFile(String fullFileName, String content, boolean coverFlag)
    {
    	writeFile(fullFileName, content,"utf-8",coverFlag);
    }

    /**
     * 写入文件内容
     * @param fullFileName 文件全名称
     * @param content 文件内容
     * @param encoding 文件编码
     * @param coverFlag 文件复盖标志
     */
    public static void writeFile(String fullFileName, String content, String encoding, boolean coverFlag)
    {
    	String path = getFileFullPath(fullFileName);
        createDirectory(path);
        if (!isExistFile(fullFileName))
        {
        	File f=new File(fullFileName);
        	try {
				f.createNewFile();
				FileOutputStream fos = new FileOutputStream(fullFileName);
				OutputStreamWriter writer = new OutputStreamWriter(fos, encoding);
				writer.write(content);
				writer.close();
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        else
        {
        	
            
            try {
            	File f=new File(fullFileName);
				FileOutputStream fos = new FileOutputStream(f,!coverFlag);
				OutputStreamWriter writer = new OutputStreamWriter(fos, encoding);
				writer.write(content);
				writer.close();
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
    
    /**
     * 得到文件的绝对路径
     * @param path
     * @return
     */
    public static String getFileFullPath(String path)
    {
    	File file=new File(path);
    	return (file.isAbsolute())?path:file.getAbsolutePath();
    }
    
    /**
     * 判断文件是否存在
     * @param file
     * @return
     */
    public static boolean isExistFile(String file)
    {
    	String fullPath = getFileFullPath(file);
    	File f=new File(fullPath);
        return f.isFile() && f.exists();
    }
    
    /**
     * 创建文件路径
     * @param path
     */
    public static void createDirectory(String path)
    {
    	String fullPath = getFileFullPath(path);
    	if(!isExistFilePath(fullPath))
    	{
    		File f=new File(getFilePath(fullPath));
    		f.mkdirs();
    	}
    }
    
    /**
     * 判断文件路径是否存在
     * @param path
     * @return
     */
    public static boolean isExistFilePath(String path)
    {
        String filePath = getFileFullPath(path);
        File f=new File(filePath);
        return f.isDirectory() && f.exists();
    }
    
	/**
	 * 得到文件路径
	 * @param fullFileName
	 * @return
	 */
    public static String getFilePath(String fullFileName)
    {
        String path = fullFileName;
        int n1 = path.lastIndexOf('/');
        int n2 = path.lastIndexOf('\\');
        return (n1 > 0) ? path.substring(0, n1) : path.substring(0, n2);
    }
    
    /**
     * 得到文件md5
     * @param f 目标文件
     * @return 目标文件md5
     */
    public static String getFileMD5(File f) {
        String result = "";
        FileInputStream fis = null;
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            fis = new FileInputStream(f);
            //int bSize = (f.length()>1024*1024*4)?1024*1024*4:Integer.parseInt(String.valueOf(f.length()));
            byte[] buffer = new byte[8192];
            int length;
            while ((length = fis.read(buffer)) != -1) {
                md.update(buffer, 0, length);
            }
            result = new String(Hex.encodeHex(md.digest()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    
    /**
     * 判断文件路径是否有效
     * @param path
     * @return
     */
    public static boolean isValidSystemFilePath(String path){
        if(SystemUtil.isWindows()){//是否windows系统
            File file = new File(path);
            return file.isDirectory();
        }else{//是否linux系统
            return path.matches("([\\\\/][\\w-]+)*$");
        }
    }

    /**
     * 拼接路径
     * @param path
     * @param filename
     * @return
     */
    public static String contact(String path, String filename) {
        char c = path.charAt(path.length() - 1);
        if (c == '/' || c == '\\')
            return format("{0}{1}", new Object[] { path, filename });
        else
            return format("{0}/{1}", new Object[] { path, filename });
    }

    public static String format(String str, Object... args) {
        return format(str, Pattern.compile("\\{(\\d+)\\}"), args);
    }
    /**
     * 字符串参数格式化
     * @param str
     * @param args
     * @return
     */
    public static String format(final String str, Pattern pattern, Object... args) {
        //这里用于验证数据有效性
        if (str == null || "".equals(str)){
            return "";
        }
        if (args.length == 0) {
            return str;
        }

        String result = str;

        //这里的作用是只匹配{}里面是数字的子字符串
        Pattern p = pattern;
        java.util.regex.Matcher m = p.matcher(str);

        while (m.find()) {
            //获取{}里面的数字作为匹配组的下标取值
            int index = Integer.parseInt(m.group(1));

            //这里得考虑数组越界问题，{1000}也能取到值么？？
            if (index < args.length) {
                //替换，以{}数字为下标，在参数数组中取值
                result = result.replace(m.group(), args[index].toString());
            } else {
                result = result.replace(m.group(), "");
            }
        }
        return result;
    }


    /**
     *  根据路径删除指定的目录或文件，无论存在与否
     *@param sPath  要删除的目录或文件
     *@return 删除成功返回 true，否则返回 false。
     */
    public static boolean deleteFolder(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 判断目录或文件是否存在
        if (!file.exists()) {  // 不存在返回 false
            return flag;
        } else {
            // 判断是否为文件
            if (file.isFile()) {  // 为文件时调用删除文件方法
                return deleteFile(sPath);
            } else {  // 为目录时调用删除目录方法
                return deleteDirectory(sPath);
            }
        }
    }

    /**
     * 删除单个文件
     * @param   sPath    被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 删除目录（文件夹）以及目录下的文件
     * @param   sPath 被删除目录的文件路径
     * @return  目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String sPath) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } //删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }
    /*
    public static void main(String[]args){
        File file=new File("f:/huangy/子轩.chm");
        System.out.println(getFileMD5(file));
        System.out.println(contact("f:/huangy","子轩.chm"));
    }*/
}
