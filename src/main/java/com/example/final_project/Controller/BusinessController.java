package com.example.final_project.Controller;


import com.example.final_project.Api.ApiResponse;
import com.example.final_project.DTO.BusinessDTO;
import com.example.final_project.DTO.TaxPayerDTO;
import com.example.final_project.Model.User;
import com.example.final_project.Service.BusinessService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/business")
@RequiredArgsConstructor
public class BusinessController {


    private final BusinessService businessService;



    @GetMapping("/get-all-business/{auditId}")
    public ResponseEntity getAllBusiness (@PathVariable Integer auditId){
        return ResponseEntity.status(200).body(businessService.getAllBusiness(auditId));
    }



    @GetMapping("/get-my-business/{taxPayerId}")
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



}
