package cn.common.entity;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by tuzi on 17-4-17.
 */
public class CaptchaDTO implements Serializable {

    @ApiModelProperty(value = "宽度")
    private Integer width;

    @ApiModelProperty(value = "高度")
    private Integer height;


    @ApiModelProperty(value = "使用场景,1为用户登录，2为忘记密码,3为领取额度,4,申请进件",required = true)
    private int scene;


    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public int getScene() {
        return scene;
    }

    public void setScene(int scene) {
        this.scene = scene;
    }
}
