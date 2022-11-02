package com.ortest.service.impl;

import com.ortest.model.Products;
import com.ortest.exceptions.ApiRequestException;
import com.ortest.repository.OrderItemRepository;
import com.ortest.repository.ProductRepo;
import com.ortest.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;

    private final OrderItemRepository orderItemRepository;

    @Autowired
    public ProductServiceImpl(ProductRepo productRepo, OrderItemRepository orderItemRepository) {
        this.productRepo = productRepo;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public List<Products> findProducts() {
        return productRepo.findAll();
    }

    @Override
    public Products saveProduct(Products product) {
        product.setProductTVA((product.getProductVATRate() / 100) * product.getUnitPriceHorsTax());
        product.setPriceTTC(product.getProductTVA() + product.getUnitPriceHorsTax());
        return productRepo.save(product);
    }



    @Override
    public Optional<Products> findById(Long id) {
        return Optional.ofNullable(productRepo.findById(id)
                .orElseThrow(() -> new ApiRequestException("Product not found")));
    }

    @Override
    public Products updateProduct(Products products, Long id){
        return productRepo.findById(id)
                .map(prod -> {
                    prod.setName(products.getName());
                    prod.setUnitPriceHorsTax(products.getUnitPriceHorsTax());
                    prod.setProductVATRate(products.getProductVATRate());
                    prod.setUnitsToStock(products.getUnitsToStock());
                    prod.setProductTVA((products.getProductVATRate()/100)* products.getUnitPriceHorsTax());
                    return productRepo.save(prod);
                }).get();
    }

    @Override
    public List<Products> getAllProductsPagination(Integer p, Integer z)
    {
        Pageable paging = PageRequest.of(p, z);

        Page<Products> pagedResult = productRepo.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public void deleteById(Long id) {
        Optional<Products> products = this.findById(id);
        Long counted = orderItemRepository.findRelatedItems(products.get().getId());
            if (counted >= 1) {
                throw new ApiRequestException("Can not delete this Product");
            } else{
                productRepo.deleteById(id);
                log.info("Product has been deleted");
            }
        }
    }

