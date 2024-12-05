package springboot.cinemaapi.cinemaapifororders.application.service.order;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import springboot.cinemaapi.cinemaapifororders.application.dto.order.OrderRequest;
import springboot.cinemaapi.cinemaapifororders.application.dto.order.OrderResponse;
import springboot.cinemaapi.cinemaapifororders.domain.model.order.Order;
import springboot.cinemaapi.cinemaapifororders.domain.model.order.Product;
import springboot.cinemaapi.cinemaapifororders.infrastructure.persistence.repository.OrderRepository;
import springboot.cinemaapi.cinemaapifororders.application.ports.input.order.OrderService;
import springboot.cinemaapi.cinemaapifororders.infrastructure.persistence.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
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
    public OrderResponse addOrder(OrderRequest orderDto) {
        Order order = modelMapper.map(orderDto, Order.class);

        order.setTotalPrice(calculatePrice(orderDto));

        List<Product> products = orderDto.getProductsIds().stream()
                .map(productId -> productRepository.findById(productId)
                        .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId)))
                .collect(Collectors.toList());

        order.setOrderItems(products);

        Order savedOrder = orderRepository.save(order);

        // Convert the saved Order entity back to a DTO and return it
        return modelMapper.map(savedOrder, OrderResponse.class);
    }

    @Override
    public OrderResponse updateOrder(String id, OrderRequest orderDto) {
        Order order = orderRepository.findById(id).orElseThrow(()-> new RuntimeException("Order not found"));

        modelMapper.map(orderDto, order);

        Order updatedOrder = orderRepository.save(order);

        return modelMapper.map(updatedOrder, OrderResponse.class);
    }


    @Override
    public OrderResponse findOrderById(String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new RuntimeException("Order not found"));

        return modelMapper.map(order,OrderResponse.class);
    }

    @Override
    public void deleteOrderById(String id) {
        Order order = orderRepository.findById(id).orElseThrow(()-> new RuntimeException("Order not found"));

        orderRepository.delete(order);
    }

    private BigDecimal calculatePrice(OrderRequest order) {
        BigDecimal price = BigDecimal.ZERO;

        List<Product> products = order.getProductsIds().stream()
                .map(productId -> productRepository.findById(productId)
                        .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId)))
                .collect(Collectors.toList());

        for(Product product : products){
            price = price.add(product.getPrice());
        }

        return price;
    }


}
