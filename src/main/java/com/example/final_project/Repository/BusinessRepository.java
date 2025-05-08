package com.example.final_project.Repository;

import com.example.final_project.Model.Business;
import com.example.final_project.Model.TaxPayer;
import com.example.final_project.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusinessRepository extends JpaRepository<Business,Integer> {


    Business findBusinessById(Integer id);

   List<Business> findBusinessByTaxPayer(TaxPayer taxPayer);

    Business findBusinessByIdAndTaxPayer(Integer id, TaxPayer taxPayer);

}
