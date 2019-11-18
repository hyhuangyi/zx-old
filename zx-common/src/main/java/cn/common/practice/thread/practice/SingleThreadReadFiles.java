package cn.common.practice.thread.practice;

import java.io.File;

/**
 * 单线程递归读取文件
 */
public class SingleThreadReadFiles {

    public static void readFile(File file) {
        File[] files = null;
        if (file != null) {
            files = file.listFiles();
        }
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    readFile(f);
                } else {
                    if (f.getAbsolutePath().endsWith("txt"))
                        System.out.println(f.getAbsolutePath());
                }
            }
        }
    }

    public static void main(String[] args) {
        readFile(new File("f:/Git/"));
    }
}
