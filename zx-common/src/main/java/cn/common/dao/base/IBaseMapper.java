package cn.common.dao.base;

import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Mapper接口：基本的增、删、改、查方法
 * MySqlMapper：针对MySQL的额外补充接口，支持批量插入
 * IdsMapper：使mapper支持批量ID操作
 * @param <T>
 */
public interface IBaseMapper<T> extends Mapper<T> , MySqlMapper<T> , IdsMapper<T> {


}
