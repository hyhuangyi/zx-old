package cn.webapp.controller.zx;

import cn.common.entity.Demo;
import cn.webapp.aop.annotation.LogInfo;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author huangy
 * @date 2019/7/16 9:33
 */
@Api(description = "ResponseBody测试")
@RequestMapping("/comm")
@RestController
public class RequestBodyController {
    /**
     *  map形式
     *  {"age":1}
     * @param map
     * @return
     */
    @LogInfo
    @PostMapping(value = "/body1")
    public Map body1(@RequestBody Map map) {
        System.out.println(map);
        return map;
    }

    /**
     * 实体类形式
     * {"age":1,"name":"zx","createTime":"2019-07-16"}
     * @param demo
     * @return
     */
    @LogInfo
    @PostMapping(value = "/body2")
    public Demo body2(@RequestBody Demo demo) {
        System.out.println(demo.getCreateTime());
        return demo;
    }

    /**
     *  测试 ZxRequestBodyAdvice->handleEmptyBody
     *  json串(带@RequestBody)
     *  "{'age':1}"
     * @param str
     * @return
     */
    @LogInfo
    @PostMapping(value = "/body3")
    public JSONObject body3(@RequestBody String str) {
        JSONObject jsonObject = JSONObject.parseObject(str);
        return jsonObject;
    }

    /**
     * json串（不带@RequestBody）
     * {"age":1}
     * @param str
     * @return
     */
    @LogInfo
    @PostMapping(value = "/body4")
    public JSONObject body4(String str) {
        JSONObject jsonObject = JSONObject.parseObject(str);
        return jsonObject;
    }
}
