package com.example.final_project.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Sales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;



    @Column(columnDefinition = "int not null")
    @Positive(message = " sale invoice must be   positive")
    private Integer sale_invoice;

    @Column(columnDefinition = "int not null")
    @Positive(message = "total amount  must be  positive")
    private double total_amount;

    @Column(columnDefinition = "int not null")
    @Positive(message = "tax amount must be   positive")
    private double tax_amount;

    @Column(columnDefinition = "int not null")
    @Positive(message = "grand amount must be  positive")
    private double grand_amount;


    @ManyToOne
    @JoinColumn(name = "branch_id",referencedColumnName = "id")
    @JsonIgnore
    private Branch branch;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "sales")
    private Set<ItemSales> itemSales;

    @ManyToOne
    @JoinColumn(name = "counterBox_id",referencedColumnName = "id")
    @JsonIgnore
    private CounterBox counterBox;

}
