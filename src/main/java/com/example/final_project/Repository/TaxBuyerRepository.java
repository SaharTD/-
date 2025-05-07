package com.example.final_project.Repository;

import com.example.final_project.Model.Business;
import com.example.final_project.Model.TaxBuyer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxBuyerRepository extends JpaRepository<TaxBuyer,Integer> {


    TaxBuyer findTaxBuyerById(Integer id);



}
