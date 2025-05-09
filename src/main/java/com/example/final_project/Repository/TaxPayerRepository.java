package com.example.final_project.Repository;

import com.example.final_project.Model.TaxPayer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxPayerRepository extends JpaRepository<TaxPayer,Integer> {


    TaxPayer findTaxBuyerById(Integer id);

    TaxPayer findTaxPayerByCommercialRegistration(String commercialRegistration);


}
