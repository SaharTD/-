package com.example.final_project.DTO;


import jakarta.persistence.Column;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SaleDTO {

    private Integer sale_invoice;

}
