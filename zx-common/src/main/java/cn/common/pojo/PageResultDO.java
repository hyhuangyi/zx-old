package cn.common.pojo;

import java.util.List;

/**
 * Created by huangYi on 2018/11/1.
 **/
public class PageResultDO<T> extends ResultDO {
    private Long recordsTotal;
    private List<T> data;

    public PageResultDO(List<T> data, Long recordsTotal) {
        super("200","成功");
        this.recordsTotal = recordsTotal;
        this.data = data;
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
