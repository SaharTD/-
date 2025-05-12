package com.example.final_project.Controller;


import com.example.final_project.Api.ApiResponse;
import com.example.final_project.DTO.BusinessDTO;
import com.example.final_project.DTO.TaxPayerDTO;
import com.example.final_project.DTOOUT.SalesDTO;
import com.example.final_project.Model.User;
import com.example.final_project.Service.BusinessService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/business")
@RequiredArgsConstructor
public class BusinessController {


    private final BusinessService businessService;




    /// sahar
    /// TP
    @GetMapping("/get-all-business/{auditId}")
    public ResponseEntity getAllBusiness (@AuthenticationPrincipal User  audit){
        return ResponseEntity.status(200).body(businessService.getAllBusiness(audit.getId()));
    }



    /// sahar
    /// TP
    @GetMapping("/get-my-business/{bId}")
    public ResponseEntity getMyBusiness (@AuthenticationPrincipal User taxPayer, @PathVariable Integer bId){
        return ResponseEntity.status(200).body(businessService.getMyBusiness(taxPayer.getId(),bId));
    }



    /// sahar
    /// TP
    @GetMapping("/get-number-of-branches/{bI}")
    public ResponseEntity getMyBranches (@AuthenticationPrincipal User taxPayer,@PathVariable Integer bI){
        return ResponseEntity.status(200).body(new ApiResponse("The number of branches for the business : "+businessService.getMyBranches(taxPayer.getId(), bI)));
    }


    /// sahar
    /// TP
    @GetMapping("/get-my-businesses")
    public ResponseEntity getMyBusinesses (@AuthenticationPrincipal User  taxPayer){
        return ResponseEntity.status(200).body(businessService.getMyBusinesses(taxPayer.getId()));
    }


    /// sahar
    /// TP
    @PostMapping("/add-business")
    public ResponseEntity addBusiness (@RequestBody @Valid BusinessDTO businessDTO , @AuthenticationPrincipal User  taxPayer){
        businessService.addBusiness(taxPayer.getId(), businessDTO);
        return ResponseEntity.status(200).body(new ApiResponse("the business is added successfully "));
    }


    @PutMapping("/update/{businessId}")
    public ResponseEntity updateBusiness(@AuthenticationPrincipal User taxPayer , @PathVariable Integer businessId , @RequestBody BusinessDTO businessDTO ){
        businessService.updateBusiness(taxPayer.getId(), businessId,businessDTO);
        return ResponseEntity.status(200).body(new ApiResponse("the business information has been updated successfully "));
    }



    @DeleteMapping("delete/{businessId}")
    public ResponseEntity deleteBusiness(@AuthenticationPrincipal User taxPayer,@PathVariable Integer businessId){
        businessService.deleteBusiness(taxPayer.getId(),businessId);
        return ResponseEntity.status(200).body(new ApiResponse("the business has been deleted successfully "));
    }



    /// sahar
    /// TP
    @GetMapping("sales-business/{businessId}")
    public ResponseEntity salesOperationOnBusiness(@AuthenticationPrincipal User taxPayer,@PathVariable Integer businessId){
        List<SalesDTO> sales= businessService.salesOperationOnBusiness(taxPayer.getId(), businessId);
        return ResponseEntity.status(200).body(new ApiResponse("The total number of sales operations: \n "+sales.size()
                +businessService.salesOperationOnBusiness(taxPayer.getId(), businessId)));
    }


    /// sahar
    /// TP
    @GetMapping("business-revenue/{businessId}")
    public ResponseEntity businessRevenue(@AuthenticationPrincipal User taxPayer,@PathVariable Integer businessId){
        return ResponseEntity.status(200).body(new ApiResponse("The total revenue of the business: "+businessService.businessRevenue(taxPayer.getId(), businessId)));
    }



}
