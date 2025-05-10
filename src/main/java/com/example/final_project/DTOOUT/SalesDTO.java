package com.example.final_project.DTOOUT;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesDTO {


    @Positive(message = " sale invoice must be   positive")
    private Integer sale_invoice;


    @Positive(message = "grand amount must be  positive")
    private Double grand_amount;


}
