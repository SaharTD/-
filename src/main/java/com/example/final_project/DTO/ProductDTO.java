package com.example.final_project.DTO;


import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Check;


@Getter
@Setter
@AllArgsConstructor
public class ProductDTO {

    @NotEmpty
    @Size(min = 4,max = 20,message = "product name length should be between 4-20")
    private String name;

    private Integer quantity;

    private String barcode;





}
