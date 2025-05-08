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
    @NotEmpty(message = "the phone number should not be empty")
    @Pattern(regexp = "^5[0-9]{8}$",message = " please enter correct phone number")
    private String phoneNumber;

    @Column(columnDefinition = "varchar(10) not null")
    @NotEmpty(message = "the commercial registration number should not be empty")
    @Pattern(regexp = "^[1-9][0-9]{9}$",message = " please enter correct commercial registration")
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
    private User user;


}
