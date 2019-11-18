package cn.webapp.controller.user;

import cn.common.entity.MyUserDetails;
import cn.common.entity.SysUser;
import cn.common.service.IUserService;
import cn.common.servlet.ServletContextHolder;
import cn.common.util.jwt.JwtUtil;
import cn.webapp.aop.annotation.LogInfo;
import cn.webapp.aop.annotation.OperateLog;
import cn.webapp.aop.annotation.ValidatedRequest;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

/**
 * Created by huangYi on 2018/9/5
 **/
@Api(description = "登陆注册")
@Controller
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private IUserService userService;
    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation("登陆页面")
    @LogInfo
    @RequestMapping(value = "/comm/toLogin",method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("user", new SysUser());
        return "login";
    }

    @ApiOperation("注册页面")
    @LogInfo
    @RequestMapping(value="/comm/toRegister",method=RequestMethod.GET)
    public ModelAndView toRegister(Model model){
        model.addAttribute("user", new SysUser());
        return new ModelAndView("register");
    }

    @ApiOperation("登陆")
    @RequestMapping(value = "/comm/login",method = RequestMethod.POST)
    @ValidatedRequest
    @OperateLog(operation = "#{#user.username}用户登入")
    public String login(@Valid@ModelAttribute(value="user")  SysUser user, BindingResult result, HttpServletResponse response)throws Exception{
        //验证
        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        //通过后将authentication放入SecurityContextHolder里
        SecurityContextHolder.getContext().setAuthentication(authentication);
        MyUserDetails userDetail=(MyUserDetails) authentication.getPrincipal();
        ServletContextHolder.setToken(userDetail.getToken());
        //生成token str
        String json=JSON.toJSONString(userDetail.getToken());
        String token= JwtUtil.getToken(json);
        response.addHeader(JwtUtil.AUTHORIZATION,token);
        //存入redis
        JwtUtil.saveTokenInfo(userDetail.getToken());
        return "index";
    }

    @ApiOperation("注册")
    @RequestMapping(value = "/comm/register",method = RequestMethod.POST)
    @ValidatedRequest
    public String register(@Valid@ModelAttribute(value = "user") SysUser user,BindingResult result){

        boolean bool=userService.register(user);
        if(bool){
            return "login";
        }else {
            return "register";
        }
    }
}
