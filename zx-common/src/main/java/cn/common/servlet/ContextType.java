package cn.common.servlet;

public enum ContextType {
    TEXT_TYPE("text/plain"),
    JSON_TYPE("application/json"),
    XML_TYPE("text/xml"),
    HTML_TYPE("text/html"),
    JS_TYPE("text/javascript"),
    EXCEL_TYPE("application/vnd.ms-excel");

    private String value;

    private ContextType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

