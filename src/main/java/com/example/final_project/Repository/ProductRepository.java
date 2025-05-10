package com.example.final_project.Repository;

import com.example.final_project.Model.Branch;
import com.example.final_project.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    Product findProductById(Integer id);

    @Query("select p from Product p where p.branch.id=?1")
    List<Product> findAllByBranchId(Integer branchId);

    Product findProductByBarcode(String barcode);

}
