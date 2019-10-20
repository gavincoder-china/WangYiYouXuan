package com.wyyx.provider.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import com.wyyx.provider.dto.ProductComment;

public interface ProductCommentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductComment record);

    int insertSelective(ProductComment record);

    ProductComment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductComment record);

    int updateByPrimaryKey(ProductComment record);
    List<ProductComment> selectByProductId(@Param("productId")Long productId);


}