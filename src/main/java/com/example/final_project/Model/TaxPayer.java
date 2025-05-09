package com.example.final_project.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class TaxPayer {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(columnDefinition = "varchar(9) not null")
    private String phoneNumber;

    @Column(columnDefinition = "varchar(10) not null unique")
    private String commercialRegistration ;



    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registrationDate;

    private Boolean isActive;






    @ManyToOne
    @JoinColumn(name = "auditor_id",referencedColumnName = "id")
    @JsonIgnore
    private Auditor auditor;


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "taxPayer")
    private Set<Payment> payments;



    @OneToMany(cascade = CascadeType.ALL,mappedBy = "taxPayer")
    private Set<Business> businesses;


    @OneToOne
    @MapsId
    @JsonIgnore
    @JoinColumn(name = "id")
    private User user;

}
