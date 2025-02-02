package springboot.cinemaapi.cinemaapifororders.controller.order;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springboot.cinemaapi.cinemaapifororders.entity.order.Order;
import springboot.cinemaapi.cinemaapifororders.entity.order.Product;
import springboot.cinemaapi.cinemaapifororders.payload.dto.order.OrderDto;
import springboot.cinemaapi.cinemaapifororders.payload.dto.order.ProductDto;
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
    public ResponseEntity<OrderDto> createOrder(@RequestBody @Valid OrderDto order) {

        return new ResponseEntity<>(orderService.createOrder(order), HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long id, @RequestBody @Valid OrderDto order) {

        return ResponseEntity.ok(orderService.updateOrder(id,order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {

        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderById(@PathVariable Long id) {

        orderService.deleteOrderById(id);

        return ResponseEntity.ok("Order canceled!");
    }


    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> findAllProducts(@RequestParam(required = false) Product.Type sectionType) {
        List<ProductDto> products = productService.getProducts(sectionType);

        return ResponseEntity.ok(products);
    }

    @PreAuthorize("hasRole({'ADMIN','MANAGER'})")
    @PostMapping("/products")
    public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid ProductDto product) {
        return new ResponseEntity<>(productService.addProduct(product), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole({'ADMIN','MANAGER'})")
    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductDto product) {
        return ResponseEntity.ok(productService.updateProduct(id,product));
    }

}
