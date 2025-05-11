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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/business")
@RequiredArgsConstructor
public class BusinessController {


    private final BusinessService businessService;



    @GetMapping("/get-all-business/{auditId}")
    public ResponseEntity getAllBusiness (@PathVariable Integer auditId){
        return ResponseEntity.status(200).body(businessService.getAllBusiness(auditId));
    }



    @GetMapping("/get-my-business/{taxPayerId}/{bId}")
    public ResponseEntity getMyBusiness (@PathVariable Integer taxPayerId,@PathVariable Integer bId){
        return ResponseEntity.status(200).body(businessService.getMyBusiness(taxPayerId,bId));
    }

    @GetMapping("/get-number-of-branches/{taxPayerId}/{bI}")
    public ResponseEntity getMyBranches (@PathVariable Integer taxPayerId,@PathVariable Integer bI){
        return ResponseEntity.status(200).body(new ApiResponse("The number of branches for the business : "+businessService.getMyBranches(taxPayerId,bI)));
    }


    @GetMapping("/get-my-businesses/{taxPayerId}")
    public ResponseEntity getMyBusinesses (@PathVariable Integer taxPayerId){
        return ResponseEntity.status(200).body(businessService.getMyBusinesses(taxPayerId));
    }


    @PostMapping("/add-business/{taxPayerId}")
    public ResponseEntity addBusiness (@RequestBody @Valid BusinessDTO businessDTO , @PathVariable Integer taxPayerId){
        businessService.addBusiness(taxPayerId,businessDTO);
        return ResponseEntity.status(200).body(new ApiResponse("the business is added successfully "));
    }


    @PutMapping("/update/{taxPayerId}/{businessId}")
    public ResponseEntity updateBusiness(@PathVariable Integer taxPayerId , @PathVariable Integer businessId , @RequestBody BusinessDTO businessDTO ){
        businessService.updateBusiness(taxPayerId,businessId,businessDTO);
        return ResponseEntity.status(200).body(new ApiResponse("the business information has been updated successfully "));
    }



    @DeleteMapping("delete/{taxPayerId}/{businessId}")
    public ResponseEntity deleteBusiness(@PathVariable Integer taxPayerId,@PathVariable Integer businessId){
        businessService.deleteBusiness(taxPayerId,businessId);
        return ResponseEntity.status(200).body(new ApiResponse("the business has been deleted successfully "));
    }


    @GetMapping("sales-business/{taxPayerId}/{businessId}")
    public ResponseEntity salesOperationOnBusiness(@PathVariable Integer taxPayerId,@PathVariable Integer businessId){
        List<SalesDTO> sales= businessService.salesOperationOnBusiness(taxPayerId, businessId);
        return ResponseEntity.status(200).body(new ApiResponse("The total number of sales operations: \n "+sales.size()
                +businessService.salesOperationOnBusiness(taxPayerId, businessId)));
    }


    @GetMapping("business-revenue/{taxPayerId}/{businessId}")
    public ResponseEntity businessRevenue(@PathVariable Integer taxPayerId,@PathVariable Integer businessId){
        return ResponseEntity.status(200).body(new ApiResponse("The total revenue of the business: "+businessService.businessRevenue(taxPayerId,businessId)));
    }



}
