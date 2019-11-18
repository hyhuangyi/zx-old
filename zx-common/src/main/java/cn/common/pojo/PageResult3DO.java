package cn.common.pojo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by huangYi on 2018/11/1.
 **/
public class PageResult3DO<T,M> extends ResultDO {
    @ApiModelProperty("分页总条数")
    private Long recordsTotal;
    @ApiModelProperty("list")
    private List<T> data;
    @ApiModelProperty("汇总数据")
    private M monthSum;


    public PageResult3DO( List<T> data,Long recordsTotal, M monthSum) {
        super("200","成功");
        this.recordsTotal = recordsTotal;
        this.data = data;
        this.monthSum = monthSum;
    }

    public M getMonthSum() {
        return monthSum;
    }

    public void setMonthSum(M monthSum) {
        this.monthSum = monthSum;
    }

    public Long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    @Override
    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
