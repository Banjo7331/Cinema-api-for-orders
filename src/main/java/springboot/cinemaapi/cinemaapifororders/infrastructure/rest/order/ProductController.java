package springboot.cinemaapi.cinemaapifororders.infrastructure.rest.order;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springboot.cinemaapi.cinemaapifororders.application.dto.order.ProductRequest;
import springboot.cinemaapi.cinemaapifororders.application.dto.order.ProductResponse;
import springboot.cinemaapi.cinemaapifororders.domain.enums.ProductType;
import springboot.cinemaapi.cinemaapifororders.application.ports.input.order.ProductService;

import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable String id) {
        return ResponseEntity.ok(productService.findProductById(id));
    }
    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(@RequestParam(required = false) ProductType sectionType,@RequestParam(defaultValue = "0") Integer page,
                                                            @RequestParam(defaultValue = "10") Integer size) {
        Page<ProductResponse> products = productService.findProducts(sectionType,page,size);

        return ResponseEntity.ok(products);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest product) {
        return new ResponseEntity<>(productService.addProduct(product), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable String id,@Valid @RequestBody ProductRequest product) {
        return ResponseEntity.ok(productService.updateProduct(id,product));
    }
}
