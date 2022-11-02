package com.ortest.mapstruct;
import lombok.*;
@Getter
@Setter
public class OrderItemDTO {
    private Long id;

    private Integer quantity;

    private Double totalItemPriceHostTax;

    private Double totalItemPriceTTC;

    private Long orderId;

    private Long productId;
}
