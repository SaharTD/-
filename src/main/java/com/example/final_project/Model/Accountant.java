package com.example.final_project.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Accountant {

    @Id
    private Integer id;

    @Column(columnDefinition = "varchar(10) not null unique")
    private String employeeId;

    private Boolean isActive;


    @OneToOne
    @MapsId
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "accountant")
    private Set<CounterBox> counterBoxes;



    @ManyToOne
    @JsonIgnore
    @JoinColumn(name ="business_id")
    private Business business;


    @ManyToOne
    @JsonIgnore
    @JoinColumn(name ="branch_id")
    private Branch branch;
}
