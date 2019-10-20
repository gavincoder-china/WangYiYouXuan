package com.wyyx.provider.mapper;

import com.wyyx.provider.dto.ComUser;
import org.apache.ibatis.annotations.Param;

public interface ComUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ComUser record);

    int insertSelective(ComUser record);

    ComUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ComUser record);

    int updateByPrimaryKey(ComUser record);

    ComUser selectByPhone(@Param("phone") String phone);

    //dkl:查询用户等级
    int selectRoleByUserId(Long userId);

    int selectPointsById(@Param("id") Long id);



}