package springboot.cinemaapi.cinemaapifororders.service.order.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import springboot.cinemaapi.cinemaapifororders.entity.order.Order;
import springboot.cinemaapi.cinemaapifororders.entity.order.Product;
import springboot.cinemaapi.cinemaapifororders.payload.dto.order.OrderDto;
import springboot.cinemaapi.cinemaapifororders.payload.dto.order.ProductDto;
import springboot.cinemaapi.cinemaapifororders.repository.ProductRepository;
import springboot.cinemaapi.cinemaapifororders.service.order.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    private ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ProductDto> getProducts(Product.Type type) {
        List<Product> products;
        if(type == null){
            products = productRepository.findAll();
        }else{
            products = productRepository.findAllProductByType(type);
        }

        return products.stream()
                .map(product ->modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());

    }

    @Override
    public ProductDto addProduct(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);

        return modelMapper.map(productRepository.save(product), ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product product = productRepository.findById(id).orElseThrow(()-> new RuntimeException("Product not found"));

        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setType(productDto.getType());
        product.setCode(productDto.getCode());

        Product updateProduct = productRepository.save(product);

        return modelMapper.map(product, ProductDto.class);
    }

}
