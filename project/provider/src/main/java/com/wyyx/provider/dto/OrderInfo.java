package com.wyyx.provider.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class OrderInfo implements Serializable {
    private Long id;

    private Long orderId;

    private Long count;

    private Long productId;

    private static final long serialVersionUID = 1L;
}