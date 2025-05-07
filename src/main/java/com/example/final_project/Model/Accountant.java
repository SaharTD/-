package com.example.final_project.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
@Entity
public class Accountant {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;



    @OneToMany(mappedBy = "accountant")
    private List<CounterBox> counterBoxes = new ArrayList<>();

}
