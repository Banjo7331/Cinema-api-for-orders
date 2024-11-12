package springboot.cinemaapi.cinemaapifororders.infrastructure.rest.order;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springboot.cinemaapi.cinemaapifororders.application.dto.order.OrderDto;
import springboot.cinemaapi.cinemaapifororders.application.ports.input.order.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')  or hasRole('EMPLOYER')  or hasRole('USER')")
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody @Valid OrderDto order) {

        return new ResponseEntity<>(orderService.addOrder(order), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long id, @Valid @RequestBody OrderDto order) {

        return ResponseEntity.ok(orderService.updateOrder(id,order));
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('EMPLOYER') or hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {

        return ResponseEntity.ok(orderService.findOrderById(id));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('EMPLOYER') or hasRole('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderById(@PathVariable Long id) {

        orderService.deleteOrderById(id);

        return ResponseEntity.ok("Order canceled!");
    }

}
