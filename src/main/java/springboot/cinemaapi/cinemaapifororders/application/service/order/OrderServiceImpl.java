package springboot.cinemaapi.cinemaapifororders.application.service.order;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import springboot.cinemaapi.cinemaapifororders.domain.model.order.Order;
import springboot.cinemaapi.cinemaapifororders.application.dto.order.OrderDto;
import springboot.cinemaapi.cinemaapifororders.application.dto.order.ProductDto;
import springboot.cinemaapi.cinemaapifororders.domain.model.order.Product;
import springboot.cinemaapi.cinemaapifororders.infrastructure.persistence.repository.OrderRepository;
import springboot.cinemaapi.cinemaapifororders.application.ports.input.order.OrderService;
import springboot.cinemaapi.cinemaapifororders.infrastructure.persistence.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    private ProductRepository productRepository;

    private ModelMapper modelMapper;


    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public OrderDto addOrder(OrderDto orderDto) {
        Order order = modelMapper.map(orderDto, Order.class);

        order.setTotalPrice(calculatePrice(orderDto));

        List<Product> products = orderDto.getOrderedItemsIds().stream()
                .map(productId -> productRepository.findById(productId)
                        .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId)))
                .collect(Collectors.toList());

        order.setOrderItems(products);

        Order savedOrder = orderRepository.save(order);

        // Convert the saved Order entity back to a DTO and return it
        return modelMapper.map(savedOrder, OrderDto.class);
    }

    @Override
    public OrderDto updateOrder(Long id, OrderDto orderDto) {
        Order order = orderRepository.findById(id).orElseThrow(()-> new RuntimeException("Order not found"));

        modelMapper.map(orderDto, order);

        Order updatedOrder = orderRepository.save(order);

        return modelMapper.map(updatedOrder, OrderDto.class);
    }


    @Override
    public OrderDto findOrderById(Long orderId) {
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

        List<Product> products = order.getOrderedItemsIds().stream()
                .map(productId -> productRepository.findById(productId)
                        .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId)))
                .collect(Collectors.toList());

        for(Product product : products){
            price = price.add(product.getPrice());
        }

        return price;
    }


}
