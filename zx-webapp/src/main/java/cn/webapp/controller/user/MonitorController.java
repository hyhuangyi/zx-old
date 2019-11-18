package cn.webapp.controller.user;

import cn.common.dao.mapper.TkTestDao;
import cn.common.entity.SysUser;
import cn.webapp.async.event.HyTestEvent;
import cn.webapp.domain.Server;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author huangy
 * @date 2019/7/24 16:42
 */
@Api(description = "系统监控")
@RequestMapping("/comm")
@Controller
public class MonitorController {
    @ApiOperation("服务监控")
    @GetMapping("/monitor")
    public String monitor(Model model)throws Exception{
        Server server=new Server();
        server.copyTo();
        model.addAttribute("server", server);
        return "server";
    }

    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private TkTestDao tkTestDao;

    @GetMapping("/event")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public void event(String str){
        SysUser sysUser=new SysUser();
        sysUser.setId(1);
        sysUser.setEmail(str);
        tkTestDao.updateByPrimaryKeySelective(sysUser);
        System.out.println("开始调监听");
        publisher.publishEvent(new HyTestEvent(this,str,100));
    }
}
