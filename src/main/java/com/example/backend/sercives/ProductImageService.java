package com.example.backend.sercives;

import com.example.backend.entity.Product;
import com.example.backend.entity.ProductImage;
import com.example.backend.exceptions.CreateProductImageException;
import com.example.backend.repositories.ProductImageRepository;
import com.example.backend.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class ProductImageService {

    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;
    private final Path root = Paths.get("src/main/resources/static/product_images");

    @Autowired
    public ProductImageService(ProductImageRepository productImageRepository, ProductRepository productRepository){
        this.productImageRepository = productImageRepository;
        this.productRepository = productRepository;
        try{
            if (!Files.exists(root)) {
                Files.createDirectory(root);
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public ProductImage findProductImageById(Long id){
        return productImageRepository.findById(id).orElse(null);
    }

    public ProductImage create(MultipartFile file, Long productId, boolean isPreviewImage) {
        ProductImage productImage = new ProductImage();

        try {
            if (!Files.exists(root.resolve(productId + "/"))){
                Files.createDirectory(root.resolve(productId + "/"));
            }

            String fileName = "image" + (long)(Math.random() * 1_000_000_000) +
                    file.getOriginalFilename().replaceAll(" ", "_");
            Files.copy(file.getInputStream(), root
                    .resolve(productId + "/")
                    .resolve(fileName));

            productImage.setPath(root
                    .resolve(productId + "/")
                    .resolve(fileName).toString());
            productImage.setSize(file.getSize());
            productImage.setPreviewImage(isPreviewImage);
            productImage.setContentType(file.getContentType());

            Optional<Product> productOptional = productRepository.findById(productId);

            if (productOptional.isPresent()){
                Product product = productOptional.get();
                productImage.setProduct(product);

                ProductImage productImageFromDB = productImageRepository.save(productImage);

                if (isPreviewImage){
                    product.setMainPictureId(productImageFromDB.getId());
                    productRepository.save(product);
                }
                return productImageFromDB;
            }
            throw new CreateProductImageException("There is no such product with id " + productId);
        }catch (IOException e){
            System.out.println(e.getMessage());
            throw new CreateProductImageException(e.getMessage());
        }
    }
}
