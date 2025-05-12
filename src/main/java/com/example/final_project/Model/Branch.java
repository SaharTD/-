package com.example.final_project.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "branch Number must be noy empty")
    @Column(columnDefinition = "varchar(30) not null ")
    private String branchNumber;

    @NotEmpty(message = "region must be noy empty")
    @Column(columnDefinition = "varchar(50) not null")
    @Size(min = 4)
    private String region;

    @NotEmpty(message ="city must be noy empty" )
    @Column(columnDefinition = "varchar(50) not null")
    @Size(min = 4)
    private String city;


    @ManyToOne
    @JsonIgnore
    private Business business;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "branch")
    private Set<Product> products;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "branch")
    private Set<Accountant> accountants;


    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branch")
    private Set<CounterBox> counterBoxes;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "branch")
    private Set<Sales> sales;

}
