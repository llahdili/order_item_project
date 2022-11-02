package com.ortest.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Session;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table
public class OrderItems {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Integer quantity;

    private Double totalItemPriceHostTax;

    private Double totalItemPriceTTC;
    @JsonIgnore
    @ManyToOne
    private Orders orders;

    @ManyToOne
    private Products products;


}
