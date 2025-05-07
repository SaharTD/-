package com.example.final_project.Service;

import com.example.final_project.Api.ApiException;
import com.example.final_project.Model.Branch;
import com.example.final_project.Model.Business;
import com.example.final_project.Repository.BranchRepository;
import com.example.final_project.Repository.BusinessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchService {


    private final BranchRepository branchRepository;
    private final BusinessRepository businessRepository;


    public List<Branch> getAllBranches(){
        return branchRepository.findAll();
    }


    public void addBranch(Integer businessId, Branch branch){
        Business business = businessRepository.findBusinessById(businessId);
        if (business==null)
            throw new ApiException("business not found");

    }

}
