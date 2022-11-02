package com.ortest.mapstruct;

import lombok.*;
@Getter
@Setter
public class ProductDTO {

    private long id;

    private String name;

    private Double unitPriceHorsTax;

    private Double productTVA;

    private Double productVATRate;

    private Double priceTTC;

    private Integer unitsToStock;

}
