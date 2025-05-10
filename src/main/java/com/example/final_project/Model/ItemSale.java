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



    private Integer quantity;

    private Double unitPrice;

    private Double totalPrice; // = quantity * unitPrice


    @ManyToOne
    @JsonIgnore
    private Sales sales;

    @ManyToOne
    private Product product;


}
