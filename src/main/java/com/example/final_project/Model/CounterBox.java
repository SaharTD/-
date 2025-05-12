package com.example.final_project.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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

    @Column(columnDefinition = "varchar(20) not null")
    @NotEmpty(message = "type can not be empty")
    private String type;

    @Column(columnDefinition = "varchar(20) not null")
    private String paymentType;

    @Column(columnDefinition = "int not null")
    @NotNull(message = "Daily Treasury ")
    private Double DailyTreasury;

    @Column
    private LocalDateTime openDatetime;

    @Column(name = "close_datetime", columnDefinition = "TIMESTAMP COMMENT 'time of close the box'")
    private LocalDateTime closeDatetime;

    @Pattern(regexp = "^Opened|Closed$")
    private String status;

//    @Column
//    private Boolean status=false;


    @ManyToOne
    @JsonIgnore
    private Branch branch;

    @ManyToOne
    @JsonIgnore
    private Accountant accountant;

    @OneToMany(mappedBy = "counterBox", cascade = CascadeType.ALL)
    private Set<Sales> salesList;

}
