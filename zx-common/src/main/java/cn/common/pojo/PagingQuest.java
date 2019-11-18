package cn.common.pojo;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;

/**
 * 分页查询请求参数
 *
 * @author huangyi
 */
public class PagingQuest {
    @ApiModelProperty(value = "起始记录数" ,hidden=true)
    private Integer rowStart;

    @ApiModelProperty(value = "第几页")
    private Integer page =1;

    @ApiModelProperty(value = "每页记录数")
    @Max(value=100000)
    private Integer rows=10 ;

    public Integer getRowStart() {
        return rowStart;
    }

    public void setRowStart(Integer rowStart) {
        this.rowStart = rowStart;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "PagingQuest{" +
                "rowStart=" + rowStart +
                ", page=" + page +
                ", rows=" + rows +
                '}';
    }
}
