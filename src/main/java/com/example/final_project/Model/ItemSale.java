package com.example.final_project.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemSale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(columnDefinition = "varchar(40) not null")
    @NotEmpty(message = "name of product must be not empty")
    private String productName;

    @Column(columnDefinition = "int not null")
    @NotNull(message = "quantity must be not empty")
    private Integer quantity;

    @Column(columnDefinition = "double not null")
    @NotNull(message = "unitPrice must be not empty")
    private Double unitPrice;

    @Column(columnDefinition = "double not null")
    @NotNull(message = "totalPrice must be not empty")
    private Double totalPrice; // = quantity * unitPrice


    @ManyToOne
    @JsonIgnore
    @JoinColumn
    private Sales sales;

    @ManyToOne
    @JsonIgnore
    @JoinColumn
    private Product product;




}
