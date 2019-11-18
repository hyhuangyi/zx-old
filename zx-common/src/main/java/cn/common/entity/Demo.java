package cn.common.entity;

public class Demo {
    private String createTime;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Demo() {
    }
    public Demo(String createTime) {
        this.createTime = createTime;
    }
}