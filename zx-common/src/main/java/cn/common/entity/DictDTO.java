package cn.common.entity;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author xuyh
 * @date 2017/4/14
 */
public class DictDTO {
    @ApiModelProperty("字典类型")
    private String ddItem;
    @ApiModelProperty("字典值")
    private String ddValue;
    @ApiModelProperty("字典文本")
    private String ddText;
    @ApiModelProperty("顺序")
    private Integer index;
    @ApiModelProperty("父级字典值")
    private String parentValue;
    private List<DictDTO> dictDTOS= Lists.newArrayList();

    public String getParentValue() {
        return parentValue;
    }

    public void setParentValue(String parentValue) {
        this.parentValue = parentValue;
    }

    public List<DictDTO> getDictDTOS() {
        return dictDTOS;
    }

    public void setDictDTOS(List<DictDTO> dictDTOS) {
        this.dictDTOS = dictDTOS;
    }

    public String getDdItem() {
        return ddItem;
    }

    public void setDdItem(String ddItem) {
        this.ddItem = ddItem;
    }

    public String getDdText() {
        return ddText;
    }

    public void setDdText(String ddText) {
        this.ddText = ddText;
    }

    public String getDdValue() {
        return ddValue;
    }

    public void setDdValue(String ddValue) {
        this.ddValue = ddValue;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "DictDTO{" +
                "ddItem='" + ddItem + '\'' +
                ", ddValue='" + ddValue + '\'' +
                ", ddText='" + ddText + '\'' +
                ", index=" + index +
                ", parentValue='" + parentValue + '\'' +
                ", dictDTOS=" + dictDTOS +
                '}';
    }
}
