package com.example.final_project.Service;

import com.example.final_project.Api.ApiException;
import com.example.final_project.DTO.CounterBoxDTO;
import com.example.final_project.Model.Accountant;
import com.example.final_project.Model.Branch;
import com.example.final_project.Model.CounterBox;
import com.example.final_project.Notification.NotificationService;
import com.example.final_project.Repository.AccountantRepository;
import com.example.final_project.Repository.BranchRepository;
import com.example.final_project.Repository.CounterBoxRepository;
import com.example.final_project.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;



@RequiredArgsConstructor
@Service
public class CounterBoxService {

   // private EmailService emailService;


    private final CounterBoxRepository counterBoxRepository;
    private final AccountantRepository accountantRepository;
    private final BranchRepository branchRepository;
    private final NotificationService notificationService;
    private final ProductRepository productRepository;
    private final SalesRepository salesRepository;


    //كرييت البوكس مع المحاسب
    public void createCounterBox(Integer accountantId,CounterBoxDTO counterBoxDTO) {

        Accountant accountant = accountantRepository.findAccountantById(accountantId);
        if (accountant == null) {
            throw new ApiException("Accountant not found");
        }

        Branch branch = branchRepository.findBranchesById(counterBoxDTO.getBranchId());
        if (branch == null) {
            throw new ApiException("Branch not found");
        }

        CounterBox counterBox = new CounterBox();

        counterBox.setType(counterBoxDTO.getType());
        counterBox.setOpenDatetime(LocalDateTime.now());
        counterBox.setAccountant(accountant);
        counterBox.setBranch(branch);
        counterBox.setStatus("Closed");
       // counterBox.setOpenDatetime(LocalDateTime.now());
        counterBox.setAccountant(accountant);

        if (counterBoxDTO.getBranchId() != null) {
            Branch branch2 = branchRepository.findBranchesById(counterBoxDTO.getBranchId());
            if (branch2 == null) {
                throw new ApiException("Branch not found");
            }
            counterBox.setBranch(branch);
        }

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
        box.setStatus("Opened");
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
        box.setStatus("Closed");

        counterBoxRepository.save(box);

        long hours = Duration.between(box.getOpenDatetime(), now).toHours();
        long minutes = Duration.between(box.getOpenDatetime(), now).toMinutesPart();

        return "Counter box closed successfully.\n Total open duration: " + hours + " hours and " + minutes + " minutes.";
    }


    // Endpoint 7
    // close counter box if still opening for 12h
    public void closeCounterBoxAuto(){
        List<CounterBox> counterBoxes = counterBoxRepository.findCounterBoxByOpenDatetime();
        if (counterBoxes.isEmpty())
            throw new ApiException("None of the boxes are open.");
        for (CounterBox cb:counterBoxes){
            cb.setCloseDatetime(LocalDateTime.now());
            cb.setStatus("Closed");
            String sendTo= cb.getBranch().getBusiness().getTaxPayer().getMyUser().getEmail();
            notificationService.sendEmail(sendTo,"Closing Counter Box","Dear customer \n we have closed your counter box because it was opening for 12 hours");
        }
        counterBoxRepository.saveAll(counterBoxes);
    }




}
