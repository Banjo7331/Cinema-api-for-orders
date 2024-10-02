package springboot.cinemaapi.cinemaapifororders.service.order.impl;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import springboot.cinemaapi.cinemaapifororders.entity.order.Product;
import springboot.cinemaapi.cinemaapifororders.payload.dto.order.ProductDto;
import springboot.cinemaapi.cinemaapifororders.payload.enums.ProductType;
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
    public ProductDto findProductById(Long id) {
        return modelMapper.map(productRepository.findById(id).orElseThrow(()-> new RuntimeException("Product not found")), ProductDto.class);
    }

    @Override
    public List<ProductDto> getProducts(ProductType type) {
        List<Product> products;
        if(type == null){
            products = productRepository.findAll();
        }else{
            products = productRepository.findAllProductByProductType(type);
        }

        return products.stream()
                .map(product ->modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());

    }

    @Transactional
    @Override
    public ProductDto addProduct(ProductDto productDto) {
        System.out.println(productDto.toString());
        Product product = modelMapper.map(productDto, Product.class);

        return modelMapper.map(productRepository.save(product), ProductDto.class);
    }

    @Transactional
    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        System.out.println(productDto);
        Product product = productRepository.findById(id).orElseThrow(()-> new RuntimeException("Product not found"));

        modelMapper.map(productDto, product);

        Product updateProduct = productRepository.save(product);


        return modelMapper.map(updateProduct, ProductDto.class);
    }

}
