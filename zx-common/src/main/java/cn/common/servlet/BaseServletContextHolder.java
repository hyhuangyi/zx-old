package cn.common.servlet;

import cn.common.entity.Token;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServletContextHolder {
    private static final ThreadLocal<ServletData> manager = new ThreadLocal();

    public BaseServletContextHolder() {
    }

    public static void prepare(HttpServletRequest request, HttpServletResponse response) {
        prepare((Token)null, request, response);
    }

    public static <T> void prepare(Token data, HttpServletRequest request, HttpServletResponse response) {
        ServletData<T> sd = new ServletData();
        sd.setRequest(request);
        sd.setResponse(response);
        sd.setData(data);
        manager.set(sd);
    }

    public static HttpServletRequest getRequest() {
        return ((ServletData)manager.get()).getRequest();
    }

    public static HttpServletResponse getResponse() {
        return ((ServletData)manager.get()).getResponse();
    }

    public static Token getToken() {
        return ((ServletData)manager.get()).getData();
    }

    public static void setToken(Token token) {
        ServletData servletData = (ServletData)manager.get();
        servletData.setData(token);
        manager.set(servletData);
    }

    public static void setAttribute(String name, Object value) {
        ServletData servletData = (ServletData)manager.get();
        if (name == null) {
            throw new IllegalArgumentException("BaseServletContextHolder.setAttribute.name null");
        } else {
            if (value == null) {
                servletData.getAttributes().remove(name);
            }

            servletData.getAttributes().put(name, value);
        }
    }

    public static Object getAttribute(String name) {
        ServletData servletData = (ServletData)manager.get();
        return name == null ? null : servletData.getAttributes().get(name);
    }

    public static void clear() {
        manager.remove();
    }

    public static HttpServletResponse setResponseContext(ContextType contentType, String... header) {
        HttpServletResponse response = getResponse();
        return setResponseContext(response, contentType, header);
    }

    public static HttpServletResponse setResponseContext(HttpServletResponse response, ContextType contentType, String... header) {
        response.setCharacterEncoding(CharacterEncode.UTF8.getValue());
        response.setHeader("Content-type", contentType.getValue() + ";charset=UTF-8");
        return response;
    }
}
