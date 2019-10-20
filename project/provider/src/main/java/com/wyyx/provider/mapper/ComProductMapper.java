package com.wyyx.provider.mapper;

import com.wyyx.provider.dto.ComProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ComProductMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ComProduct record);

    int insertSelective(ComProduct record);

    ComProduct selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ComProduct record);

    int updateByPrimaryKey(ComProduct record);

    //zy：查询首页商品
    List<ComProduct> selectAll(@Param("num") int num);

    //zy：根据商品类型查询商品
    List<ComProduct> selectByClass(@Param("p_type") int p_type,
                                   @Param("start") int start, @Param("offset") int offset);

    long selectByClassCount(@Param("p_type") int p_type);

    //zy：模糊查询商品,带分页
    List<ComProduct> selectByName(@Param("name") String name,
                                  @Param("start") int start,
                                  @Param("offset") int offset);

    long selectByNameCount(@Param("name") String name);

    //kitty_zhu:默认搜索框最火爆商品
    List<ComProduct> selectByHot();

    //dkl:通过商品id查询商品信息
    ComProduct selectById(Long id);

    //减库存+加销量
    int updateInventoryAndSalesbyid(@Param("id") Long id);





}