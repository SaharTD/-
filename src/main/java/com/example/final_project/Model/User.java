package com.example.final_project.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(20) not null")
    @NotEmpty(message = "name must ne not empty")
    private String name;

    @Column(columnDefinition = "varchar(20) not null")
    @NotEmpty(message = "username must ne not empty unique")
    private String username;

    @Column(columnDefinition = "varchar(20) not null")
    @NotEmpty(message = "password must ne not empty")
    private String password;

    @Column(columnDefinition = "varchar(40) not null unique")
    @Email(message = "email must be valid")
    private String email;

    @Check(constraints = "role in ('TAXPAYER','AUDIT','ACCOUNTANT')")
    @Column(columnDefinition ="varchar(20) CHECK(role IN ('TAXPAYER','AUDIT','ACCOUNTANT'))") @NotEmpty(message = "role must ne not empty")
    @Pattern(regexp = "TAXPAYER|AUDIT|ACCOUNTANT|ADMIN")
    private String role;


    @OneToOne(cascade = CascadeType.ALL,mappedBy = "user")
    @PrimaryKeyJoinColumn
    private Auditor auditor;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "user")
    @PrimaryKeyJoinColumn
    private Accountant accountant;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "user")
    @PrimaryKeyJoinColumn
    private TaxPayer taxPayer;


}
