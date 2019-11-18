package cn.webapp.controller.zx;

import cn.common.entity.First;
import cn.webapp.aop.annotation.TimeCount;
import cn.webapp.aop.annotation.ValidatedRequest;
import cn.common.entity.City;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangYi on 2018/3/2
 **/
@Api(description = "校验注解")
@Controller
@Validated
public class ValidDemoController {

    private static final Logger logger = Logger.getLogger(ValidDemoController.class);

    /**
     * 默认校验失败后会让方法抛出Unchecked Exception，
     * 在springboot中默认是会让其跳转到error页面，所以只要添加异常处理器
     * 对model（对象）校验可以不需要再开头加上@Validated注解
     * @return
     */
    @ApiOperation("@Valid@ModelAttribute注解测试")
    @RequestMapping(value = "exceptionTest",method = RequestMethod.GET)
    @ResponseBody
    @ValidatedRequest
    @TimeCount
    @PreAuthorize("#id>10")//id大于10才能访问
    public Map exceptionTest(@Validated({First.class})@ModelAttribute City city, BindingResult result,@ApiParam("id")@RequestParam Integer id) {
        Map map=new HashMap();
        map.put("code",city.getCode());
        map.put("ass", SecurityContextHolder.getContext().getAuthentication());
        return map;
    }

    /**
     * Controller头上加了@Validated注解并配合methodValidationPostProcessor
     * 来对单个参数来检验
     * @param name
     * @return
     */
    @ApiOperation("针对单个参数校验")
    @RequestMapping(value = "/comm/exceptionTest1",method = RequestMethod.GET)
    @ResponseBody
    @TimeCount
    public String exceptionTest1(@ApiParam("姓名") @NotEmpty(message = "姓名不能为空")@RequestParam String name,@NotEmpty(message = "年龄不能为空") @ApiParam("年龄") @RequestParam String age)  {
        System.out.println(name);
        return name;
    }

}
