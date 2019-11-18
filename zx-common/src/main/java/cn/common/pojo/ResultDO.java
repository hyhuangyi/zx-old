package cn.common.pojo;

import java.io.Serializable;

/**
 * Created by huangYi on 2018/11/1.
 **/
public class ResultDO<M>implements Serializable {
    private static final long serialVersionUID = -1369434131311843294L;
    private String code;
    private String msg;
    private String requestId;
    private M data;

    public ResultDO(){
    }
    public ResultDO( String requestId,String code, String msg, M data) {
        this.code = code;
        this.msg = msg;
        this.requestId = requestId;
        this.data = data;
    }

    public ResultDO(BaseErrorCode code) {
        this.code = code.getCode();
        this.msg = code.getDescription();
    }


    public ResultDO(String code) {
        this.code = code;
    }

    public ResultDO(M data) {
        this.data = data;
    }

    public ResultDO(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultDO(String code, String msg, M data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultDO(String code, M data) {
        this.code = code;
        this.data = data;
    }

    public M getData() {
        return data;
    }

    public void setData(M data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
