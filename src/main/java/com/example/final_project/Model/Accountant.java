package com.example.final_project.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Boolean isActive=false;


    @OneToOne
    @JoinColumn(name = "id")
    private User user;

    @ManyToMany(mappedBy = "accountant")
    private Set<CounterBox> counterBoxes;

    @ManyToOne
    @MapsId
    @JsonIgnore
    @JoinColumn(name = "id")
    private Branch branch;
}
