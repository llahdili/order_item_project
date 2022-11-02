package com.ortest.controller;

import com.ortest.mapstruct.OrderItemDTO;
import com.ortest.mapstruct.mappers.OrderItemMapper;
import com.ortest.model.OrderItems;
import com.ortest.service.OrderItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
public class OrderItemController {
    private final OrderItemMapper orderItemMapper;

    private final OrderItemService orderItemService;


    @Autowired
    public OrderItemController(OrderItemMapper orderItemMapper, OrderItemService orderItemService) {
        this.orderItemMapper = orderItemMapper;
        this.orderItemService = orderItemService;
    }



    @GetMapping("/all")
    public ResponseEntity<List<OrderItemDTO>> findAll() {
        return ResponseEntity.ok(orderItemMapper.toOrderItemDTOs(orderItemService.findAllItems()));
    }

    @GetMapping("/item/{id}")
    public Optional<OrderItems> getItemById(@PathVariable Long id) {
        return orderItemService.findById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<OrderItemDTO> saveOrderItem(@RequestBody OrderItemDTO orderItemDTO)   {
        orderItemService.save(orderItemMapper.toOrderItem(orderItemDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(orderItemDTO);
    }

    @PutMapping("/update/{id}")
    public OrderItems UpdateOrderItem(@RequestBody OrderItemDTO orderItemDTO, @PathVariable Long id)   {
        return orderItemService.updateItem(orderItemMapper.toOrderItem(orderItemDTO), id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        orderItemService.deleteById(id);
        return new ResponseEntity<>("Item has been deleted", HttpStatus.ACCEPTED);
    }




}
