package com.codecool.ordersystem.service;

import com.codecool.ordersystem.dto.OrderDTO;
import com.codecool.ordersystem.dto.OrderItemDTO;
import com.codecool.ordersystem.entity.Order;
import com.codecool.ordersystem.entity.OrderItem;
import com.codecool.ordersystem.repository.CustomerRepository;
import com.codecool.ordersystem.repository.OrderItemRepositpry;
import com.codecool.ordersystem.repository.OrderRepository;
import com.codecool.ordersystem.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private OrderItemRepositpry orderItemRepository;
    private CustomerRepository customerRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, OrderItemRepositpry orderItemRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
        this.customerRepository = customerRepository;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow();
    }

    public Order saveOrder(OrderDTO orderDTO) {
        List<OrderItem> orders = new ArrayList<>();
        Order newOrder = new Order(null,
                orderDTO.getDate(),
                orderDTO.getShipped(),
                null,
                customerRepository.findById(orderDTO.getSellerId()).orElseThrow(),
                customerRepository.findById(orderDTO.getBuyerId()).orElseThrow(),
                orders
        );
        for (OrderItemDTO o : orderDTO.getOrders()) {
            OrderItem newOrderItem = new OrderItem();
            newOrderItem.setProduct(productRepository.findById(o.getProductId()).orElseThrow());
            newOrderItem.setQuantity(o.getQuantity());
            newOrderItem.setSellingPrice(o.getSellingPrice());
            newOrderItem.setOrder(newOrder);
            orders.add(newOrderItem);
        }
        return orderRepository.save(newOrder);
    }

    public void updateCustomer(Long id, OrderDTO orderDTO) {
        Order order = orderRepository.findById(id).orElseThrow();
        order.setDate(orderDTO.getDate());
        order.setShipped(orderDTO.getShipped());
        order.setShippingDate(orderDTO.getShippingDate());
        order.setSeller(customerRepository.findById(orderDTO.getSellerId()).orElseThrow());
        order.setBuyer(customerRepository.findById(orderDTO.getBuyerId()).orElseThrow());

        for (OrderItemDTO o : orderDTO.getOrders()) {
            OrderItem orderItem = orderItemRepository.findById(o.getId()).orElseThrow();
            orderItem.setProduct(productRepository.findById(o.getProductId()).orElseThrow());
            orderItem.setQuantity(o.getQuantity());
            orderItem.setSellingPrice(o.getSellingPrice());
        }
        orderRepository.save(order);
    }
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

    public List<Order> findBySellerId(Long id) {
        return orderRepository.findBySellerId(id);
    }

    public List<Order> findByBuyerId(Long id) {
        return orderRepository.findByBuyerId(id);
    }

    public List<Order> findOrdersToShip() {
        return orderRepository.findOrdersToShip();
    }

    public List<Order> findByProductId(Long id) {
        return orderRepository.findByProductId(id);
    }
}
