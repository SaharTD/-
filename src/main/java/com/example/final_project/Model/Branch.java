package com.example.final_project.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotEmpty
    @Column(columnDefinition = "varchar(3) not null unique")
    private String branchNumber;

    @NotEmpty
    private String region;

    @NotEmpty
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


}
