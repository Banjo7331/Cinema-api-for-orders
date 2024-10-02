package springboot.cinemaapi.cinemaapifororders.service.order.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import springboot.cinemaapi.cinemaapifororders.entity.order.Order;
import springboot.cinemaapi.cinemaapifororders.entity.order.Product;
import springboot.cinemaapi.cinemaapifororders.payload.dto.order.OrderDto;
import springboot.cinemaapi.cinemaapifororders.payload.dto.order.ProductDto;
import springboot.cinemaapi.cinemaapifororders.repository.OrderRepository;
import springboot.cinemaapi.cinemaapifororders.service.order.OrderService;

import java.math.BigDecimal;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    private ModelMapper modelMapper;


    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public OrderDto createOrder(OrderDto orderDto) {


        Order order = modelMapper.map(orderDto,Order.class);

        order.setTotalPrice(calculatePrice(orderDto));

        return modelMapper.map(orderRepository.save(order), OrderDto.class);
    }

    @Override
    public OrderDto updateOrder(Long id, OrderDto orderDto) {
        Order order = orderRepository.findById(id).orElseThrow(()-> new RuntimeException("Order not found"));

        modelMapper.map(orderDto, order);

        Order updatedOrder = orderRepository.save(order);

        return modelMapper.map(updatedOrder, OrderDto.class);
    }


    @Override
    public OrderDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new RuntimeException("Order not found"));

        return modelMapper.map(order,OrderDto.class);
    }

    @Override
    public void deleteOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(()-> new RuntimeException("Order not found"));

        orderRepository.delete(order);
    }

    private BigDecimal calculatePrice(OrderDto order) {
        BigDecimal price = BigDecimal.ZERO;

        for(ProductDto product : order.getOrderItems()){
            price = price.add(product.getPrice());
        }

        return price;
    }


}
