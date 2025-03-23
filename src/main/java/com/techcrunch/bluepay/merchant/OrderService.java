package com.techcrunch.bluepay.merchant;

import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    public OrderDTO get(Long id) {
        return orderMapper.toDto(orderRepository.findById(id).orElseThrow());
    }

    public OrderDTO create(OrderDTO orderDTO) {
        return orderMapper.toDto(orderRepository.save(orderMapper.toEntity(orderDTO)));
    }

    public OrderDTO update(Long id, OrderDTO orderDTO) {
        Order order = orderRepository.findById(id).orElseThrow();
        orderMapper.partialUpdate(orderDTO, order);
        return orderMapper.toDto(orderRepository.save(order));
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    public OrderDTO getByInvoiceId(Long invoiceId) {
        return orderMapper.toDto(orderRepository.findByInvoiceId(invoiceId).orElseThrow());
    }
}
