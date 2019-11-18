package cn.webapp.controller.zx;

import cn.common.dao.mapper.CityDao;
import cn.common.entity.City;
import cn.webapp.aop.annotation.TimeCount;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(description="缓存demo")
@Controller
@RequestMapping("/comm")
public class CacheDemoController {
    private static final Logger logger = Logger.getLogger(CacheDemoController.class);
    @Autowired
    private CityDao cityDao;
    @Autowired
    private RedisTemplate redisTemplate;


    @ApiOperation("城市列表（带分页）")
    @RequestMapping(value = "/getCityListPage",method = RequestMethod.GET)
    public String getCityListPage(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum, HttpServletRequest request) {
        long count =cityDao.queryAllCity().size();
        long  allpage=(long) Math.ceil(count/20);
        if(pageNum<=1){
            pageNum=1;
        }else if(pageNum>allpage){
            pageNum=(int)allpage+1;
        }
        PageHelper.startPage(pageNum, 20);
        List<City> list = cityDao.queryAllCity();
        request.setAttribute("city",list);
        request.setAttribute("page",pageNum);
        return "city";
    }

    @ApiOperation("获取所有城市数据")
    @RequestMapping(value = "/city",method = RequestMethod.GET)
    @ResponseBody
    @TimeCount
    public List<City> queryAllCity(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {
        List<City> list = cityDao.queryAllCity();
        return list;
    }

    @ApiOperation("获取单个城市数据")
    @RequestMapping(value = "/getCity",method = RequestMethod.GET)
    @ResponseBody
    public City getCity(String code){
        String key="city"+code;
        ValueOperations<String,City> operations=redisTemplate.opsForValue();
        boolean hasKey=redisTemplate.hasKey(key);
        if(hasKey){
            City city=operations.get(key);
            logger.info("getCity : 从缓存中获取了城市 >> " +city);
            return  city;
        }
        City city=cityDao.getCity(code);
        operations.set(key,city);
        return city;
    }

    @ApiOperation("测试注解sql")
    @GetMapping(value = "/query/{id}")
    @ResponseBody
    public List<City> query(@PathVariable("id") Integer id){
        return cityDao.query(id);
    }

}
