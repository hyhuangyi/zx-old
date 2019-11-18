package cn.common.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by huangYi on 2018/8/22
 **/
public class SysExcuteTimeLog implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer excuteTime;
    private String excuteMethod;
    private Date createDate;
    private Date updateDate;
    private Date operateDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getExcuteTime() {
        return excuteTime;
    }

    public void setExcuteTime(Integer excuteTime) {
        this.excuteTime = excuteTime;
    }

    public String getExcuteMethod() {
        return excuteMethod;
    }

    public void setExcuteMethod(String excuteMethod) {
        this.excuteMethod = excuteMethod;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    @Override
    public String toString() {
        return "SysExcuteTimeLog{" +
                "id=" + id +
                ", excuteTime=" + excuteTime +
                ", excuteMethod='" + excuteMethod + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", operateDate=" + operateDate +
                '}';
    }
}
