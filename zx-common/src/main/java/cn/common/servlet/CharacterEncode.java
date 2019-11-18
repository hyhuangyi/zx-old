package cn.common.servlet;

public enum CharacterEncode {
    UTF8("UTF-8");

    private String value;

    private CharacterEncode(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
