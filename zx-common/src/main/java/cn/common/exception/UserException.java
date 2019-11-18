package cn.common.exception;

/**
 * Created by huangYi on 2018/9/5
 **/
public class UserException extends RuntimeException{
    public UserException() {
    }

    public UserException(String message) {
        super(message);
    }
}
