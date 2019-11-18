package cn.common.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * @author huangy
 * @date 2019/6/28 14:20
 */
public class BlackListExport {
    @Excel(name = "客户姓名", width = 15, orderNum = "2")
    private String name;
    @Excel(name = "备注", width = 10, orderNum = "1")
    private String remark;
    @Excel(name = "手机号", width = 15, orderNum = "0")
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BlackListExport() {
    }

    public BlackListExport(String name, String remark, String phone) {
        this.name = name;
        this.remark = remark;
        this.phone = phone;
    }
}
