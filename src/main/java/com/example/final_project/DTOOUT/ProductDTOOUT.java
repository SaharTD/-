package com.example.final_project.DTOOUT;


import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class ProductDTOOUT {

    @NotEmpty
    @Size(min = 4,max = 20,message = "product name length should be between 4-20")
    private String name;

    @NotNull
    @PositiveOrZero(message = "price cannot be negative")
    private Double price;


    @Column(columnDefinition = "int ")
    private Integer quantity;






}
