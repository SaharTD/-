package com.example.final_project.Repository;

import com.example.final_project.Model.ItemSales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemSalesRepository extends JpaRepository<ItemSales,Integer> {

    List<ItemSales> findItemSalesById(Integer id);
}
