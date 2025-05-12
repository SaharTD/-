package com.example.final_project.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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

private String productName;

    private Integer quantity;

    private Double unitPrice;

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
