package com.ortest.model;
import com.ortest.common.utils.Statu;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

import static javax.persistence.GenerationType.AUTO;
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Orders {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    private LocalDateTime dateCreatedOrder;

    private String reference;

    private boolean active;

    private Boolean validation;

    @Enumerated(EnumType.STRING)
    private Statu statu;

    @OneToMany(mappedBy = "orders")
    private Set<OrderItems> orderItems;


    private Double totaleOrdersPriceTTC;


    private Double totaleOrdersPriceHorsTax;


}
