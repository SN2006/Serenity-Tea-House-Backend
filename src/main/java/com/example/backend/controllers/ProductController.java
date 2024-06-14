package com.example.backend.controllers;

import com.example.backend.dto.productDtos.CreateProductRequestDto;
import com.example.backend.dto.productDtos.CreateProductResponseDto;
import com.example.backend.dto.productDtos.ProductDto;
import com.example.backend.entity.Product;
import com.example.backend.sercives.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {"http://localhost:3000/"})
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<Product> products = productService.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        products.forEach(product -> productDtos.add(productToDto(product)));
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/type")
    public ResponseEntity<List<ProductDto>> getAllProductsByType(
            @RequestParam("filter") String filter
    ) {
        List<Product> products = productService.findByType(filter);
        List<ProductDto> productDtos = new ArrayList<>();
        products.forEach(product -> productDtos.add(productToDto(product)));
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") Long productId) {
        Optional<Product> productOptional = productService.findById(productId);
        return productOptional.map(product -> ResponseEntity.ok(productToDto(product))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CreateProductResponseDto> createProduct(@RequestBody @Validated CreateProductRequestDto createProductRequestDto) {
        Product productFromDb = productService.create(
                dtoToProduct(
                        createProductRequestDto
                )
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new CreateProductResponseDto(productFromDb.getId())
        );
    }

    private Product dtoToProduct(CreateProductRequestDto createProductRequestDto) {
        Product product = new Product();
        product.setName(createProductRequestDto.getName());
        product.setDescription(createProductRequestDto.getDescription());
        product.setPrice(createProductRequestDto.getPrice());
        product.setType(createProductRequestDto.getType());
        product.setMainPictureId(null);
        return product;
    }

    private ProductDto productToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setType(product.getType());
        productDto.setPrice(product.getPrice());
        productDto.setMainPictureId(product.getMainPictureId());

        product.getImages().forEach(image -> productDto.addImagesId(image.getId()));

        return productDto;
    }
}
