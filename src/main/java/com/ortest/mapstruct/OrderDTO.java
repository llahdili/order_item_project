package com.ortest.mapstruct;
import com.ortest.common.utils.Statu;
import com.ortest.model.OrderItems;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
@Getter
@Setter
public class OrderDTO {

    private Long id;

    private LocalDateTime dateCreatedOrder;

    private String reference;

    private boolean active;

    private Statu statu;

    private Boolean validation;

    private Set<OrderItems> orderItems;

    private Double totaleOrdersPriceTTC;

    private Double totaleOrdersPriceHorsTax;
}
