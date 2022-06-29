package com.codecool.ordersystem.service;

import com.codecool.ordersystem.dto.OrderDTO;
import com.codecool.ordersystem.dto.OrderItemDTO;
import com.codecool.ordersystem.dto.OrderItemResponseDTO;
import com.codecool.ordersystem.dto.OrderResponseDTO;
import com.codecool.ordersystem.entity.Order;
import com.codecool.ordersystem.entity.OrderItem;
import com.codecool.ordersystem.repository.CustomerRepository;
import com.codecool.ordersystem.repository.OrderItemRepositpry;
import com.codecool.ordersystem.repository.OrderRepository;
import com.codecool.ordersystem.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private OrderItemRepositpry orderItemRepository;
    private CustomerRepository customerRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository,
                        OrderItemRepositpry orderItemRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
        this.customerRepository = customerRepository;
    }

    public List<OrderResponseDTO> findAll() {
        List<OrderResponseDTO> orderResponseDTOS = new ArrayList<>();
        for (Order o : orderRepository.findAll()) {
            orderResponseDTOS.add(getOrderResponseDTO(o));
        }
        return orderResponseDTOS;
    }

    public Optional<OrderResponseDTO> findById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent())
            return Optional.of(getOrderResponseDTO(orderRepository.findById(id).orElse(null)));
        else return Optional.empty();
    }

    private OrderResponseDTO getOrderResponseDTO(Order o) {
        OrderResponseDTO orderResponse = new OrderResponseDTO();
        setOrderResponse(o, orderResponse);
        List<OrderItemResponseDTO> orderItemResponseDTOS = new ArrayList<>();
        for (OrderItem oi : o.getOrders()) {
            orderItemResponseDTOS.add(setOrderItemResponseDTO(oi));
        }
        orderResponse.setOrders(orderItemResponseDTOS);
        return orderResponse;
    }

    private void setOrderResponse(Order o, OrderResponseDTO orderResponse) {
        orderResponse.setId(o.getId());
        orderResponse.setDate(o.getDate());
        orderResponse.setShipped(o.getShipped());
        orderResponse.setShippingDate(o.getShippingDate());
        orderResponse.setSeller(o.getSeller());
        orderResponse.setBuyer(o.getBuyer());
        orderResponse.setOrders(new ArrayList<>());
    }

    private OrderItemResponseDTO setOrderItemResponseDTO(OrderItem oi) {
        OrderItemResponseDTO orderItemResponse = new OrderItemResponseDTO();
        orderItemResponse.setId(oi.getId());
        orderItemResponse.setQuantity(oi.getQuantity());
        orderItemResponse.setSellingPrice(oi.getSellingPrice());
        orderItemResponse.setProduct(oi.getProduct());
        return orderItemResponse;
    }

    public OrderResponseDTO saveOrder(OrderDTO orderDTO) {
        List<OrderItem> orders = new ArrayList<>();
        Order newOrder = new Order(null,
                orderDTO.getDate(),
                false,
                null,
                customerRepository.findById(orderDTO.getSellerId()).orElse(null),
                customerRepository.findById(orderDTO.getBuyerId()).orElse(null),
                orders
        );
        for (OrderItemDTO o : orderDTO.getOrders()) {
            OrderItem newOrderItem = setOrderItem(newOrder, o);
            orders.add(newOrderItem);
        }
        return getOrderResponseDTO(orderRepository.save(newOrder));
    }

    public Optional<OrderResponseDTO> updateOrder(Long id, OrderDTO orderDTO) {
        Optional<Order> orderOpt = orderRepository.findById(id);
        List<OrderItem> orderItemsOld = new ArrayList<>();
        if (orderOpt.isPresent()) {
            try {
                Order order = orderOpt.get();
                setOrder(orderDTO, order);
                orderItemsOld = order.getOrders();
                List<OrderItem> orders = new ArrayList<>();
                for (OrderItemDTO o : orderDTO.getOrders()) {
                    OrderItem orderItem = setOrderItem(order, o);
                    orders.add(orderItem);
                    orderItemRepository.save(orderItem);
                }
                order.setOrders(orders);
                deleteOrederItems(orderItemsOld);
                return Optional.of(getOrderResponseDTO(orderRepository.save(order)));
            } catch (Exception e) {
                return Optional.empty();
            }
        } else return Optional.empty();
    }

    private void deleteOrederItems(List<OrderItem> orderItemsOld) {
        for (OrderItem oi : orderItemsOld) {
            orderItemRepository.deleteById(oi.getId());
        }
    }

    private void setOrder(OrderDTO orderDTO, Order order) {
        order.setDate(orderDTO.getDate());
        order.setShipped(orderDTO.getShipped());
        order.setShippingDate(orderDTO.getShippingDate());
        order.setSeller(customerRepository.findById(orderDTO.getSellerId()).orElse(null));
        order.setBuyer(customerRepository.findById(orderDTO.getBuyerId()).orElse(null));
        order.getOrders().forEach(o -> o.setOrder(null));
    }

    private OrderItem setOrderItem(Order order, OrderItemDTO o) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(productRepository.findById(o.getProductId()).orElse(null));
        orderItem.setQuantity(o.getQuantity());
        orderItem.setSellingPrice(o.getSellingPrice());
        orderItem.setOrder(order);
        return orderItem;
    }

    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

    public List<OrderResponseDTO> findBySellerId(Long id) {
        List<OrderResponseDTO> orderResponseDTOS = new ArrayList<>();
        for (Order o : orderRepository.findBySellerId(id)) {
            orderResponseDTOS.add(getOrderResponseDTO(o));
        }
        return orderResponseDTOS;
    }

    public List<OrderResponseDTO> findByBuyerId(Long id) {
        List<OrderResponseDTO> orderResponseDTOS = new ArrayList<>();
        for (Order o : orderRepository.findByBuyerId(id)) {
            orderResponseDTOS.add(getOrderResponseDTO(o));
        }
        return orderResponseDTOS;
    }

    public List<OrderResponseDTO> findOrdersToShip() {
        List<OrderResponseDTO> orderResponseDTOS = new ArrayList<>();
        for (Order o : orderRepository.findOrdersToShip()) {
            orderResponseDTOS.add(getOrderResponseDTO(o));
        }
        return orderResponseDTOS;
    }

    public List<OrderResponseDTO> findByProductId(Long id) {
        List<OrderResponseDTO> orderResponseDTOS = new ArrayList<>();
        for (Order o : orderRepository.findByProductId(id)) {
            orderResponseDTOS.add(getOrderResponseDTO(o));
        }
        return orderResponseDTOS;
    }

    public Optional<OrderResponseDTO> shippingOrder(Long id) {
        Optional<Order> orderOpt = orderRepository.findById(id);
        if (orderOpt.isPresent()) {
            orderOpt.get().setShipped(true);
            orderOpt.get().setShippingDate(LocalDate.now());
            return Optional.of(getOrderResponseDTO(orderRepository.save(orderOpt.get())));
        } else return Optional.empty();
    }
}
