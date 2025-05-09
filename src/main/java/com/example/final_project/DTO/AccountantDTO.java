package com.example.final_project.DTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountantDTO {

    @NotEmpty(message = "name must not be empty")
    private String name;

    @NotEmpty(message = "username must not be empty")
    private String username;

    @NotEmpty(message = "password must not be empty")
    private String password;

    @Email(message = "email must be valid")
    private String email;

    @NotEmpty(message = "role must not be empty")
    private String role;

    private Integer branchId;
}
