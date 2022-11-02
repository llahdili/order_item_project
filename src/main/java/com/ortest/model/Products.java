package com.ortest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Products {
        @Id
        @GeneratedValue(strategy = AUTO)
        private long id;

        private String name;

        private Double unitPriceHorsTax;

        private Double productTVA ;

        private Double productVATRate;

        private Double priceTTC;

        private Integer unitsToStock;


}
