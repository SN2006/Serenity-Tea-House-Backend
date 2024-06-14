package com.example.backend.validation;

import com.example.backend.entity.Product;
import com.example.backend.sercives.ProductService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductNameValidator implements ConstraintValidator<ProductName, String> {

    private final ProductService productService;

    @Autowired
    public ProductNameValidator(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext ctx) {
        if (value == null){
            return false;
        }
        List<Product> products = productService.findByName(value);
        return products.isEmpty();
    }
}
