package springboot.cinemaapi.cinemaapifororders.service.order.impl;

import org.aspectj.weaver.ast.Or;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import springboot.cinemaapi.cinemaapifororders.entity.order.Order;
import springboot.cinemaapi.cinemaapifororders.entity.order.Product;
import springboot.cinemaapi.cinemaapifororders.payload.dto.OrderDto;
import springboot.cinemaapi.cinemaapifororders.repository.OrderRepository;
import springboot.cinemaapi.cinemaapifororders.repository.ProductRepository;
import springboot.cinemaapi.cinemaapifororders.service.order.OrderService;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    private ModelMapper modelMapper;


    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public Order createOrder(OrderDto orderDto) {


        Order order = modelMapper.map(orderDto,Order.class);

        order.setTotalPrice(calculatePrice(order));

        return orderRepository.save(order);
    }

    private BigDecimal calculatePrice(Order order) {
        BigDecimal price = BigDecimal.ZERO;

        for(Product product : order.getOrderItems()){
            price = price.add(product.getPrice());
        }

        return price;
    }
}
