package com.wyyx.provider.mapper;

import com.wyyx.provider.dto.SecProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SecProductMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SecProduct record);

    int insertSelective(SecProduct record);

    SecProduct selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SecProduct record);

    int updateByPrimaryKey(SecProduct record);

    List<SecProduct> selectAll(@Param("start") int start, @Param("offset") int offset);

    Long count();

    Long selectInventoryById(@Param("id") Long id);

    SecProduct selectById(@Param("id") Long id);

    int updateInventorybyid( @Param("id") Long id);


}