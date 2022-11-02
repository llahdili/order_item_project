package com.ortest.mapstruct.mappers;

import com.ortest.mapstruct.OrderDTO;
import com.ortest.model.Orders;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    List<OrderDTO> toOrdersDTO(List<Orders> orders);

    OrderDTO toOrderDTO(Orders orders);

    Orders toOrder(OrderDTO orderDTO);

    Orders toOrderId(OrderDTO orderDTO, Long id);

}