package com.example.final_project.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessDTO {



    @NotEmpty(message = "the business name should not be empty")
    private String businessName;




    @NotEmpty(message = "the business name should not be empty")
    private String businessCategory;

    @NotEmpty(message = "the commercial registration number should not be empty")
    @Pattern(regexp = "^[1-9][0-9]{9}$",message = " please enter correct commercial registration")
    private String commercialRegistration ;


    @NotEmpty(message = "the tax number should not be empty")
    @Pattern(regexp = "^[1-9][0-9]{14}$",message = " please enter correct tax number")
    private String taxNumber ;




    @NotEmpty(message = "the business located city should not be empty")
    private String city;

    @NotEmpty(message = "the business located region should not be empty")
    private String region;

}
