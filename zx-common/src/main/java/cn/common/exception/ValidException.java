package cn.common.exception;

/**
 * Created by huangYi on 2018/9/6
 **/
public class ValidException extends RuntimeException {
    public ValidException() {
        super();
    }

    public ValidException(String message) {
        super(message);
    }
}
