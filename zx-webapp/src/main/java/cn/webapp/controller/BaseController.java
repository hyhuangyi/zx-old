package cn.webapp.controller;

import cn.common.util.date.DateUtils;
import cn.common.util.file.JxlsUtils;
import cn.common.util.PropertyPlaceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

public class BaseController {
	public   final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private PropertyPlaceUtil propertyPlaceUtil;
	/**
	 * 导出方法 jxls
	 * @param response
	 * @param modelName
	 * @param model
	 */
	public void downFileFromModel(HttpServletResponse response, String modelName, Map<String, Object> model) {
		String fileName = DateUtils.getStringDate(new Date(), "yyyyMMddHHmmss") + ".xlsx";
		String pathName = propertyPlaceUtil.getFileExportPath()+ fileName;
		try (OutputStream out = new FileOutputStream(new File(pathName));
				InputStream in = this.getClass().getResourceAsStream(modelName)) {
			JxlsUtils.exportExcel(in, out, model);
			String contentType = "application/msexcel";
			downFile(response, pathName, fileName, contentType);
		} catch (IOException e) {
			logger.error(e.getLocalizedMessage(), e);
		}finally {
			//删除生成的文件！
			deleteFile(pathName);
		}
	}
	public void downFile(HttpServletResponse response, String pathName, String fileName, String contentType) {
		logger.info("--------------------------开始下载------------------------------");
		try (
			OutputStream out = response.getOutputStream();
			InputStream in = new FileInputStream(pathName)) {
			// 设置Content-Disposition
			response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
			// 设置文件MIME类型
			response.setContentType(contentType);
			// 写文件
			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
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
}
