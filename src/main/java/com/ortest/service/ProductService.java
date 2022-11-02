package com.ortest.service;

import com.ortest.model.Products;

import java.util.List;
import java.util.Optional;

public interface ProductService {
   List<Products> findProducts();
   Products saveProduct(Products product);
   void deleteById(Long id);
   Optional<Products> findById(Long id);
   Products updateProduct(Products products, Long id);

   List<Products> getAllProductsPagination(Integer pageNo, Integer pageSize);

}
