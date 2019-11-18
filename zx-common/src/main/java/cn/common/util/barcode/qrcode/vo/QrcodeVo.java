package cn.common.util.barcode.qrcode.vo;

public class QrcodeVo {

	private String url;
	private Integer width;
	private Integer height;
	private Integer margin;
	private Enum<?> errorCorrection;
	private String needLogo;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public Integer getMargin() {
		return margin;
	}
	public void setMargin(Integer margin) {
		this.margin = margin;
	}
	public Enum<?> getErrorCorrection() {
		return errorCorrection;
	}
	public void setErrorCorrection(Enum<?> errorCorrection) {
		this.errorCorrection = errorCorrection;
	}
	public String getNeedLogo() {
		return needLogo;
	}
	public void setNeedLogo(String needLogo) {
		this.needLogo = needLogo;
	}
	
}
