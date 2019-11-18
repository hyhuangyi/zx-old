package cn.common.servlet;

import cn.common.entity.Token;
import cn.common.servlet.BaseServletContextHolder;
/**
 * Created by tuzi on 17-5-4.
 */
public class ServletContextHolder extends BaseServletContextHolder {

    /**
     * 获取管理平台用户TOKEN
     * @return
     */
    public static Token getToken() {
        return BaseServletContextHolder.getToken();
    }

}
