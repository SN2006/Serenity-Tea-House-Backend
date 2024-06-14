package com.example.backend.dto.productDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long id;
    private String name;
    private String description;
    private String type;
    private Float price;
    private Long mainPictureId;
    private List<Long> imagesIds = new ArrayList<>();

    public void addImagesId(Long imagesId) {
        imagesIds.add(imagesId);
    }

}
