package com.example.final_project.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.CollectionIdJdbcTypeCode;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Size(min = 4,max = 20,message = "product name length should be between 4-20")
    @Column(columnDefinition = "varchar(20) not null unique")
    @Check(constraints = "length(name)>=4 and length(name)<=20")
    private String name;

    @NotNull
    @PositiveOrZero(message = "price cannot be negative")
    @Column(columnDefinition = "double not null")
    @Check(constraints = "price>=0.0")
    private Double price;


    @Column(columnDefinition = "int ")
    private Integer quantity;


    @PositiveOrZero(message = "stock cannot be negative")
    @Column(columnDefinition = "int not null")
    @Check(constraints = "stock>=0")
    private Integer stock;

    @Column(columnDefinition = "varchar(13) unique")
    private String barcode;


    @ManyToOne
    @JsonIgnore
    private Branch branch;

//    @ManyToMany(mappedBy = "products")
//    private Set<Sales> sales;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ItemSale> itemSales;


}
