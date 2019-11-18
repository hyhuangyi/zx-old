package cn.common.dao.mapper;

import cn.common.entity.DictDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SysTreeDictMapper {
    List<DictDTO> listDictsByCode(String code);
    List<DictDTO> listSubDicts(@Param("ddItem")String ddItem,@Param("ddValue") String ddValue);
}