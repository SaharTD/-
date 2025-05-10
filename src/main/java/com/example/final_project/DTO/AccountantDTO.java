package com.example.final_project.DTO;


import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class AccountantDTO {


    @NotEmpty(message = "name must ne not empty")
    private String name;

    @NotEmpty(message = "username must ne not empty unique")
    private String username;

    @NotEmpty(message = "password must not be empty")
    private String password;

    @Email(message = "email must be valid")
    private String email;
//
//    @NotEmpty(message = "role must not be empty")
//    private String role;
//

    //@NotEmpty(message = "role must not be empty")
    private String role;

    private Integer branchId;

    @NotEmpty(message = "the employee id should not be empty")
//    @Pattern(regexp = "^ACC[0-9]{3,8}$",message = "the employee id must start with ACC followed by 3-8 characters")
    private String employeeId;


    @NotEmpty(message = "the business name should not be empty")
    private String businessName;


}
