package com.example.final_project.Service;

import com.example.final_project.Api.ApiException;
import com.example.final_project.DTO.CounterBoxDTO;
import com.example.final_project.Model.Accountant;
import com.example.final_project.Model.Branch;
import com.example.final_project.Model.CounterBox;
import com.example.final_project.Model.Sales;
import com.example.final_project.Repository.AccountantRepository;
import com.example.final_project.Repository.BranchRepository;
import com.example.final_project.Repository.CounterBoxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;


@RequiredArgsConstructor
@Service
public class CounterBoxService {

    private final CounterBoxRepository counterBoxRepository;
    private final AccountantRepository accountantRepository;
    private final BranchRepository branchRepository;

    public void createCounterBox(CounterBoxDTO counterBoxDTO) {

        if (counterBoxDTO.getAccountantId() == null) {
            throw new ApiException("Valid accountant ID is required");
        }


        Accountant accountant = accountantRepository.findAccountantById(counterBoxDTO.getAccountantId());
        if (accountant == null) {
            throw new ApiException("Accountant not found");
        }

        Branch branch = branchRepository.findBranchesById(counterBoxDTO.getBranchId());
        if (branch == null) {
            throw new ApiException("Branch not found");
        }



        CounterBox counterBox = new CounterBox();

        counterBox.setType(counterBoxDTO.getType());
        counterBox.setPaymentType(counterBoxDTO.getPaymentType());
        counterBox.setDailyTreasury(counterBoxDTO.getDailyTreasury());
        counterBox.setOpenDatetime(LocalDateTime.now());
        counterBox.setAccountant(accountant);
        counterBox.setBranch(branch);
        accountant.setLastActiveCounterBox(LocalDateTime.now());
        counterBoxRepository.save(counterBox);
        accountantRepository.save(accountant);




    }

    public List getAllCounterBoxes() {
        return counterBoxRepository.findAll();
    }

    public CounterBox getCounterBox(Integer id) {
        CounterBox counterBox = counterBoxRepository.findCounterBoxById(id);
        if (counterBox == null) {
            throw new ApiException("counterBox not found");
        }
        return counterBox;
    }

    public void updateCounterBox(CounterBox counterBox, Integer id) {
        CounterBox oldBox = counterBoxRepository.findCounterBoxById(id);
        if (oldBox == null) {
            throw new ApiException("counterBox not found");
        }

        oldBox.setType(counterBox.getType());
        oldBox.setPaymentType(counterBox.getPaymentType());
        oldBox.setDailyTreasury(counterBox.getDailyTreasury());
        oldBox.setOpenDatetime(counterBox.getOpenDatetime());
        oldBox.setCloseDatetime(counterBox.getCloseDatetime());

        counterBoxRepository.save(oldBox);
    }

    public void deleteCounterBox(Integer id) {
        CounterBox box = counterBoxRepository.findCounterBoxById(id);
        if (box == null) {
            throw new ApiException("counterBox not found");
        }
        counterBoxRepository.delete(box);
    }


    public void createCounterBox2(CounterBoxDTO counterBoxDTO) {
        CounterBox counterBox = new CounterBox();
        counterBox.setType(counterBoxDTO.getType());
        counterBox.setPaymentType(counterBoxDTO.getPaymentType());
        counterBox.setDailyTreasury(counterBoxDTO.getDailyTreasury());
        counterBox.setOpenDatetime(LocalDateTime.now());


        counterBoxRepository.save(counterBox);
    }




}
