package cn.common.service;

import cn.common.dao.mapper.SysTreeDictMapper;
import cn.common.dao.mapper.SysUserDao;
import cn.common.entity.DictDTO;
import cn.common.entity.SysUser;
import cn.common.exception.UserException;
import cn.common.util.comm.RegexUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by huangYi on 2018/9/6
 **/
@Service
public class UserService implements IUserService {

    @Autowired
    private SysUserDao userDao;
    @Autowired
    private SysTreeDictMapper sysTreeDictMapper;

    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    /**
     * 注册
     * @param sysUser
     * @return
     */
    @Override
    public boolean register(SysUser sysUser) {

        if(StringUtils.isEmpty(sysUser.getEmail())){
            throw new UserException("邮箱不能为空");
        }
        if(StringUtils.isEmpty(sysUser.getPhone())){
            throw new UserException("手机号码不能为空");
        }
        if(!RegexUtils.checkEmail(sysUser.getEmail())){
            throw new UserException("邮箱格式不正确！");
        }
        if(!RegexUtils.checkMobile(sysUser.getPhone())){
            throw new UserException("手机格式不正确");
        }
        List<SysUser> list1=userDao.getUserByEmail(sysUser.getEmail());
        List<SysUser> list2=userDao.getUserByPhone(sysUser.getPhone());
        List<SysUser> list3=userDao.getUserByName(sysUser.getUsername());
        if(list1.size()>=1){
            throw new UserException("邮箱已被注册");
        }
        if(list2.size()>=1){
            throw new UserException("手机号码已被注册");
        }
        if(list3.size()>=1){
            throw new UserException("用户名已被注册");
        }
        sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
        sysUser.setCreateDate(new Date());
        sysUser.setUpdateDate(new Date());
        userDao.insertSelective(sysUser);
        return true;
    }

    @Override
    public Map<String, List<DictDTO>> listSubDicts(String code, String value) {
        Map<String, List<DictDTO>> map = Maps.newHashMap();
        if (code != null && value != null) {
            String[] split = value.split(",");
            for (String str : split) {
                List<DictDTO> list = sysTreeDictMapper.listSubDicts(code, str);
                map.put(str, list);
            }
        }
        return map;
    }

    @Override
    public Map<String, List<DictDTO>> listDictsByCode(String code) {
        return listDictsHasLevel(code, true);
    }

    @Override
    public Map<String, List<DictDTO>> listDicts(String code) {
        return listDictsHasLevel(code, false);
    }



    private Map<String, List<DictDTO>> listDictsHasLevel(String code, boolean level) {
        Map<String, List<DictDTO>> map = Maps.newHashMap();
        if (code != null) {
            String[] split;
            if (code.contains("/")) {
                split = code.split("/");
            } else {
                split = code.split(",");
            }
            for (String str : split) {
                List<DictDTO> list = sysTreeDictMapper.listDictsByCode(str);
                if (level) {
                    List<DictDTO> ret = listSubDicts(list);
                    map.put(str, ret);
                } else {
                    map.put(str, list);
                }
            }
        }
        return map;
    }


    public List<DictDTO> listSubDicts(List<DictDTO> list) {
        List<DictDTO> ret = Lists.newArrayList();
        for (DictDTO dictDTO : list) {
            if (dictDTO.getParentValue().equals("0")) {
                getChild(dictDTO, list);
                ret.add(dictDTO);
            }
        }
        return ret;
    }
    /*对子集处理*/
    private void getChild(DictDTO dictDTO, List<DictDTO> list) {
        List<DictDTO> children = dictDTO.getDictDTOS();
        for (DictDTO dto : list) {//获取子集
            if (dto.getParentValue().equals(dictDTO.getDdValue())) {
                children.add(dto);
            }
        }
        if (!CollectionUtils.isEmpty(children)) {
            for (DictDTO child : children) {
                getChild(child, list);
            }
        }
    }



}
