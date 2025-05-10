package com.example.final_project.DTO;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter @Setter
public class SaleRequestDTO {

    private Integer counterBoxId;
    private Integer branchId;
    private Set<Integer> productIds;



}
