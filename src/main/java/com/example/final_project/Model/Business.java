package com.example.final_project.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Business {


    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(50) not null unique")
    @NotEmpty(message = "the business name should not be empty")
    private String businessName;

    @Column(columnDefinition = "varchar(50) not null")
    @NotEmpty(message = "the business name should not be empty")
    private String businessCategory;

    @Column(columnDefinition = "varchar(15) not null")
    @NotEmpty(message = "the tax number should not be empty")
    @Pattern(regexp = "^[1-9][0-9]{14}$", message = " please enter correct tax number")
    private String taxNumber;

    @Column(columnDefinition = "varchar(10) not null")
    @NotEmpty(message = "the commercial registration number should not be empty")
    @Pattern(regexp = "^[1-9][0-9]{9}$", message = " please enter correct commercial registration")
    private String commercialRegistration;

    @Column(columnDefinition = "varchar(20) not null")
    @NotEmpty(message = "the business located city should not be empty")
    private String city;

    @Column(columnDefinition = "varchar(20) not null")
    @NotEmpty(message = "the business located region should not be empty")
    private String region;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime requestDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registrationDate;

    private Boolean isActive;



    @ManyToOne
    @JsonIgnore
    private TaxPayer taxPayer;

    @ManyToOne
    @JsonIgnore
    private Auditor auditor;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "business")
    private Set<TaxReports> taxReports;


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "business")
    private Set<Accountant> accountants;


    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "business")
    private Set<Branch> branches;


}
