package com.wyyx.provider.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import com.wyyx.provider.dto.OrderInfo;

public interface OrderInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderInfo record);

    int insertSelective(OrderInfo record);

    OrderInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);

    List<OrderInfo> selectByOrderId(@Param("orderId")Long orderId);


}