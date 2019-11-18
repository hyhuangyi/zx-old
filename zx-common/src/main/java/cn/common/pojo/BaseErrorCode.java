package cn.common.pojo;

public interface BaseErrorCode {
    default String getHttpCode() {
        return "200";
    }

    default String getCode() {
        return "SYSTEM_ERROR";
    }

    String getDescription();
}