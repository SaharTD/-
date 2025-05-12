package com.example.final_project.Service;


import com.example.final_project.Api.ApiException;
import com.example.final_project.DTO.AccountantDTO;
import com.example.final_project.DTO.TaxPayerDTO;
import com.example.final_project.Model.Accountant;
import com.example.final_project.Model.Business;
import com.example.final_project.Model.TaxPayer;
import com.example.final_project.Model.User;
import com.example.final_project.Model.*;
import com.example.final_project.Notification.NotificationService;
import com.example.final_project.Repository.*;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


@Service
@RequiredArgsConstructor


public class TaxPayerService {


    private final TaxPayerRepository taxPayerRepository;
    private final MyUserRepository myUserRepository;
    private final AccountantRepository accountantRepository;
    private final NotificationService notificationService;
    private final BusinessRepository businessRepository;
    private final BranchRepository branchRepository;
    private final CounterBoxRepository counterBoxRepository;
    private final WhatsAppService whatsAppService;


    /// run by admin
    public void activateTP(Integer adminId, Integer taxPayerId) {
        TaxPayer taxPayer = taxPayerRepository.findTaxBuyerById(taxPayerId);
        if (taxPayer == null) {
            throw new ApiException("Tax Payer is not found");
        }
        if (taxPayer.getIsActive()) {
            throw new ApiException("Tax Payer is already active");
        }
        taxPayer.setIsActive(true);
        taxPayerRepository.save(taxPayer);


        String subject = "Successful Activation of Your Account as a Taxpayer";
        String message = "Dear : " + taxPayer.getUser().getName() + " We are pleased to inform you that your account has been successfully activated you can now use our services :\n" +
                "Best regards,\n" +
                "[mohasil team]";


        notificationService.sendEmail(taxPayer.getUser().getEmail(), subject, message);

    }


    public List<TaxPayer> getAllTaxTaxPayers(Integer AuditId) {
        return taxPayerRepository.findAll();
    }


    public void register(TaxPayerDTO taxPayerDTO) {
        User user = new User();
        if (taxPayerRepository.findTaxPayerByCommercialRegistration(taxPayerDTO.getCommercialRegistration()) != null) {
            throw new ApiException("A Taxpayer With the same commercial registration number already exit");

        }

        user.setRole("TAXPAYER");
        user.setUsername(taxPayerDTO.getUsername());
        user.setName(taxPayerDTO.getName());
//        String hashPassword = new BCryptPasswordEncoder().encode(taxPayerDTO.getPassword());
//        user.setPassword(hashPassword);
        user.setPassword(taxPayerDTO.getPassword());
        user.setEmail(taxPayerDTO.getEmail());

        TaxPayer taxPayer = new TaxPayer();
        taxPayer.setPhoneNumber(taxPayerDTO.getPhoneNumber());
        taxPayer.setUser(user);
        taxPayer.setIsActive(false);
        taxPayer.setCommercialRegistration(taxPayerDTO.getCommercialRegistration());
        taxPayer.setRegistrationDate(LocalDateTime.now());

        myUserRepository.save(user);
        taxPayerRepository.save(taxPayer);

    }


    public void updateTaxPayer(Integer taxPayerId, TaxPayerDTO taxPayerDTO) {

        TaxPayer taxPayer = taxPayerRepository.findTaxBuyerById(taxPayerId);

        if (taxPayer == null) {
            throw new ApiException("The Taxpayer is not found");
        }

        taxPayer.getUser().setEmail(taxPayerDTO.getEmail());
        taxPayer.getUser().setUsername(taxPayerDTO.getUsername());

//        String hashPassword = new BCryptPasswordEncoder().encode(taxPayerDTO.getPassword());
//        taxPayer.getUser().setPassword(hashPassword);

        taxPayer.setPhoneNumber(taxPayerDTO.getPhoneNumber());
        taxPayer.setCommercialRegistration(taxPayerDTO.getCommercialRegistration());

        taxPayerRepository.save(taxPayer);

    }


