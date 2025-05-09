package com.example.final_project.Repository;

import com.example.final_project.Model.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesRepository extends JpaRepository<Sales,Integer> {

    Sales findSalesById(Integer id);

    // NOW() + INTERVAL 7 DAY
    @Query("select s from Sales s where s.invoiceDate")
    List<Sales> findSalesByDateThreeMonthsBefore();
}
