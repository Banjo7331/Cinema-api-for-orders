package springboot.cinemaapi.cinemaapifororders.application.service.order;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import springboot.cinemaapi.cinemaapifororders.application.dto.order.ProductRequest;
import springboot.cinemaapi.cinemaapifororders.application.dto.order.ProductResponse;
import springboot.cinemaapi.cinemaapifororders.domain.model.order.Product;
import springboot.cinemaapi.cinemaapifororders.domain.enums.ProductType;
import springboot.cinemaapi.cinemaapifororders.infrastructure.persistence.repository.ProductRepository;
import springboot.cinemaapi.cinemaapifororders.application.ports.input.order.ProductService;

import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    private ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductResponse findProductById(String id) {
        return modelMapper.map(productRepository.findById(id).orElseThrow(()-> new RuntimeException("Product not found")), ProductResponse.class);
    }

    @Override
    public Page<ProductResponse> findProducts(ProductType type, Integer page, Integer size) {

        Page<Product> products;

        Pageable pageable = PageRequest.of(page, size, Sort.by("productType"));
        if(type == null){
            products = productRepository.findAll(pageable);
        }else{
            products = productRepository.findAllProductByProductType(type,pageable);
        }

        return products.map(product ->modelMapper.map(product, ProductResponse.class));

    }

    @Transactional
    @Override
    public ProductResponse addProduct(ProductRequest productDto) {
        Product product = modelMapper.map(productDto, Product.class);

        return modelMapper.map(productRepository.save(product), ProductResponse.class);
    }

    @Transactional
    @Override
    public ProductResponse updateProduct(String id, ProductRequest productDto) {
        Product product = productRepository.findById(id).orElseThrow(()-> new RuntimeException("Product not found"));

        modelMapper.map(productDto, product);

        Product updateProduct = productRepository.save(product);


        return modelMapper.map(updateProduct, ProductResponse.class);
    }

}
