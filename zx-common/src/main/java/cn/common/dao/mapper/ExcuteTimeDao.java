package cn.common.dao.mapper;

import cn.common.entity.SysExcuteTimeLog;
import org.springframework.stereotype.Repository;

/**
 * Created by huangYi on 2018/8/22
 **/
@Repository
public interface ExcuteTimeDao {
    void insert(SysExcuteTimeLog sysExcuteTimeLog);
}
