package cn.webapp.controller.user;

import cn.common.dao.mapper.TkTestDao;
import cn.common.entity.SysUser;
import cn.common.entity.Token;
import cn.common.exception.ZXException;
import cn.common.servlet.ServletContextHolder;
import cn.webapp.aop.annotation.OperateLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangYi on 2018/8/13
 **/
@Api(description = "首页")
@Controller
public class IndexController {
    @Autowired
    private TkTestDao tkTestDao;
    /**
     * 首页
     * @return
     */
    @ApiOperation("首页")
    @RequestMapping(value = "/comm/index",method = RequestMethod.GET)
    @OperateLog(operation = "首页")
    public String hello() {
        return "index";
    }

    @ApiOperation("爱情树")
    @RequestMapping(value = "/comm/aqs",method = RequestMethod.GET)
    public String aqs(){
        return "aqs";
    }

    @ApiOperation("video插件")
    @RequestMapping(value = "/comm/video",method = RequestMethod.GET)
    public String video(){
        return "video";
    }

    @ApiOperation("跑马灯")
    @RequestMapping(value = "/comm/pmd",method = RequestMethod.GET)
    public String pmd(){
        return "pmd";
    }

    @ApiOperation("通用mapper")
    @ResponseBody
    @RequestMapping(value = "/comm/tkMapper",method = RequestMethod.GET)
    public List<SysUser> tkMapper(){

      return   tkTestDao.selectAll();
    }

    @ApiOperation("测试专用")
    @RequestMapping(value = "/comm/test",method = RequestMethod.POST)
    @ResponseBody
    @Cacheable(value = "city",key="#kdd")
    public List test(Integer  kdd){
        List list=new ArrayList();
        for(int i=0;i<100;i++){
            list.add(i);
        }
        return list;
    }

    /**
     * 权限必须要有前缀ROLE_
     * @return
     */
    @PreAuthorize("hasRole('ROLE_admin')")
    @ApiOperation("token获取")
    @RequestMapping(value = "token",method = RequestMethod.GET)
    @ResponseBody
    public Token token()  {
        System.err.println(Thread.currentThread().getName());
        Token token= ServletContextHolder.getToken();
        return token;
    }
    @ApiOperation("跨域测试")
    @RequestMapping(value = "/comm/zx",method = RequestMethod.GET)
    @ResponseBody
    public String zx(HttpServletRequest request)  {
        System.out.println(request.getHeader("authorization"));
       return "http://xll-test.oss-cn-shanghai.aliyuncs.com/template/1571975546369560.png";
    }

    @ApiOperation("读取jar下文件测试")
    @RequestMapping(value = "/comm/readResource",method = RequestMethod.GET)
    @ResponseBody
    public void readResource(HttpServletResponse response) {
        //文件流
        InputStream is = getClass().getResourceAsStream("/generatorConfig.xml");
        try {
        response.setContentType("application/force-download");//设置返回的文件类型,强制下载不打开
        response.addHeader("Content-Length", "" + is.available());//文件大小
        response.addHeader(
                "Content-disposition",
                "attachment;filename="
                        + URLEncoder.encode("读取jar下文件测试.xml","utf8"));// 设置头部信息

            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = is.read(buffer)) != -1) {
                response.getOutputStream().write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            throw  new ZXException("下载异常");
        } finally {
            try {
                if(is!=null){
                    is.close();
                }
                response.getOutputStream().close();
            }catch (Exception e){
                throw new ZXException("流关闭异常");
            }
        }
    }

}
