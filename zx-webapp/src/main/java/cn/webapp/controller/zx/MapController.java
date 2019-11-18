package cn.webapp.controller.zx;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by huangYi on 2018/9/20
 **/
@Api(description = "地图")
@Controller
public class MapController {
    @ApiOperation("地图")
    @RequestMapping(value = "/comm/getMap",method = RequestMethod.GET)
    public String getMap(){
        return "map";
    }

    @ApiOperation("房屋估值")
    @RequestMapping(value = "/comm/fwgz",method = RequestMethod.GET)
    public String fwgz(){
        return "fcgz";
    }
}
