/*
 * Copyright (C),2015-2015. 城家酒店管理有限公司
 * FileName: BarcodeUtil.java
 * Author:   zhangxuxing
 * Date:     2017年11月3日
 * Description: 二维码生成
 * History: //修改记录 修改人姓名 修改时间 版本号 描述 需求来源
 */
package cn.common.util.barcode;

import cn.common.util.barcode.qrcode.components.MatrixToImageWriterEx;
import cn.common.util.barcode.qrcode.vo.QrcodeVo;
import com.google.zxing.common.BitMatrix;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;


public class QrcodeUtils {

	/**
	 * 生成二维码
	 * @param qrcodeVo 输入参数，必填
	 * @return
	 * 	 	  BufferedImage 图片流
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public static BufferedImage createImageStream(QrcodeVo qrcodeVo) throws URISyntaxException, IOException {
		if(qrcodeVo.getWidth() == null)
			qrcodeVo.setWidth(300);
		if(qrcodeVo.getHeight() == null)
			qrcodeVo.setHeight(300);
		BitMatrix matrix = MatrixToImageWriterEx.createQRCode(qrcodeVo);
		if(qrcodeVo.getMargin() != null && qrcodeVo.getMargin().intValue() == 0)
			matrix = deleteWhite(matrix);
		return MatrixToImageWriterEx.writeToStream(matrix, qrcodeVo);
	}
	
	public static BitMatrix deleteWhite(BitMatrix matrix){
	    int[] rec = matrix.getEnclosingRectangle();  
	    int resWidth = rec[2] + 1;  
	    int resHeight = rec[3] + 1;  
	  
	    BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
	    resMatrix.clear();  
	    for (int i = 0; i < resWidth; i++) {  
	        for (int j = 0; j < resHeight; j++) {  
	            if (matrix.get(i + rec[0], j + rec[1]))  
	                resMatrix.set(i, j);  
	        }  
	    }  
	    return resMatrix;  
	}  
	
}
