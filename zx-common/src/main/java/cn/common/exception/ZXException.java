package cn.common.exception;

import java.io.Serializable;

/**
 * Created by huangYi on 2018/11/1.
 **/
public class ZXException extends RuntimeException implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public ZXException() {
    }

    public ZXException(String message) {
        super(message);
    }
    

    public ZXException(String message, Throwable cause) {
        super(message, cause);
    }
}
