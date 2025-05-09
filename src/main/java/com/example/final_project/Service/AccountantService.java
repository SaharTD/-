package com.example.final_project.Service;

import com.example.final_project.Api.ApiException;
import com.example.final_project.DTO.AccountantDTO;
import com.example.final_project.Model.Accountant;
import com.example.final_project.Model.Branch;
import com.example.final_project.Model.User;
import com.example.final_project.Repository.AccountantRepository;
import com.example.final_project.Repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AccountantService {

    private final AccountantRepository accountantRepository;
    private final BranchRepository branchRepository;




    public void createAccountant(AccountantDTO accountantDTO) {
        Accountant accountant = new Accountant();

        User user = new User();
        user.setName(accountantDTO.getName());
        user.setUsername(accountantDTO.getUsername());
        user.setPassword(accountantDTO.getPassword());
        user.setEmail(accountantDTO.getEmail());
        user.setRole(accountantDTO.getRole());
        accountant.setUser(user);

        //نختار الفرع
       /* if (accountantDTO.getBranchId() != null) {
            Branch branch = branchRepository.getReferenceById(accountantDTO.getBranchId());
            if (branch == null) {
                throw new ApiException("Branch not found");
            }
            accountant.setBranch(branch);
        }*/

        accountantRepository.save(accountant);
    }



    public List<Accountant> getAllAccountants() {
        return accountantRepository.findAll();
    }

    public void updateAccountant(Integer id, AccountantDTO accountantDTO) {
        Accountant accountant = accountantRepository.getReferenceById(id);
        if (accountant==null){
            throw new ApiException("Accountant not found");
        }
        User user = accountant.getUser();
        user.setName(accountantDTO.getName());
        user.setUsername(accountantDTO.getUsername());
        user.setPassword(accountantDTO.getPassword());
        user.setEmail(accountantDTO.getEmail());
        user.setRole(accountantDTO.getRole());

        if (accountantDTO.getBranchId() != null) {
            Branch branch = branchRepository.getReferenceById(accountantDTO.getBranchId());
            if (branch==null){
                throw new ApiException("Branch not found");
            }
            accountant.setBranch(branch);
        }

        accountantRepository.save(accountant);
    }


    //Delete the accountant if it is not associated with any counterBox
    public void deleteAccountant(Integer id) {
        Accountant accountant = accountantRepository.getReferenceById(id);

        if (accountant == null) {
            throw new ApiException("Accountant not found");
        }
        if (accountant.getCounterBoxes() != null && !accountant.getCounterBoxes().isEmpty()) {
            throw new ApiException("Cannot delete accountant with assigned counter boxes");
        }
        accountantRepository.delete(accountant);
    }



}
