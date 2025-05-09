package com.example.final_project.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CounterBoxDTO {

    @NotEmpty(message = "type must not be empty")
    private String type;

    @NotEmpty(message = "paymentType must not be empty")
    private String paymentType;

    @NotNull(message = "DailyTreasury must not be null")
    private Double dailyTreasury;
    //ليتها كومنت لين نضبط المحاسب
   /* @NotNull(message = "accountantId is required")
    private Integer accountantId;*/

    private Integer branchId;
}
