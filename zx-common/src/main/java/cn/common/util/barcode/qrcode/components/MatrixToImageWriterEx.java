package cn.common.util.barcode.qrcode.components;

import cn.common.util.barcode.qrcode.vo.QrcodeVo;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Hashtable;


public class MatrixToImageWriterEx {
	
	private static final MatrixToLogoImageConfig DEFAULT_CONFIG = new MatrixToLogoImageConfig();
	
	/**
	 * 根据内容生成二维码数据
	 * @return
	 */
	public static BitMatrix createQRCode(QrcodeVo qrcodeVo){
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		//设置字符编码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        // 指定纠错等级
		hints.put(EncodeHintType.ERROR_CORRECTION, qrcodeVo.getErrorCorrection() != null ? qrcodeVo.getErrorCorrection() : ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.MARGIN, qrcodeVo.getMargin() != null ? qrcodeVo.getMargin() : 1);
        BitMatrix matrix = null;
        try {  
            matrix = new MultiFormatWriter().encode(qrcodeVo.getUrl(), BarcodeFormat.QR_CODE, qrcodeVo.getWidth(), qrcodeVo.getHeight(), hints);
        } catch (WriterException e) {
            e.printStackTrace();  
        }
        return matrix;
	}
	
	/**
	 * 写入二维码、以及将照片logo写入二维码中
	 * @param matrix 要写入的二维码
	 * @param format 二维码照片格式
	 * @param imagePath 二维码照片保存路径
	 * @param logoPath logo路径
	 * @throws IOException
	 */
	public static void writeToFile(BitMatrix matrix, String format, String imagePath, String logoPath) throws IOException {
		MatrixToImageWriter.writeToFile(matrix, format, new File(imagePath));
		
		//添加logo图片, 此处一定需要重新进行读取，而不能直接使用二维码的BufferedImage 对象
//		BufferedImage img = ImageIO.read(new File(imagePath));
//		MatrixToImageWriterEx.overlapImage(img, format, imagePath, logoPath, DEFAULT_CONFIG);
	}
	/**
	 * 写入二维码、以及将照片logo写入二维码中
	 * @param matrix 要写入的二维码
	 * @param format 二维码照片格式
	 * @param imagePath 二维码照片保存路径
	 * @param logoPath logo路径
						 * @param logoConfig logo配置对象
						 * @throws IOException
	 */
	public static void writeToFile(BitMatrix matrix, String format, String imagePath, String logoPath, MatrixToLogoImageConfig logoConfig) throws IOException {
		MatrixToImageWriter.writeToFile(matrix, format, new File(imagePath));
//		BufferedImage img = ImageIO.read(new File(imagePath));
//		MatrixToImageWriterEx.overlapImage(img, format, imagePath, logoPath, logoConfig);
	}
	
	public static BufferedImage writeToStream(BitMatrix matrix, QrcodeVo qrcodeVo) throws IOException, URISyntaxException {
//		return MatrixToImageWriter.writeToStream(matrix, format, new FileOutputStream(new File(imagePath)));
		BufferedImage bi = MatrixToImageWriter.toBufferedImage(matrix);
		//添加logo图片, 此处一定需要重新进行读取，而不能直接使用二维码的BufferedImage 对象
		if(qrcodeVo.getNeedLogo() != null && qrcodeVo.getNeedLogo().equals("1")){
			InputStream in = MatrixToImageWriterEx.class.getResourceAsStream("/qrcode_logo.png");
			return MatrixToImageWriterEx.overlapImage(bi, "png", in, DEFAULT_CONFIG);
		}
		return bi;
	}

	/**
	 * 将照片logo添加到二维码中间
	 * @param image 生成的二维码照片对象
	 * @param imagePath 照片保存路径
	 * @param in logo照片路径
	 * @param formate 照片格式
	 * @return 
	 */
	public static BufferedImage overlapImage(BufferedImage image, String formate, InputStream in, MatrixToLogoImageConfig logoConfig) {
		try {
			BufferedImage logo = ImageIO.read(in);
			Graphics2D g = image.createGraphics();
			//考虑到logo照片贴到二维码中，建议大小不要超过二维码的1/5;
			int width = image.getWidth() / 5;
			int height = image.getHeight() / 5;
//			int width = image.getWidth() / logoConfig.getLogoPart();
//			int height = image.getHeight() / logoConfig.getLogoPart();
			//logo起始位置，此目的是为logo居中显示
			int x = (image.getWidth() - width) / 2;
			int y = (image.getHeight() - height) / 2;
			//绘制图
			g.drawImage(logo, x, y, width, height, null);
			g.dispose();
			//写入logo照片到二维码
//			ImageIO.write(image, formate, new File(imagePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image;
	}
	
}
