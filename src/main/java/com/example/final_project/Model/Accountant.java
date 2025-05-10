package com.example.final_project.Model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registrationDate;




    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastActiveCounterBox;


    @OneToOne
    @MapsId
    @JsonIgnore
    @JoinColumn(name = "id")
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
