package cn.common.util.barcode.qrcode.components;
/*
 * 定制logo属性类
 */

import java.awt.*;

public class MatrixToLogoImageConfig {
	//logo默认边框颜色
	public static final Color DEFAULT_BORDERCOLOR = Color.BLACK;
	//logo默认边框宽度
	public static final int DEFAULT_BORDER = 0;
	//logo大小默认为照片的1/7
	public static final int DEFAULT_LOGOPART = 7;

	private final int border = DEFAULT_BORDER;
	private final Color borderColor;
	private final int logoPart;
	
	/**
	 * Creates a default config with on color and off color, generating normal black-on-white barcodes.
	 */
	public MatrixToLogoImageConfig() {
		this(DEFAULT_BORDERCOLOR, DEFAULT_LOGOPART);
	}

	
	public MatrixToLogoImageConfig(Color borderColor, int logoPart) {
		this.borderColor = borderColor;
		this.logoPart = logoPart;
	}


	public Color getBorderColor() {
		return borderColor;
	}


	public int getBorder() {
		return border;
	}


	public int getLogoPart() {
		return logoPart;
	}
}