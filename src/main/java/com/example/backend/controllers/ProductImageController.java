package com.example.backend.controllers;

import com.example.backend.dto.productDtos.CreateProductImagesResponseDto;
import com.example.backend.entity.ProductImage;
import com.example.backend.sercives.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/products/images")
@CrossOrigin(origins = {"http://localhost:3000/"})
public class ProductImageController {

    private final ProductImageService productImageService;

    @Autowired
    public ProductImageController(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductImage(@PathVariable Long id) {
        ProductImage productImage = productImageService.findProductImageById(id);
        if (productImage != null) {
            try{
                return ResponseEntity.ok()
                        .header("filename", productImage.getPath())
                        .contentType(MediaType.valueOf(productImage.getContentType()))
                        .contentLength(productImage.getSize())
                        .body(new InputStreamResource(new FileInputStream(productImage.getPath())));
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CreateProductImagesResponseDto> createProductImages(
            @RequestParam("file1") MultipartFile file1,
            @RequestParam("file2") MultipartFile file2,
            @RequestParam("file3") MultipartFile file3,
            @RequestParam("productId") Long productId
    ) {
        boolean isMainPicture = true;
        CreateProductImagesResponseDto dto = new CreateProductImagesResponseDto(new ArrayList<>());

        if (file1 != null){
            ProductImage productImage = productImageService.create(file1, productId, true);
            dto.getImageIds().add(productImage.getId());
            isMainPicture = false;
        }
        if (file2 != null){
            ProductImage productImage = productImageService.create(file2, productId, isMainPicture);
            dto.getImageIds().add(productImage.getId());
            isMainPicture = false;
        }
        if (file3 != null){
            ProductImage productImage = productImageService.create(file3, productId, isMainPicture);
            dto.getImageIds().add(productImage.getId());
            isMainPicture = false;
        }

        if (isMainPicture){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(dto);
    }

}
