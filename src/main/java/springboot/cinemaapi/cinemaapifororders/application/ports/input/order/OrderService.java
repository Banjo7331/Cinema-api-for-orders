package springboot.cinemaapi.cinemaapifororders.application.ports.input.order;

import springboot.cinemaapi.cinemaapifororders.application.dto.order.OrderRequest;
import springboot.cinemaapi.cinemaapifororders.application.dto.order.OrderResponse;

import java.util.UUID;

public interface OrderService {
    OrderResponse addOrder(OrderRequest order);
    OrderResponse updateOrder(String id, OrderRequest orderDto);
    OrderResponse findOrderById(String orderId);
    void deleteOrderById(String id);
}
