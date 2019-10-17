package com.wyyx.provider.mapper;
import org.apache.ibatis.annotations.Param;

import com.wyyx.provider.dto.ComProduct;

import java.util.List;

public interface ComProductMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ComProduct record);

    int insertSelective(ComProduct record);

    ComProduct selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ComProduct record);

    int updateByPrimaryKey(ComProduct record);

    //查询首页分类商品
   // List<ComProduct> selectAllByClass(ComProduct comProduct);

   //查询首页商品,
   List<ComProduct> selectAll(@Param("num") int num);




}