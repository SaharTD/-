package com.example.final_project.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.Check;

@Data
@AllArgsConstructor
public class DTOAuditor {

//    private Integer user_id;
    @NotEmpty
    private String role="auditor";
    @NotEmpty
    @Size(min = 4,max = 20,message = "auditor name length should be between 4-20")
    @Column(columnDefinition = "varchar(20) not null")
    @Check(constraints = "length(name)>=4 and length(name)<=20")
    private String name;
    @NotEmpty
    @Size(min = 4,max = 20,message = "username length should be between 4-20")
    @Column(columnDefinition = "varchar(20) not null")
    @Check(constraints = "length(username)>=4 and length(username)<=20")
    private String username;
    @Email
    private String email;
    @NotEmpty
    @Size(min = 8,max = 16,message = "password length should be between 8-16")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@&*%#.=+/-]).{8,16}$")
    @Column(columnDefinition = "varchar(16) not null")
    @Check(constraints = "length(name)>=8 and length(name)<=16")
    private String password;
    private String SOCPA;

}
