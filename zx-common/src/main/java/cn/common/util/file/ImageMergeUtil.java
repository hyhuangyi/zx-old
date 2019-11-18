package cn.common.util.file;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.*;

/**
 * 图像合并工具类
 */
public class ImageMergeUtil {

    private static Logger LOGGER = Logger.getLogger(ImageMergeUtil.class);

    /**
     * 图像合并bytes [图片类型]
     *
     * @param prosPath 第一张图片路径
     * @param consPath 第二张图片路径
     * @return
     */
    public static byte[] imageMergeToBytes(String prosPath, String consPath) {
        LOGGER.info("prosFile:" + prosPath + ";consFile:" + consPath);
        byte[] bytes = null;
        try {
            // 读取待合并的文件
            BufferedImage prosImg = ImageIO.read(new File(prosPath));
            BufferedImage consImg = ImageIO.read(new File(consPath));

            // 图像压缩
            prosImg = resize(prosImg, 1000, 1000, true);
            consImg = resize(consImg, 1000, 1000, true);

            // 合并后图像
            BufferedImage mergeImg = mergeImage(prosImg, consImg, false);

            // 压缩 后大概100K-300K
            mergeImg = resize(mergeImg, 1000, 1000, false);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(mergeImg, "jpg", baos);
            bytes = baos.toByteArray();

            baos.close();
        } catch (IOException e) {
            LOGGER.error("[图像合并bytes]异常", e);
        }
        return bytes;
    }

    /**
     * 图像合并bytes [.dat类型]
     *
     * @param prosPath 第一张图片路径
     * @param consPath 第二张图片路径
     * @return
     */
    public static byte[] imageMergeToBytes2(String prosPath, String consPath)
            throws Exception {
        LOGGER.info("prosPath:" + prosPath + ";consPath:" + consPath);
        // 读取待合并的文件
        BufferedImage bi1 = null;
        BufferedImage bi2 = null;
        // 调用mergeImage方法获得合并后的图像
        BufferedImage destImg = null;
        String imgStr = "";
        byte[] bytes = null;
        try {
            // 读取图片的base64String
            String prosString = readFile2String(prosPath, "");
            String consString = readFile2String(consPath, "");

            bi1 = getBufferedImage(prosString);
            bi1 = resize(bi1, 1000, 1000, true);
            bi2 = getBufferedImage(consString);
            bi2 = resize(bi2, 1000, 1000, true);
            // 合成
            destImg = mergeImage(bi1, bi2, false);
            // 压缩 后大概100K-300K
            destImg = resize(destImg, 1000, 1000, false);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(destImg, "jpg", baos);
            bytes = baos.toByteArray();
        } catch (IOException e) {
            LOGGER.error("[图像合并bytes2]异常", e);
        }
        return bytes;
    }

    /**
     * @param base64string 图片base64编码
     * @return 读取到的缓存图像
     * @throws IOException 路径错误或者不存在该文件时抛出IO异常
     */
    private static BufferedImage getBufferedImage(String base64string)
            throws IOException {
        InputStream stream = BaseToInputStream(base64string);
        return ImageIO.read(stream);
    }

    private static InputStream BaseToInputStream(String base64string)
            throws IOException {
        ByteArrayInputStream stream = null;
        byte[] bytes1 = Base64.decodeBase64(base64string.getBytes());
        stream = new ByteArrayInputStream(bytes1);
        return stream;
    }

