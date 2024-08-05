package springboot.cinemaapi.cinemaapifororders.controller.order;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.cinemaapi.cinemaapifororders.entity.order.Order;
import springboot.cinemaapi.cinemaapifororders.entity.order.Product;
import springboot.cinemaapi.cinemaapifororders.payload.dto.OrderDto;
import springboot.cinemaapi.cinemaapifororders.service.order.OrderService;
import springboot.cinemaapi.cinemaapifororders.service.order.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private OrderService orderService;
    private ProductService productService;

    public OrderController(OrderService orderService, ProductService productService) {
        this.orderService = orderService;
        this.productService = productService;
    }


    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody @Valid OrderDto order) {

        return new ResponseEntity<>(orderService.createOrder(order), HttpStatus.CREATED);
    }


    @GetMapping("/products")
    public ResponseEntity<List<Product>> findAllProducts(@RequestParam(required = false) Product.Type sectionType) {
        List<Product> products = productService.getProducts(sectionType);

        return ResponseEntity.ok(products);
    }

}
