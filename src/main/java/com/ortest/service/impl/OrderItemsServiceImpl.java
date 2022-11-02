package com.ortest.service.impl;

import com.ortest.common.utils.Statu;
import com.ortest.exceptions.ApiRequestException;
import com.ortest.model.OrderItems;
import com.ortest.model.Orders;
import com.ortest.model.Products;
import com.ortest.repository.OrderItemRepository;
import com.ortest.repository.OrderRepo;
import com.ortest.service.OrderItemService;
import com.ortest.service.ProductService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderItemsServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemsRepository;
    private final ProductService productService;

    private final OrderRepo orderRepo;

    @Autowired
    public OrderItemsServiceImpl(OrderItemRepository orderItemsRepository, ProductService productService, OrderRepo orderRepo) {
        this.orderItemsRepository = orderItemsRepository;
        this.productService = productService;
        this.orderRepo = orderRepo;
    }


    @Override
    public List<OrderItems> findAllItems() {
        return orderItemsRepository.findAll();
    }

    @Override
    public Optional<OrderItems> findById(Long id) {
        return Optional.ofNullable(orderItemsRepository.findById(id).orElseThrow(() ->
                new ApiRequestException("Item not Found")));
    }

    @Override
    public OrderItems save(OrderItems orderItem) {

        Orders orders = orderRepo.findById(orderItem.getOrders().getId()).orElseThrow(() ->
        new ApiRequestException("Order not found with id = " + orderItem.getOrders().getId()));
        if (orders.isActive() == true && orders.getValidation() == false && orders.getStatu().equals(Statu.PROCESSING)) {

            Products products = productService.findById(orderItem.getProducts().getId()).orElseThrow(() ->
                    new ApiRequestException("Product not found with id = " + orderItem.getProducts().getId()));

            products.setUnitsToStock(products.getUnitsToStock() + orderItem.getQuantity());
            new ApiRequestException("we added " + orderItem.getQuantity() + " units to our " + products.getName() + " product stock ! ");

            orderItem.setTotalItemPriceHostTax(orderItem.getQuantity() * products.getUnitPriceHorsTax());
            orderItem.setTotalItemPriceTTC(orderItem.getQuantity() * products.getPriceTTC());

            if (orders.getTotaleOrdersPriceTTC() == null)
                orders.setTotaleOrdersPriceTTC(0.0);
            orders.setTotaleOrdersPriceTTC(orders.getTotaleOrdersPriceTTC() + orderItem.getTotalItemPriceTTC());

            if (orders.getTotaleOrdersPriceHorsTax() == null)
                orders.setTotaleOrdersPriceHorsTax(0.0);
            orders.setTotaleOrdersPriceHorsTax(orders.getTotaleOrdersPriceHorsTax() + orderItem.getTotalItemPriceHostTax());
            }else {
             throw new ApiRequestException ("can not add items to this order");
        }
            return orderItemsRepository.save(orderItem);

    }

    @Override
    public void deleteById(Long id){
        Optional<OrderItems> orderItems = this.findById(id);
     //   Optional<Orders> orders = orderRepo.findById(orderItems.get().getOrders().getId());
        orderItems.map(orderItems1 -> {
                    orderItems1.getProducts().setUnitsToStock(orderItems.get().getProducts().getUnitsToStock() - orderItems1.getQuantity());
                    orderItems1.getOrders().setTotaleOrdersPriceHorsTax(orderItems1.getOrders().getTotaleOrdersPriceHorsTax() - orderItems.get().getTotalItemPriceHostTax());
                    orderItems1.getOrders().setTotaleOrdersPriceTTC(orderItems1.getOrders().getTotaleOrdersPriceTTC() - orderItems.get().getTotalItemPriceTTC());
                    return orderItemsRepository.save(orderItems1);
                });
        orderItemsRepository.deleteById(id);
    }

    @Override
    public OrderItems updateItem(OrderItems orderItem, Long id){

         Orders orders = orderRepo.findById(orderItem.getOrders().getId()).orElseThrow(() ->new ApiRequestException("Order not found " ));

        if (orders.isActive() || orders.getValidation().equals(false) || orders.getStatu() == Statu.PROCESSING){
            return orderItemsRepository.findById(id).map(orderItems -> {
                orderItems.setOrders(orderItem.getOrders());
                orderItems.setTotalItemPriceTTC(orderItem.getTotalItemPriceTTC());
                orderItems.setTotalItemPriceHostTax(orderItem.getTotalItemPriceHostTax());
                orderItems.getProducts();
                orderItems.setQuantity(orderItem.getQuantity());
                return orderItemsRepository.save(orderItems);
            }).get();
    }else {throw new ApiRequestException("Can not Update this items to the order ");}
    }

    @Override
    public List<OrderItems> findOrdersTodelete(Long id){
        return orderItemsRepository.findOrdersTodelete(id);
    }

}