package cn.common.dao.mapper;


import cn.common.entity.SysUser;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SysUserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    List<SysUser> getUserByName(String userName);

    List<SysUser> getUserByPhone(String phone);

    List<SysUser> getUserByEmail(String email);
}