package com.ortest.controller;

import com.ortest.mapstruct.ProductDTO;
import com.ortest.mapstruct.mappers.ProductMapper;
import com.ortest.model.Products;
import com.ortest.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@Slf4j
public class ProductController {
  private final ProductService productService;
  private final ProductMapper productMapper;
  @Autowired
  public ProductController(ProductService productService, ProductMapper productMapper) {
    this.productService = productService;
    this.productMapper = productMapper;
  }

  @GetMapping("/all")
  public List<ProductDTO> findAll() {
    return productMapper.toProductsDTO(productService.findProducts());
  }
  @GetMapping("/product/{id}")
  public ProductDTO findById(@PathVariable Long id) {
    return productMapper.toProductDTO(productService.findById(id).get());
  }

  @PostMapping("/add")
  public ProductDTO create(@RequestBody ProductDTO productDTO) {
    Products products = productService.saveProduct(productMapper.toProduct(productDTO));
    return productMapper.toProductDTO(products);
  }

    @PutMapping("/update/{id}")
    public ProductDTO updateProduct(@RequestBody ProductDTO productDTO, @PathVariable Long id) {
      Products products = productService.updateProduct(productMapper.toProduct(productDTO), id);
      return productMapper.toProductDTO(products);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
    productService.deleteById(id);
    return new ResponseEntity<>("product deleted" ,HttpStatus.OK);
  }

  @GetMapping("/pagi")
  public List<ProductDTO> getAllProductsPagination(
          @RequestParam(defaultValue = "0") Integer p, @RequestParam(defaultValue = "10") Integer z)
  {
    List<Products> listOfProductRequired = productService.getAllProductsPagination(p, z);
    return productMapper.toProductsDTO(listOfProductRequired);
  }

  @GetMapping("/pagi/{p}/{z}")
  public List<ProductDTO> getAllProductsPagination2(
          @PathVariable Integer p, @PathVariable Integer z)
  {
    List<Products> listOfProductRequired = productService.getAllProductsPagination(p, z);
    return productMapper.toProductsDTO(listOfProductRequired);
  }
}


