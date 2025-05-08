package com.example.final_project.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaxPayerDTO {



    @NotEmpty(message = "name must ne not empty")
    private String name;

    @NotEmpty(message = "username must ne not empty unique")
    private String username;

    @NotEmpty(message = "password must ne not empty")
    private String password;

    @Email(message = "email must be valid")
    private String email;


    @NotEmpty(message = "the phone number should not be empty")
//    @Pattern(regexp = "^05[0-9]{8}$",message = " please enter correct phone number")
    private String phoneNumber;


    @NotEmpty(message = "the commercial registration number should not be empty")
//    @Pattern(regexp = "^[1-9][0-9]{9}$",message = " please enter correct commercial registration")
    private String commercialRegistration ;




}
