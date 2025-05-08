package com.example.final_project.Controller;

import com.example.final_project.Api.ApiResponse;
import com.example.final_project.Model.Branch;
import com.example.final_project.Model.Product;
import com.example.final_project.Service.BranchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/branch")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @GetMapping("/get")
    public ResponseEntity getAll(){
        return ResponseEntity.status(200).body(branchService.getAllBranches());
    }

    @PostMapping("/add/{businessId}")
    public ResponseEntity addBranch(@PathVariable Integer businessId, @RequestBody@Valid Branch branch){
        branchService.addBranch(businessId, branch);
        return ResponseEntity.status(200).body(new ApiResponse("new branch added"));
    }

    @PutMapping("/update/{businessId}/branch/{branchId}")
    public ResponseEntity updateBranch(@PathVariable Integer businessId,@PathVariable Integer branchId, @RequestBody@Valid Branch branch){
        branchService.updateBranch(businessId, branchId, branch);
        return ResponseEntity.status(200).body(new ApiResponse("branch updated"));
    }

    @DeleteMapping("/delete/{businessId}/branch/{branchId}")
    public ResponseEntity deleteProduct(@PathVariable Integer businessId,@PathVariable Integer branchId){
        branchService.deleteBranch(businessId, branchId);
        return ResponseEntity.status(200).body(new ApiResponse("branch deleted"));
    }


}
