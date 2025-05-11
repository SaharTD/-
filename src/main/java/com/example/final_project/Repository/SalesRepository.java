package com.example.final_project.Repository;

import com.example.final_project.Model.Accountant;
import com.example.final_project.Model.Branch;
import com.example.final_project.Model.Business;
import com.example.final_project.Model.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SalesRepository extends JpaRepository<Sales,Integer> {

    Sales findSalesById(Integer id);

    List<Sales> findSalesByBranch(Branch branch);


    @Query("select s from Sales s where s.sale_invoice=?1 and s.counterBox.accountant.id=?2")
    Sales findSalesByIdAndCounterBox_Accountant(Integer sales_invoice, Integer accId );


    @Query("select s from Sales s where s.branch.business.id=?1 and s.saleDate between ?2 and ?3")
    List<Sales> findSalesByBranch_BusinessAndSaleDateBetween(Business branchBusiness, LocalDateTime saleDateAfter, LocalDateTime saleDateBefore);



}
