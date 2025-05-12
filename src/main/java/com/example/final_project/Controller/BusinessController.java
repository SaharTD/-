package com.example.final_project.Controller;


import com.example.final_project.Api.ApiResponse;
import com.example.final_project.DTO.BusinessDTO;
import com.example.final_project.DTOOUT.SalesDTO;
import com.example.final_project.Model.User;
import com.example.final_project.Service.BusinessService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/business")
@RequiredArgsConstructor
public class BusinessController {


    private final BusinessService businessService;



    @GetMapping("/get-all-business")
    public ResponseEntity getAllBusiness (@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(businessService.getAllBusiness(user.getId()));
    }



    @GetMapping("/get-my-business/{bId}")
    public ResponseEntity getMyBusiness (@AuthenticationPrincipal User user,@PathVariable Integer bId){
        return ResponseEntity.status(200).body(businessService.getMyBusiness(user.getId(), bId));
    }

    @GetMapping("/get-number-of-branches/{bI}")
    public ResponseEntity getMyBranches (@AuthenticationPrincipal User user,@PathVariable Integer bI){
        return ResponseEntity.status(200).body(new ApiResponse("The number of branches for the business : "
                +businessService.getMyBranches(user.getId(), bI)));
    }


    @GetMapping("/get-my-businesses")
    public ResponseEntity getMyBusinesses (@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(businessService.getMyBusinesses(user.getId()));
    }


    @PostMapping("/add-business")
    public ResponseEntity addBusiness (@AuthenticationPrincipal User user,@RequestBody @Valid BusinessDTO businessDTO){
        businessService.addBusiness(user.getId(), businessDTO);
        return ResponseEntity.status(200).body(new ApiResponse("the business is added successfully "));
    }


    @PutMapping("/update/{businessId}")
    public ResponseEntity updateBusiness(@AuthenticationPrincipal User user, @PathVariable Integer businessId , @RequestBody BusinessDTO businessDTO ){
        businessService.updateBusiness(user.getId(), businessId,businessDTO);
        return ResponseEntity.status(200).body(new ApiResponse("the business information has been updated successfully "));
    }



    @DeleteMapping("/delete/{businessId}")
    public ResponseEntity deleteBusiness(@AuthenticationPrincipal User user,@PathVariable Integer businessId){
        businessService.deleteBusiness(user.getId(), businessId);
        return ResponseEntity.status(200).body(new ApiResponse("the business has been deleted successfully "));
    }


    @GetMapping("/sales-business/{businessId}")
    public ResponseEntity salesOperationOnBusiness(@AuthenticationPrincipal User user,@PathVariable Integer businessId){
        List<SalesDTO> sales= businessService.salesOperationOnBusiness(user.getId(), businessId);
        return ResponseEntity.status(200).body(new ApiResponse("The total number of sales operations: \n "+sales.size()
                +businessService.salesOperationOnBusiness(user.getId(), businessId)));
    }


    @GetMapping("/business-revenue/{businessId}")
    public ResponseEntity businessRevenue(@AuthenticationPrincipal User user,@PathVariable Integer businessId){
        return ResponseEntity.status(200).body(new ApiResponse("The total revenue of the business: "
                +businessService.businessRevenue(user.getId(), businessId)));
    }


}
