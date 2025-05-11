package com.example.final_project.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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






    @Column(columnDefinition = "int unique")
//    @Positive(message = " sale invoice must be  positive")
    private Integer sale_invoice;

    @Column(columnDefinition = "double ")
//    @Positive(message = "total amount  must be positive")
    private Double total_amount=0.0;

    @Column(columnDefinition = "double ")
//    @Positive(message = "tax amount must be  positive")
    private Double tax_amount=0.0;

    @Column(columnDefinition = "double ")
//    @Positive(message = "grand amount must be  positive")
    private Double grand_amount=0.0;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime saleDate;

    @ManyToMany
    @JsonIgnore
    private Set<Product> products;

    @ManyToOne
//    @JoinColumn(name = "branch_id",referencedColumnName = "id")
    @JsonIgnore
    private Branch branch;


    @ManyToOne
//    @JoinColumn(name = "counterBox_id",referencedColumnName = "id")
    @JsonIgnore
    private CounterBox counterBox;

}
