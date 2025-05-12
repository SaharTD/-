package com.example.final_project.Controller;

import com.example.final_project.Api.ApiResponse;
import com.example.final_project.DTOOUT.SalesDTO;
import com.example.final_project.Model.Branch;
import com.example.final_project.Model.Product;
import com.example.final_project.Model.User;
import com.example.final_project.Service.BranchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/branch")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @GetMapping("/get")
    public ResponseEntity getAll(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(branchService.getAllBranches());
    }

    @PostMapping("/add/{businessId}")
    public ResponseEntity addBranch(@AuthenticationPrincipal User user,@PathVariable Integer businessId,@RequestBody @Valid Branch branch){
        branchService.addBranch(businessId, user.getId(), branch);
        return ResponseEntity.status(200).body(new ApiResponse("new branch added"));
    }

    @PutMapping("/update/{businessId}/branch/{branchId}")
    public ResponseEntity updateBranch(@AuthenticationPrincipal User user,@PathVariable Integer businessId,@PathVariable Integer branchId, @RequestBody @Valid Branch branch){
        branchService.updateBranch(businessId, branchId, branch);
        return ResponseEntity.status(200).body(new ApiResponse("branch updated"));
    }

    @DeleteMapping("/delete/{businessId}/branch/{branchId}")
    public ResponseEntity deleteProduct(@AuthenticationPrincipal User user,@PathVariable Integer businessId,@PathVariable Integer branchId){
        branchService.deleteBranch(businessId, branchId);
        return ResponseEntity.status(200).body(new ApiResponse("branch deleted"));
    }

//    /// 3
//    @GetMapping("branch-sales/{branchId}")
//    public ResponseEntity salesOperationOnBranch(@AuthenticationPrincipal User user,@PathVariable Integer branchId) {
//        List<SalesDTO> sales = branchService.salesOperationOnBranch(user.getId(), branchId);
//        return ResponseEntity.status(200).body(new ApiResponse("The total number of sales operations: \n " + sales.size()
//                + branchService.salesOperationOnBranch(user.getId(), branchId)));
//    }

    @GetMapping("/sales-summary/{branchId}")
    //    public ResponseEntity<Map<String, Double>> getSalesByBranch(@AuthenticationPrincipal User taxPayer, @PathVariable Integer branchId) {
    public ResponseEntity<Map<String, Double>> getSalesByBranch(User taxPayer, @PathVariable Integer branchId) {
        return ResponseEntity.status(200).body(branchService.getSalesSummaryByBranch(taxPayer.getId(),branchId));
    }

    // Endpoint 12
    @GetMapping("/get-tax-payer-branches")
    public ResponseEntity getAllTaxPayerBranches(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(branchService.getTaxPayerBranches(user.getId()));
    }



}
