package com.example.final_project.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
@Entity
public class ItemSales {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotEmpty (message = "name can not be empty")
    private String productName;

    @Column
    @NotNull
    private Integer quantity;

    @Column
    @NotNull
    private Double price;

    @Column
    @NotNull
    private Double totalPrice;

    @Column
    @NotEmpty
    private String invoiceNumber;







    @ManyToOne
//    @JoinColumn(name = "sales_id")
    private Sales sales;
}
