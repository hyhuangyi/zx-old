package cn.common.dao.mapper;

import cn.common.entity.City;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by huangYi on 2018/3/11
 **/
@Mapper
@Repository
public interface CityDao {
//放开注释加入redis缓存
    @Cacheable(value = "city",keyGenerator = "keyGenerator")
    List<City> queryAllCity();

    City getCity(String code);

    /*直接用注解写sql
    *cacheNames/value 指定缓存名字
    * key   缓存数据使用的key 默认使用方法参数的值
    *
    * */
    @Cacheable(value = "city",key ="'HUONU_API_'+#p0")//使用方法名字
    @Select(" SELECT *FROM cd_city ORDER BY CODE ASC ")
    List<City> query(Integer id);
}
