package com.example.shopee.Admin.DTO;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class ProductRequestDTO {
    private String name;
    private double price;
    private String description;
    private String category;
    private MultipartFile image;
    private int stockQuantity;
    private Date updatedAt;
}
