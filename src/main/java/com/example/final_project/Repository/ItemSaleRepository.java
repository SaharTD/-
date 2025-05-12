package com.example.final_project.Repository;

import com.example.final_project.Model.ItemSale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemSaleRepository extends JpaRepository<ItemSale,Integer> {

    List<ItemSale> findItemSaleBySalesId(Integer salesId);

    ItemSale findItemSaleById(Integer id);
}
