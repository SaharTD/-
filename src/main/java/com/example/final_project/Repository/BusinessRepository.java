package com.example.final_project.Repository;

import com.example.final_project.Model.Business;
import com.example.final_project.Model.TaxPayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BusinessRepository extends JpaRepository<Business,Integer> {


    @Query("select b from Business b where b.taxPayer.id=?1")
    Business getBusinessFromTaxPayerId(Integer id);

    Business findBusinessById(Integer id);

   List<Business> findBusinessByTaxPayer(TaxPayer taxPayer);

    Business findBusinessByIdAndTaxPayer(Integer id, TaxPayer taxPayer);

    Business findBusinessByBusinessName( String businessName);




}
