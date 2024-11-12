package springboot.cinemaapi.cinemaapifororders.infrastructure.rest.order;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springboot.cinemaapi.cinemaapifororders.application.dto.order.ProductDto;
import springboot.cinemaapi.cinemaapifororders.domain.enums.ProductType;
import springboot.cinemaapi.cinemaapifororders.application.ports.input.order.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findProductById(id));
    }
    @GetMapping
    public ResponseEntity<Page<ProductDto>> getAllProducts(@RequestParam(required = false) ProductType sectionType,@RequestParam(defaultValue = "0") Integer page,
                                                            @RequestParam(defaultValue = "10") Integer size) {
        Page<ProductDto> products = productService.findProducts(sectionType,page,size);

        return ResponseEntity.ok(products);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto product) {
        return new ResponseEntity<>(productService.addProduct(product), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id,@Valid @RequestBody ProductDto product) {
        return ResponseEntity.ok(productService.updateProduct(id,product));
    }
}
