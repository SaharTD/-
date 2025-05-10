package com.example.final_project.Service;

import com.example.final_project.Api.ApiException;
import com.example.final_project.DTO.CounterBoxDTO;
import com.example.final_project.Model.*;
import com.example.final_project.Repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;


@RequiredArgsConstructor
@Service
public class CounterBoxService {

   // private EmailService emailService;


    private final CounterBoxRepository counterBoxRepository;
    private final AccountantRepository accountantRepository;
    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;
    private final SalesRepository salesRepository;

    //كرييت مع المحاسب
    public void createCounterBox(CounterBoxDTO counterBoxDTO) {
        if (counterBoxDTO.getAccountantId() == null) {
            throw new ApiException("Accountant ID is required");
        }

        Accountant accountant = accountantRepository.getReferenceById(counterBoxDTO.getAccountantId());
        if (accountant == null) {
            throw new ApiException("Accountant not found");
        }
        CounterBox counterBox = new CounterBox();
        counterBox.setType(counterBoxDTO.getType());
        counterBox.setPaymentType(counterBoxDTO.getPaymentType());
        counterBox.setDailyTreasury(counterBoxDTO.getDailyTreasury());
       // counterBox.setOpenDatetime(LocalDateTime.now());
        counterBox.setAccountant(accountant);

        if (counterBoxDTO.getBranchId() != null) {
            Branch branch = branchRepository.getReferenceById(counterBoxDTO.getBranchId());
            if (branch == null) {
                throw new ApiException("Branch not found");
            }
            counterBox.setBranch(branch);
        }

        counterBoxRepository.save(counterBox);
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
       // oldBox.setOpenDatetime(counterBox.getOpenDatetime());
        //oldBox.setCloseDatetime(counterBox.getCloseDatetime());

        counterBoxRepository.save(oldBox);
    }

    public void deleteCounterBox(Integer id) {
        CounterBox box = counterBoxRepository.findCounterBoxById(id);
        if (box == null) {
            throw new ApiException("counterBox not found");
        }
        counterBoxRepository.delete(box);
    }




//-----------------------------------------------------

    public void openCounterBox(Integer boxId, Integer accountantId) {
        CounterBox box = counterBoxRepository.findCounterBoxById(boxId);
        if (box == null) {
            throw new ApiException("CounterBox not found");
        }

        if (box.getOpenDatetime() != null) {
            throw new ApiException("CounterBox is already opened");
        }

        Accountant accountant = accountantRepository.getReferenceById(accountantId);
        if (accountant == null) {
            throw new ApiException("Accountant not found");
        }

        box.setOpenDatetime(LocalDateTime.now());
        box.setAccountant(accountant);
        //box.setStatus(true);

        counterBoxRepository.save(box);
    }

    public String closeCounterBox(Integer counterBoxId, Integer accountantId) {
        CounterBox box = counterBoxRepository.findCounterBoxById(counterBoxId);
        if (box == null) {
            throw new ApiException("CounterBox not found");
        }

        if (box.getAccountant() == null || !box.getAccountant().getId().equals(accountantId)) {
            throw new ApiException("You are not authorized to close this counter box");
        }

        if (box.getCloseDatetime() != null) {
            throw new ApiException("This counter box is already closed");
        }

        LocalDateTime now = LocalDateTime.now();
        box.setCloseDatetime(now);
        //box.setStatus(false);

        counterBoxRepository.save(box);

        long hours = Duration.between(box.getOpenDatetime(), now).toHours();
        long minutes = Duration.between(box.getOpenDatetime(), now).toMinutesPart();

        return "Counter box closed successfully.\n Total open duration: " + hours + " hours and " + minutes + " minutes.";
    }












}
