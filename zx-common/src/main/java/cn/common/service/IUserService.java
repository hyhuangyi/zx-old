package cn.common.service;

import cn.common.entity.DictDTO;
import cn.common.entity.SysUser;

import java.util.List;
import java.util.Map;

/**
 * Created by huangYi on 2018/9/6
 **/
public interface IUserService {

    boolean register(SysUser sysUser);

    /**
     * 根据字典代码获取字典值列表,带层级
     * @param code
     * @return
     */
    Map<String,List<DictDTO>> listDictsByCode(String code);
    /**
     * 根据字典代码获取字典所有项，不带层级
     * @param code
     * @return
     */
    Map<String,List<DictDTO>> listDicts(String code);
    /**
     * 根据字典代码获取字典二级项
     * @param code
     * @return
     */
    Map<String,List<DictDTO>> listSubDicts(String code,String value);

}
