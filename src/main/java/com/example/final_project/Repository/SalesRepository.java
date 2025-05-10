package com.example.final_project.Repository;

import com.example.final_project.Model.Branch;
import com.example.final_project.Model.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesRepository extends JpaRepository<Sales,Integer> {

    Sales findSalesById(Integer id);

    List<Sales> findSalesByBranch(Branch branch);

}
