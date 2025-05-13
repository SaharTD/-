package com.example.final_project.Controller;


import com.example.final_project.Api.ApiResponse;
import com.example.final_project.DTO.BusinessDTO;
import com.example.final_project.DTOOUT.SalesDTO;
import com.example.final_project.Model.MyUser;
import com.example.final_project.Service.BusinessService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/business")
@RequiredArgsConstructor
public class BusinessController {


    private final BusinessService businessService;




    @GetMapping("/get-all-business")
    public ResponseEntity getAllBusiness (@AuthenticationPrincipal MyUser myUser){
        return ResponseEntity.status(200).body(businessService.getAllBusiness(myUser.getId()));
    }


    // authority -> TaxPayer
    //  sahar - 8
    @GetMapping("/get-my-business/{bId}")
    public ResponseEntity getMyBusiness (@AuthenticationPrincipal MyUser myUser, @PathVariable Integer bId){
        return ResponseEntity.status(200).body(businessService.getMyBusiness(myUser.getId(), bId));
    }

    // authority -> TaxPayer
    @GetMapping("/get-number-of-branches/{bI}")
    public ResponseEntity getMyBranches (@AuthenticationPrincipal MyUser myUser, @PathVariable Integer bI){
        return ResponseEntity.status(200).body(new ApiResponse("The number of branches for the business : "
                +businessService.getMyBranches(myUser.getId(), bI)));
    }

    // authority -> TaxPayer
    //sahar - 9
    @GetMapping("/get-my-businesses")
    public ResponseEntity getMyBusinesses (@AuthenticationPrincipal MyUser myUser){
        return ResponseEntity.status(200).body(businessService.getMyBusinesses(myUser.getId()));
    }

    // authority -> TaxPayer
    //sahar - 10
    @PostMapping("/add-business")
    public ResponseEntity addBusiness (@AuthenticationPrincipal MyUser myUser, @RequestBody @Valid BusinessDTO businessDTO){
        businessService.addBusiness(myUser.getId(), businessDTO);
        return ResponseEntity.status(200).body(new ApiResponse("the business is added successfully "));
    }

    // authority -> TaxPayer
    @PutMapping("/update/{businessId}")
    public ResponseEntity updateBusiness(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer businessId , @RequestBody BusinessDTO businessDTO ){
        businessService.updateBusiness(myUser.getId(), businessId,businessDTO);
        return ResponseEntity.status(200).body(new ApiResponse("the business information has been updated successfully "));
    }


    // authority -> TaxPayer
    @DeleteMapping("/delete/{businessId}")
    public ResponseEntity deleteBusiness(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer businessId){
        businessService.deleteBusiness(myUser.getId(), businessId);
        return ResponseEntity.status(200).body(new ApiResponse("the business has been deleted successfully "));
    }

    // authority -> TaxPayer
    //sahar - 11
    @GetMapping("/sales-business/{businessId}")
    public ResponseEntity salesOperationOnBusiness(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer businessId){
        List<SalesDTO> sales= businessService.salesOperationOnBusiness(myUser.getId(), businessId);
        return ResponseEntity.status(200).body(new ApiResponse("The total number of sales operations: \n "+sales.size()
                +businessService.salesOperationOnBusiness(myUser.getId(), businessId)));
    }

    // authority -> TaxPayer
    //sahar - 12
    @GetMapping("/business-revenue/{businessId}")
    public ResponseEntity businessRevenue(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer businessId){
        return ResponseEntity.status(200).body(new ApiResponse("The total revenue of the business: "
                +businessService.businessRevenue(myUser.getId(), businessId)));
    }


}
