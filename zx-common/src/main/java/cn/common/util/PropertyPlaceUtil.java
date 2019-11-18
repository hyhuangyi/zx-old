package cn.common.util;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by huangy
 * 配置文件类
 */
@Component
public class PropertyPlaceUtil {

    @Value("${file.export.path}")
    private String fileExportPath;//文件下载目录

    public String getFileExportPath() {
        return fileExportPath;
    }

    public void setFileExportPath(String fileExportPath) {
        this.fileExportPath = fileExportPath;
    }
}
