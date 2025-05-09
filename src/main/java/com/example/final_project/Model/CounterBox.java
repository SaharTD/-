package com.example.final_project.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
@Entity
public class CounterBox {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotEmpty(message = "type can not be empty")
    private String type;

    @Column
    @NotEmpty
    private String paymentType;

    @Column
    @NotNull
    private Double DailyTreasury;

    @Column
    private LocalDateTime openDatetime;

    @Column
    private LocalDateTime closeDatetime;

    @ManyToOne
    @JsonIgnore
    private Branch branch;

    @ManyToMany
//    @JoinColumn(name = "accountant_id") // اسم العمود اللي راح يتخزن في جدول counterBox
    private Set<Accountant> accountant;

    @OneToMany(mappedBy = "counterBox", cascade = CascadeType.ALL)
    private Set<Sales> salesList;

}
