package com.wyyx.provider.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import com.wyyx.provider.dto.ComUser;

public interface ComUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ComUser record);

    int insertSelective(ComUser record);

    ComUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ComUser record);

    int updateByPrimaryKey(ComUser record);

    ComUser selectByPhone(@Param("phone")String phone);

}