    public void deleteTaxPayer(Integer taxPayerId) {

        TaxPayer taxPayer = taxPayerRepository.findTaxBuyerById(taxPayerId);
        if (taxPayer == null) {
            throw new ApiException("The Taxpayer is not found");
        }
        myUserRepository.delete(taxPayer.getUser());
    }


    public void addAccountant(Integer taxPayerID,Integer branchId ,AccountantDTO accountantDTO) {

        TaxPayer taxPayer = taxPayerRepository.findTaxBuyerById(taxPayerID);
        if (taxPayer == null) {
            throw new ApiException("The Taxpayer is not found");
        }


        Branch branch = branchRepository.findBranchesById(branchId);
        if (branch == null) {
            throw new ApiException("Branch not found");
        }

        User userACC = new User();
        userACC.setRole("ACCOUNTANT");


        userACC.setName(accountantDTO.getName());
        userACC.setUsername(accountantDTO.getUsername());
        userACC.setPassword(accountantDTO.getPassword());
        userACC.setEmail(accountantDTO.getEmail());
        myUserRepository.save(userACC);
        //accountant.setUser(userACC);

        Accountant accountant = new Accountant();
        accountant.setEmployeeId(accountantDTO.getEmployeeId());
        accountant.setUser(userACC);
        accountant.setIsActive(true);
        accountant.setBranch(branch);


        Business business = businessRepository.findBusinessByBusinessName(accountantDTO.getBusinessName());
        accountant.setBusiness(business);
        accountant.setUser(userACC);
        myUserRepository.save(userACC);

        accountantRepository.save(accountant);

        CounterBox counterBox = counterBoxRepository.findByBranch(branch);
        if (counterBox == null) {
            throw new ApiException("No counter box found for this branch");
        }


        counterBox.setAccountant(accountant);
        counterBox.setStatus("Closed");
        counterBoxRepository.save(counterBox);


        myUserRepository.save(userACC);


        accountant.setUser(userACC);

        accountant.setId(userACC.getId());

        accountantRepository.save(accountant);



        String phone=accountantDTO.getPhoneNumber();
        if (phone.startsWith("0")){
            phone=phone.substring(1);
        }
        String fullPhoneNumber="966"+phone;

        whatsAppService.sendAccountantActivationMessage(
                accountant.getUser().getUsername(),
                accountant.getUser().getPassword(),
                accountant.getEmployeeId(),
                fullPhoneNumber,
                LocalDate.now()
        );


    }

    // Endpoint 40
    public void activateAccountant(Integer taxPayerId, Integer accountantId) {
        TaxPayer taxPayer = taxPayerRepository.findTaxBuyerById(taxPayerId);
        Accountant accountant = accountantRepository.findAccountantById(accountantId);
        if (taxPayer == null)
            throw new ApiException("tax payer not found");
        if (accountant == null)
            throw new ApiException("accountant not found");

        if (accountant.getIsActive())
            throw new ApiException("accountant is already active");
        accountant.setIsActive(true);
        accountantRepository.save(accountant);
    }

    // Endpoint 41
    public void deActivateAccountant(Integer taxPayerId, Integer accountantId) {
        TaxPayer taxPayer = taxPayerRepository.findTaxBuyerById(taxPayerId);
        Accountant accountant = accountantRepository.findAccountantById(accountantId);
        if (taxPayer == null)
            throw new ApiException("tax payer not found");
        if (accountant == null)
            throw new ApiException("accountant not found");

        if (!accountant.getIsActive())
            throw new ApiException("accountant is already non active");
        accountant.setIsActive(false);
        accountantRepository.save(accountant);
    }

    // Displays all accountants associated with the TB across all branches affiliated with him
    public List<Map<String, Object>> getAccountantsByTaxPayerId(Integer taxPayerId) {
        List<Object[]> rows = accountantRepository.findAccountantsByTaxPayerId(taxPayerId);
        List<Map<String, Object>> result = new ArrayList<>();

        for (Object[] row : rows) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("taxPayerId", row[0]);
            map.put("commercialRegistration", row[1]);
            map.put("employeeId", row[2]);
            map.put("isActive", row[3]);
            map.put("branchId", row[4]);
            result.add(map);
        }

        return result;
    }








}