    /**
     * @param fileName
     * @param encoding 编码类型
     * @return 转换后的字符串
     */
    public static String readFile2String(String fileName, String encoding) {
        InputStreamReader reader = null;
        StringWriter writer = new StringWriter();
        try {
            File file = new File(fileName);
            if (encoding != null && !"".equals(encoding.trim())) {
                reader = new InputStreamReader(new FileInputStream(file),
                        encoding);
            } else {
                reader = new InputStreamReader(new FileInputStream(file));
            }
            char[] buffer = new char[8 * 1024];
            int n = 0;
            while (-1 != (n = reader.read(buffer))) {
                writer.write(buffer, 0, n);
            }
            reader.close();
            writer.close();
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {

                }
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {

                }
            }
        }
        if (writer != null) {
            return writer.toString();
        } else {
            return null;
        }
    }

    /**
     * 免冠照
     *
     * @return
     */
    public static byte[] mgImage(String path) {
        LOGGER.info("path:" + path);
        byte[] bytes = null;
        try {
            // 读取待合并的文件
            BufferedImage mgImg = ImageIO.read(new File(path));

            // 图像压缩
            mgImg = resize(mgImg, 1000, 1000, true);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(mgImg, "jpg", baos);
            bytes = baos.toByteArray();
            baos.close();
        } catch (IOException e) {
            LOGGER.error("[获取免冠照bytes]异常", e);

        }
        return bytes;
    }

    /**
     * 获取免冠照
     *
     * @param mgImage
     * @return
     */
    public static String mgImageToString(String mgImage) {
        byte[] bytes = mgImage(mgImage);
        return Base64.encodeBase64String(bytes);
    }

    /**
     * 图像合并String [图片类型]
     *
     * @param prosPath 第一张图片路径
     * @param consPath 第二张图片路径
     * @return
     */
    public static String imageMergeToString(String prosPath, String consPath) {
        byte[] bytes = imageMergeToBytes(prosPath, consPath);
        return Base64.encodeBase64String(bytes);
    }

    /**
     * 图像合并String [.dat类型]
     *
     * @param prosPath 第一张图片路径
     * @param consPath 第二张图片路径
     * @return
     */
    public static String imageMergeToString2(String prosPath, String consPath) {
        byte[] bytes = null;
        try {
            bytes = imageMergeToBytes2(prosPath, consPath);
        } catch (Exception e) {
            LOGGER.error("[imageMergeToString2]异常", e);

        }
        return Base64.encodeBase64String(bytes);
    }

    /**
     * 压缩图片
     */
    private static BufferedImage resize(BufferedImage source, int targetW, int targetH, boolean isRotate) {
        // targetW，targetH分别表示目标长和宽
        int type = source.getType();
        BufferedImage target = null;
        int width = source.getWidth();
        int height = source.getHeight();
        // 图片宽度小于高度 需要 则调整 宽高 值
        if (width < height && isRotate) {
            width = height;
            height = source.getWidth();
        }

        double sx = (double) targetW / width;
        double sy = (double) targetH / height;
        // 这里想实现在targetW，targetH范围内实现等比缩放
        if (sx > sy) {
            sx = sy;
            targetW = (int) (sx * source.getWidth());
        } else {
            sy = sx;
            targetH = (int) (sy * source.getHeight());
        }
        if (type == BufferedImage.TYPE_CUSTOM) {
            ColorModel cm = source.getColorModel();
            WritableRaster raster = cm.createCompatibleWritableRaster(targetW,
                    targetH);
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();
            target = new BufferedImage(cm, raster, alphaPremultiplied, null);
        } else {
            target = new BufferedImage(targetW, targetH, type);
        }
        Graphics2D g = target.createGraphics();
        // smoother than exlax:
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
        g.dispose();
        return target;
    }

    /**
     * 待合并的两张图必须满足这样的前提，如果水平方向合并，则高度必须相等；如果是垂直方向合并，宽度必须相等。
     * mergeImage方法不做判断，自己判断。
     *
     * @param img1         待合并的第一张图
     * @param img2         带合并的第二张图
     * @param isHorizontal 为true时表示水平方向合并，为false时表示垂直方向合并
     * @return 返回合并后的BufferedImage对象
     * @throws IOException
     */
    private static BufferedImage mergeImage(BufferedImage img1, BufferedImage img2, boolean isHorizontal) throws IOException {
        int w1 = img1.getWidth();
        int h1 = img1.getHeight();
        int w2 = img2.getWidth();
        int h2 = img2.getHeight();

        // 从图片中读取RGB
        int[] ImageArrayOne = new int[w1 * h1];
        // 逐行扫描图像中各个像素的RGB到数组中
        ImageArrayOne = img1.getRGB(0, 0, w1, h1, ImageArrayOne, 0, w1);
        int[] ImageArrayTwo = new int[w2 * h2];
        ImageArrayTwo = img2.getRGB(0, 0, w2, h2, ImageArrayTwo, 0, w2);
        // 生成新图片
        BufferedImage destImage = null;
        if (isHorizontal) {
            // 水平方向合并
            destImage = new BufferedImage(w1 + w2, h1, BufferedImage.TYPE_INT_RGB);
            // 设置上半部分或左半部分的RGB
            destImage.setRGB(0, 0, w1, h1, ImageArrayOne, 0, w1);
            destImage.setRGB(w1, 0, w2, h2, ImageArrayTwo, 0, w2);
        } else { // 垂直方向合并
            destImage = new BufferedImage(w1, h1 + h2, BufferedImage.TYPE_INT_RGB);
            // 设置上半部分或左半部分的RGB
            destImage.setRGB(0, 0, w1, h1, ImageArrayOne, 0, w1);
            // 设置下半部分的RGB
            destImage.setRGB(0, h1, w2, h2, ImageArrayTwo, 0, w2);
        }
        return destImage;
    }

    public static void main(String[] args) throws Exception {
        byte[] imgStr = ImageMergeUtil.imageMergeToBytes("C:\\Users\\Administrator\\Desktop\\助贷宝\\idcard\\1.jpg", "C:\\Users\\Administrator\\Desktop\\助贷宝\\idcard\\2.jpg");
        OutputStream fos = new FileOutputStream("f:\\hm\\1.jpg");
        fos.write(imgStr);

        FileImageOutputStream imageOutput = new FileImageOutputStream(new File("f:\\hm\\1.png"));
        imageOutput.write(imgStr, 0, imgStr.length);

        System.out.println("成功..........................");
    }
}