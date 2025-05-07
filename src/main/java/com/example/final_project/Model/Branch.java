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
    private String branchNumber;
    @NotEmpty
    private String region;
    @NotEmpty
    private String city;
    @NotEmpty
    private String address;


    @ManyToOne
    @JsonIgnore
    private Business business;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "branch")
    private Set<Product> products;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "branch")
    private Set<Accountant> accountants;
}
