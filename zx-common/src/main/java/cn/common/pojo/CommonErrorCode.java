package cn.common.pojo;

/**
 *   公共返回编码
 * @author Administrator
 *
 */
public enum CommonErrorCode implements BaseErrorCode {
	
	/**
     * 成功情况下的统一返回值
     */
    SUCCESS("200", "0", "成功"),
    FAIL("200", "1", "失败"),
    SYSTEM_ERROR("200", "SYSTEM_ERROR", "系统异常"),
    DAO_ERROR("200", "DAO_ERROR", "数据库异常"),
    DATABASE_ERROR("200", "DATABASE_ERROR", "数据库异常"),
    PARAM_LACK("200", "PARAM_LACK", "缺少参数[param]"),
    PARAM_ERROR("200", "PARAM_ERROR", "参数错误[param]"),
    REQUEST_TOKEN_NOT_EXIST("200", "REQUEST_TOKEN_NOT_EXIST", "http request 中没有包含TOKEN的信息！"),
    TOKEN_EXPIRE("200", "TOKEN_EXPIRE", "token失效！"),
    CAPTCHA_EXPIRE("200", "CAPTCHA_EXPIRE", "验证码失效！"),
    AUTHORITY_WITHOUT("200", "AUTHORITY_WITHOUT", "权限不足"),
    DATA_NOT_EXIST("200", "DATA_NOT_EXIST", "数据不存在"),
    NONLICET_FILE("200", "NONLICET_FILE", "非法文件！"),
    SERVER_BUSY("200", "SERVER_BUSY", "服务器繁忙,请稍候再试！"),
    USER_KICKOUT("200","USER_KICKOUT","当前用户已在其它设备上登录！");

    private String httpCode;
    private String code;
    private String description;

    private CommonErrorCode(String httpCode, String code, String description) {
        this.code = code;
        this.description = description;
        this.httpCode = httpCode;
    }

    public String getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    public String getHttpCode() {
        return this.httpCode;
    }

}
