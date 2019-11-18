package cn.common.service;

import cn.common.dao.mapper.SysUserDao;
import cn.common.entity.MyUserDetails;
import cn.common.entity.SysUser;
import cn.common.entity.Token;
import cn.common.exception.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by huangYi on 2018/8/31
 **/
@Component
public class MyUserDetailService implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysUserDao sysUserDao;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        logger.info("用户名："+userName);
        List<SysUser> sysUsers=sysUserDao.getUserByName(userName);
        if (CollectionUtils.isEmpty(sysUsers)) {
            throw new UserException("用户不存在");
        }
        SysUser sysUser=sysUsers.get(0);

        Token token=new Token();
        token.setEmail(sysUser.getEmail());
        token.setPhone(sysUser.getPhone());
        token.setUsername(sysUser.getUsername());
        token.setUuid(UUID.randomUUID().toString());

        MyUserDetails user =new MyUserDetails(token);
        user.setPassword(sysUser.getPassword());
        return user;
    }
}
