package cn.common.util.file;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import java.io.*;

/**
 * org.apache.tools.zip 压缩工具类
 * Created by huangYi on 2019/7/21.
 **/
public class AntZipUtil {

    /**
     * 压缩文件列表到某ZIP文件
     *
     * @param zipFilename 要压缩到的ZIP文件
     * @param paths       文件列表，多参数
     * @throws Exception
     */
    public static void compress(String zipFilename, String... paths)
            throws Exception {
        compress(new FileOutputStream(zipFilename), paths);
    }

    /**
     * 压缩文件列表到输出流
     *
     * @param os    要压缩到的流
     * @param paths 文件列表，多参数
     * @throws Exception
     */
    public static void compress(OutputStream os, String... paths)
            throws Exception {
        ZipOutputStream zos = new ZipOutputStream(os);
        for (String path : paths) {
            if (path.equals(""))
                continue;
            java.io.File file = new java.io.File(path);
            if (file.exists()) {
                if (file.isDirectory()) {
                    zipDirectory(zos, file.getPath(), file.getName()
                            + File.separator);
                } else {
                    zipFile(zos, file.getPath(), "");
                }
            }
        }
        zos.close();
    }

    private static void zipDirectory(ZipOutputStream zos, String dirName,
                                     String basePath) throws Exception {
        File dir = new File(dirName);
        if (dir.exists()) {
            File files[] = dir.listFiles();
            if (files.length > 0) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        zipDirectory(zos, file.getPath(), basePath
                                + file.getName().substring(
                                file.getName().lastIndexOf(
                                        File.separator) + 1)
                                + File.separator);
                    } else
                        zipFile(zos, file.getPath(), basePath);
                }
            } else {
                ZipEntry ze = new ZipEntry(basePath);
                zos.putNextEntry(ze);
            }
        }
    }

    private static void zipFile(ZipOutputStream zos, String filename,
                                String basePath) throws Exception {
        File file = new File(filename);
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(filename);
            ZipEntry ze = new ZipEntry(basePath + file.getName());
            zos.putNextEntry(ze);
            byte[] buffer = new byte[8192];
            int count = 0;
            while ((count = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, count);
            }
            fis.close();
        }
    }

    /**
     * 解压文件
     *
     * @param unZipFileName
     * @param unZipPath
     * @throws Exception
     */
    public static void unZipFile(String unZipFileName, String unZipPath)
            throws Exception {
        org.apache.tools.zip.ZipFile zipFile = new org.apache.tools.zip.ZipFile(
                unZipFileName);
        unZip(zipFile, unZipPath);
    }

    public static void unZip(org.apache.tools.zip.ZipFile zipFile,
                             String unZipRoot) throws Exception, IOException {
        java.util.Enumeration e = zipFile.getEntries();
        org.apache.tools.zip.ZipEntry zipEntry;
        while (e.hasMoreElements()) {
            zipEntry = (org.apache.tools.zip.ZipEntry) e.nextElement();
            InputStream fis = zipFile.getInputStream(zipEntry);
            if (zipEntry.isDirectory()) {
            } else {
                File file = new File(unZipRoot + File.separator
                        + zipEntry.getName());
                File parentFile = file.getParentFile();
                parentFile.mkdirs();

                FileOutputStream fos = new FileOutputStream(file);
                try {
                    byte[] b = new byte[1024];
                    int len;
                    while ((len = fis.read(b, 0, b.length)) != -1) {
                        fos.write(b, 0, len);
                    }
                } finally {
                    fos.close();
                    fis.close();
                }

            }
        }
    }

    public static void main(String[] args) throws Exception {
//        compress("F://zx.zip","F:\\download");
        unZipFile("F://zx.zip", "f:\\test");
    }

}
