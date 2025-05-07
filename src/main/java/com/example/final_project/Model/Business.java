package com.example.final_project.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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




    @Column(columnDefinition = "varchar(15) not null")
    @NotEmpty(message = "the business name should not be empty")
    private String businessName;




    @Column(columnDefinition = "varchar(15) not null")
    @NotEmpty(message = "the business name should not be empty")
    private String businessCategory;


    @Column(columnDefinition = "varchar(15) not null")
    @NotEmpty(message = "the tax number should not be empty")
//    @Pattern(regexp = "^5[0-9]{8}$",message = " please enter correct phone number")
    private String taxNumber ;



    @Column(columnDefinition = "varchar(10) not null")
    @NotEmpty(message = "the commercial registration number should not be empty")
//    @Pattern(regexp = "^5[0-9]{8}$",message = " please enter correct phone number")
    private String commercialRegistration ;


    private String city;
    private String region;

    private Boolean isActive;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime requestDate;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registrationDate;



    private String requestStatus;


    @ManyToOne
    @JsonIgnore
    private TaxBuyer taxBuyer;


    @ManyToOne
    @JsonIgnore
    private Auditor auditor;


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "business")
    private Set<TaxReports> taxReports;


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "business")
    private Set<Branch> branches;






}
