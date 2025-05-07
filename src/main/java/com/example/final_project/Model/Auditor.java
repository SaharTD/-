package com.example.final_project.Model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Auditor {

    @Id
    private Integer id;
    @NotEmpty
    private String SOCPA;

    @OneToOne
    @MapsId
    @JsonIgnore
    @JoinColumn
    private User user;


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "auditor")
    private Set<TaxBuyer> taxBuyers;





}
