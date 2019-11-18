package cn.common.entity;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.Date;

public class SysTreeDict implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键",hidden = true)
    private Integer ddId;

    /**
     * 字典项,一组字典值的唯一标识
     */
    @NotEmpty
    @ApiModelProperty("字典项,一组字典值的唯一标识")
    private String ddItem;

    /**
     * 字典展示文本
     */
    @NotEmpty
    @ApiModelProperty("字典展示文本")
    private String ddText;

    /**
     * 字典值
     */
    @NotEmpty
    @ApiModelProperty("字典值")
    private String ddValue;

    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段",hidden = true)
    private Integer ddIndex;

    /**
     * 父级字典值
     */
    @ApiModelProperty(value = "父级字典值",hidden = true)
    private String parentValue;

    /**
     * 编辑人
     */
    @ApiModelProperty(value = "编辑人",hidden = true)
    private Integer updateUser;

    /**
     * 编辑时间
     */
    @ApiModelProperty(value = "编辑时间",hidden = true)
    private Date updateDate;

    /**
     * 删除标识
     */
    @ApiModelProperty("删除标识")
    private Boolean isdel;

    /**
     * SYS_TREE_DICT
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     * @return DD_ID 主键
     */
    public Integer getDdId() {
        return ddId;
    }

    /**
     * 主键
     * @param ddId 主键
     */
    public void setDdId(Integer ddId) {
        this.ddId = ddId;
    }

    /**
     * 字典项,一组字典值的唯一标识
     * @return DD_ITEM 字典项,一组字典值的唯一标识
     */
    public String getDdItem() {
        return ddItem;
    }

    /**
     * 字典项,一组字典值的唯一标识
     * @param ddItem 字典项,一组字典值的唯一标识
     */
    public void setDdItem(String ddItem) {
        this.ddItem = ddItem == null ? null : ddItem.trim();
    }

    /**
     * 字典展示文本
     * @return DD_TEXT 字典展示文本
     */
    public String getDdText() {
        return ddText;
    }

    /**
     * 字典展示文本
     * @param ddText 字典展示文本
     */
    public void setDdText(String ddText) {
        this.ddText = ddText == null ? null : ddText.trim();
    }

    /**
     * 字典值
     * @return DD_VALUE 字典值
     */
    public String getDdValue() {
        return ddValue;
    }

    /**
     * 字典值
     * @param ddValue 字典值
     */
    public void setDdValue(String ddValue) {
        this.ddValue = ddValue == null ? null : ddValue.trim();
    }

    /**
     * 排序字段
     * @return DD_INDEX 排序字段
     */
    public Integer getDdIndex() {
        return ddIndex;
    }

    /**
     * 排序字段
     * @param ddIndex 排序字段
     */
    public void setDdIndex(Integer ddIndex) {
        this.ddIndex = ddIndex;
    }

    /**
     * 父级字典值
     * @return PARENT_VALUE 父级字典值
     */
    public String getParentValue() {
        return parentValue;
    }

    /**
     * 父级字典值
     * @param parentValue 父级字典值
     */
    public void setParentValue(String parentValue) {
        this.parentValue = parentValue == null ? null : parentValue.trim();
    }

    /**
     * 编辑人
     * @return UPDATE_USER 编辑人
     */
    public Integer getUpdateUser() {
        return updateUser;
    }

    /**
     * 编辑人
     * @param updateUser 编辑人
     */
    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 编辑时间
     * @return UPDATE_DATE 编辑时间
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 编辑时间
     * @param updateDate 编辑时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 删除标识
     * @return ISDEL 删除标识
     */
    public Boolean getIsdel() {
        return isdel;
    }

    /**
     * 删除标识
     * @param isdel 删除标识
     */
    public void setIsdel(Boolean isdel) {
        this.isdel = isdel;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SysTreeDict other = (SysTreeDict) that;
        return (this.getDdId() == null ? other.getDdId() == null : this.getDdId().equals(other.getDdId()))
            && (this.getDdItem() == null ? other.getDdItem() == null : this.getDdItem().equals(other.getDdItem()))
            && (this.getDdText() == null ? other.getDdText() == null : this.getDdText().equals(other.getDdText()))
            && (this.getDdValue() == null ? other.getDdValue() == null : this.getDdValue().equals(other.getDdValue()))
            && (this.getDdIndex() == null ? other.getDdIndex() == null : this.getDdIndex().equals(other.getDdIndex()))
            && (this.getParentValue() == null ? other.getParentValue() == null : this.getParentValue().equals(other.getParentValue()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getUpdateDate() == null ? other.getUpdateDate() == null : this.getUpdateDate().equals(other.getUpdateDate()))
            && (this.getIsdel() == null ? other.getIsdel() == null : this.getIsdel().equals(other.getIsdel()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDdId() == null) ? 0 : getDdId().hashCode());
        result = prime * result + ((getDdItem() == null) ? 0 : getDdItem().hashCode());
        result = prime * result + ((getDdText() == null) ? 0 : getDdText().hashCode());
        result = prime * result + ((getDdValue() == null) ? 0 : getDdValue().hashCode());
        result = prime * result + ((getDdIndex() == null) ? 0 : getDdIndex().hashCode());
        result = prime * result + ((getParentValue() == null) ? 0 : getParentValue().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getUpdateDate() == null) ? 0 : getUpdateDate().hashCode());
        result = prime * result + ((getIsdel() == null) ? 0 : getIsdel().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", ddId=").append(ddId);
        sb.append(", ddItem=").append(ddItem);
        sb.append(", ddText=").append(ddText);
        sb.append(", ddValue=").append(ddValue);
        sb.append(", ddIndex=").append(ddIndex);
        sb.append(", parentValue=").append(parentValue);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", isdel=").append(isdel);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}