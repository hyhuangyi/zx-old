package cn.common.servlet;

import cn.common.entity.Token;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ServletData<T> {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Token data;
    private ConcurrentMap<String, Object> attributes = new ConcurrentHashMap();

    public ServletData() {
    }

    public HttpServletRequest getRequest() {
        return this.request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return this.response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public Token getData() {
        return this.data;
    }

    public void setData(Token data) {
        this.data = data;
    }

    public ConcurrentMap<String, Object> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(ConcurrentMap<String, Object> attributes) {
        this.attributes = attributes;
    }
}
