package com.ortest.mapstruct.mappers;

import com.ortest.mapstruct.OrderItemDTO;
import com.ortest.model.OrderItems;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    List<OrderItemDTO> toOrderItemDTOs(List<OrderItems> orderItems);
    @Mapping(source = "productId", target = "products.id")
    @Mapping(source = "orderId", target = "orders.id")
    OrderItems toOrderItem(OrderItemDTO orderItemDTO);
}
