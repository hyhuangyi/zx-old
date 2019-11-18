package cn.common.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;

public class BlackList implements Serializable {

    private static final long serialVersionUID = 7637066289484530260L;

    @Excel(name="备注")
    private String remark;
    @Excel(name="手机号码")
    private String phone;
    @Excel(name="身份证号码")
    private String cardNo;

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

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
}
