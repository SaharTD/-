package com.example.final_project.Service;


import com.example.final_project.Api.ApiException;
import com.example.final_project.DTO.TaxPayerDTO;
import com.example.final_project.Model.TaxPayer;
import com.example.final_project.Model.User;
import com.example.final_project.Repository.MyUserRepository;
import com.example.final_project.Repository.TaxPayerRepository;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor



public class TaxPayerService {


    private final TaxPayerRepository taxPayerRepository;
    private final MyUserRepository myUserRepository;

    public List<TaxPayer> getAllTaxTaxPayers(Integer AuditId){
        return taxPayerRepository.findAll();
    }


    public void register(TaxPayerDTO taxPayerDTO) {
        User user = new User();

        user.setRole("TAXPAYER");
        user.setUsername(taxPayerDTO.getUsername());
//        String hashPassword = new BCryptPasswordEncoder().encode(taxPayerDTO.getPassword());
//        user.setPassword(hashPassword);
        user.setEmail(taxPayerDTO.getEmail());

        TaxPayer taxPayer = new TaxPayer();

        taxPayer.setUser(user);
        taxPayer.setIsActive(false);

       myUserRepository.save(user);
       taxPayerRepository.save(taxPayer);
    }



    public void updateTaxPayer(Integer taxPayerId,TaxPayerDTO taxPayerDTO){

        TaxPayer taxPayer= taxPayerRepository.findTaxBuyerById(taxPayerId);

        if (taxPayer==null){
            throw new ApiException("the tax payer is not found");
        }

        taxPayer.getUser().setEmail(taxPayerDTO.getEmail());
        taxPayer.getUser().setUsername(taxPayerDTO.getUsername());


//        String hashPassword = new BCryptPasswordEncoder().encode(taxPayerDTO.getPassword());
//        taxPayer.getUser().setPassword(hashPassword);



        taxPayer.setPhoneNumber(taxPayerDTO.getPhoneNumber());
        taxPayer.setCommercialRegistration(taxPayerDTO.getCommercialRegistration());

        taxPayerRepository.save(taxPayer);


    }



    public void deleteTaxPayer(Integer taxPayerId ){

        TaxPayer taxPayer= taxPayerRepository.findTaxBuyerById(taxPayerId);
        if (taxPayer==null){
            throw new ApiException("the tax payer is not found");
        }
        myUserRepository.delete(taxPayer.getUser());
    }




}
