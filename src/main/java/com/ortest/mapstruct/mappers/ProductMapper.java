package com.ortest.mapstruct.mappers;

import com.ortest.mapstruct.ProductDTO;
import com.ortest.model.Products;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Products toProduct(ProductDTO productDTO);

    ProductDTO toProductDTO(Products products);


    List<ProductDTO> toProductsDTO(List<Products> products);

    List<Products> toProducts(List<ProductDTO> productDTOS);

}
