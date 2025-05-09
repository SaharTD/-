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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(10) not null unique")
    private String employeeId;

    private Boolean isActive;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "accountant")
    private Set<CounterBox> counterBoxes;

    @ManyToOne
    @MapsId
    @JsonIgnore
    @JoinColumn(name = "id")
    private Branch branch;
}
