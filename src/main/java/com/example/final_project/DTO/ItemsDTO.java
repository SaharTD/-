package com.example.final_project.DTO;


import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class ItemsDTO {



    @Column(columnDefinition = "varchar(40) not null")
    @NotEmpty(message = "name of product must be not empty")
    private String productName;

    @Column(columnDefinition = "int not null")
    @NotNull(message = "quantity must be not empty")
    private Integer quantity;

    private String barcode;


}
