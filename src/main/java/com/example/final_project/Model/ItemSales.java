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




    @ManyToMany
    @JoinTable(
            name = "counterbox_items",
            joinColumns = @JoinColumn(name = "item_sales_id"),
            inverseJoinColumns = @JoinColumn(name = "counter_box_id")
    )
    private Set<CounterBox> counterBoxes = new HashSet<CounterBox>();


}
