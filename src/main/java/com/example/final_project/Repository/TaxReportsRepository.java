package com.example.final_project.Repository;

import com.example.final_project.Model.TaxReports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxReportsRepository extends JpaRepository<TaxReports,Integer> {

    TaxReports findTaxReportsById(Integer id);


}
