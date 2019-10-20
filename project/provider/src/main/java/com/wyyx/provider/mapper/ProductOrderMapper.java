package com.wyyx.provider.mapper;

import com.wyyx.provider.dto.ProductOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductOrder record);

    int insertSelective(ProductOrder record);

    ProductOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductOrder record);

    int updateByPrimaryKey(ProductOrder record);

    //kitty_zhu：查询全部订单
    List<ProductOrder> selectOrderAll(@Param("userId") long userId,
                                      @Param("start") int start,
                                      @Param("offset") int offset
                                     );

    //kitty_zhu：根据订单状态查询 state:订单状态
    List<ProductOrder> selectOrderByClass(@Param("userId") long userId,
                                          @Param("state") int state,
                                          @Param("start") int start,
                                          @Param("offset") int offset);

    //kitty_zhu：根据商品名称模糊查询订单
    List<ProductOrder> selectByGoodsName(@Param("userId") long userId,
                                         @Param("name") String name,
                                         @Param("state") byte state,
                                         @Param("start") int start,
                                         @Param("offset") int offset);

    //kitty_zhu:查询is_del的订单（回收站）
    List<ProductOrder> selectByIsDel(@Param("userId") long userId,
                                     @Param("isDelete") boolean isDelete,
                                     @Param("start") int start,
                                     @Param("offset") int offset);

    //kitty_zhu:查询订单状态
    ProductOrder selectOrderState(@Param("userId") long userId,@Param("id") Long id);

    //kitty_zhu:查询 isdel
    ProductOrder selectById(@Param("userId") long userId,@Param("id") Long id);

    //zmx 逻辑删除订单
    int updateIsDeleteByProductIdAndUserId(@Param("updatedIsDelete")Boolean updatedIsDelete,@Param("productId")Long productId,@Param("userId")Long userId);





}