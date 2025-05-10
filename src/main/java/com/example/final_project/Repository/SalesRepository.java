package com.example.final_project.Repository;

import com.example.final_project.Model.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SalesRepository extends JpaRepository<Sales,Integer> {

    Sales findSalesById(Integer id);

    @Query("SELECT SUM(s.grand_amount) FROM Sales s")
    Double getTotalGrandAmount();

    @Query("SELECT s FROM Sales s WHERE (:branchId IS NULL OR s.branch.id = :branchId)")
    List<Sales> findSalesByBranch(@Param("branchId") Integer branchId);

    @Query(value = """
    SELECT s.*
    FROM sales s
    JOIN branch b ON s.branch_id = b.id
    JOIN business biz ON b.business_id = biz.id
    JOIN tax_payer t ON biz.tax_payer_id = t.id
    WHERE t.id = :taxPayerId
""", nativeQuery = true)
    List<Sales> findSalesByTaxPayerId(@Param("taxPayerId") Integer taxPayerId);


}
