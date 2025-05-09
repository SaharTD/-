package com.example.final_project.Service;


import com.example.final_project.Api.ApiException;
import com.example.final_project.DTO.AccountantDTO;
import com.example.final_project.DTO.TaxPayerDTO;
import com.example.final_project.Model.Accountant;
import com.example.final_project.Model.TaxPayer;
import com.example.final_project.Model.User;
import com.example.final_project.Notification.NotificationService;
import com.example.final_project.Repository.AccountantRepository;
import com.example.final_project.Repository.MyUserRepository;
import com.example.final_project.Repository.TaxPayerRepository;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor


public class TaxPayerService {


    private final TaxPayerRepository taxPayerRepository;
    private final MyUserRepository myUserRepository;
    private final AccountantRepository accountantRepository;
    private final NotificationService notificationService;

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
//
//
//        String subject = ": Successful Activation of Your Account ";
//        String message = "Dear : " + taxPayer.getUser().getName() + " We are pleased to inform you that your account has been successfully activated you can now use our services :\n" +
//                "Best regards,\n" +
//                "[mohasil team]";
//
//
//        notificationService.sendEmail(taxPayer.getUser().getEmail(), message, subject);

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
        user.setName(taxPayerDTO.getName());
        user.setUsername(taxPayerDTO.getUsername());
//        String hashPassword = new BCryptPasswordEncoder().encode(taxPayerDTO.getPassword());
//        user.setPassword(hashPassword);
        user.setPassword(taxPayerDTO.getPassword());
        user.setEmail(taxPayerDTO.getEmail());


        TaxPayer taxPayer = new TaxPayer();
        taxPayer.setPhoneNumber(taxPayerDTO.getPhoneNumber());
        taxPayer.setCommercialRegistration(taxPayerDTO.getCommercialRegistration());


        taxPayer.setUser(user);
        taxPayer.setIsActive(false);
        taxPayer.setCommercialRegistration(taxPayerDTO.getCommercialRegistration());
        taxPayer.setPhoneNumber(taxPayerDTO.getPhoneNumber());

        taxPayer.setRegistrationDate(LocalDateTime.now());

        myUserRepository.save(user);
        taxPayerRepository.save(taxPayer);

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


    public void addAccountant(Integer taxPayerID, AccountantDTO accountantDTO) {

        TaxPayer taxPayer = taxPayerRepository.findTaxBuyerById(taxPayerID);
        if (taxPayer == null) {
            throw new ApiException("The Taxpayer is not found");
        }


        User userACC = new User();
        userACC.setRole("ACCOUNTANT");


        userACC.setName(accountantDTO.getName());
        userACC.setUsername(accountantDTO.getUsername());
        userACC.setPassword(accountantDTO.getPassword());
        userACC.setEmail(accountantDTO.getEmail());
        myUserRepository.save(userACC);

        Accountant accountant = new Accountant();
        accountant.setEmployeeId(accountantDTO.getEmployeeId());
        accountant.setUser(userACC);
        accountantRepository.save(accountant);


//
//        String subject=": Successful Activation of Your Account";
//        String message="We are pleased to inform you that your account has been successfully activated. Below are your login details:\n" +
//                "\n" +
//                "Username: \n" +accountant.getUser().getUsername()+
//                "\n" +
//                "Password:\n" +accountant.getUser().getPassword()+
//                "\n" +
//                "Employee Code:\n" +accountant.getEmployeeId()+
//                "\n" +
//                "Please keep this information secure and do not share it with anyone.\n" +
//                "\n" +
//                "If you have any questions or need assistance, feel free to contact us.\n" +
//                "\n" +
//                "Best regards,\n" +
//                "[mohasil team]";
//
//
//        notificationService.sendEmail(accountant.getUser().getEmail(),message,subject);
//    }
//


    }
}