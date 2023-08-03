package com.prd;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Data
@Document(collection = "products")
public class Product {

    @Id
    private String id;
    private String name;
    private double price;

    // Constructors, getters, and setters
}