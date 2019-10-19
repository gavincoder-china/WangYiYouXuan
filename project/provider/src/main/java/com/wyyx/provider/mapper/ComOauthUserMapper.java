package com.wyyx.provider.mapper;
import java.util.Date;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import com.wyyx.provider.dto.ComOauthUser;

public interface ComOauthUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ComOauthUser record);

    int insertSelective(ComOauthUser record);

    ComOauthUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ComOauthUser record);

    int updateByPrimaryKey(ComOauthUser record);
    ComOauthUser selectAllByOauthUserId(@Param("oauthUserId")Long oauthUserId);


    int updatephoneAndCreateTimeByOauthUserId(@Param("updatedPhone")String updatedPhone,@Param("updatedCreateTime")Date updatedCreateTime,@Param("oauthUserId")Long oauthUserId);









}