package com.ortest.controller;

import com.ortest.model.Orders;
import com.ortest.mapstruct.OrderDTO;
import com.ortest.mapstruct.mappers.OrderMapper;
import com.ortest.service.OrderService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    private final OrderMapper orderMapper;

    @Autowired
    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @PostMapping("/add")
    public ResponseEntity<OrderDTO> create(@RequestBody OrderDTO orderDTO){
        orderService.saveOrder(orderMapper.toOrder(orderDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderDTO>> findAll(){
        return new ResponseEntity<>(orderMapper.toOrdersDTO(orderService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/order/{id}")
    public Optional<OrderDTO> getOrderById(@PathVariable Long id){
        return Optional.ofNullable(orderMapper.toOrderDTO(orderService.findById(id).get()));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> desable(@PathVariable Long id){
        orderService.enable(id);
        return new ResponseEntity<>("Order has been deleted", HttpStatus.ACCEPTED);
    }

    @PutMapping("/update/{id}")
    public OrderDTO updateOrder(@RequestBody OrderDTO orderDTO, @PathVariable Long id){
        Orders orders = orderService.updateOrder(orderMapper.toOrder(orderDTO), id);
        return orderMapper.toOrderDTO(orders);
    }

}