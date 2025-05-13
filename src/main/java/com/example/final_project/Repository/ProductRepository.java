package com.example.final_project.Repository;

import com.example.final_project.Model.Branch;
import com.example.final_project.Model.Business;
import com.example.final_project.Model.Product;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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

    Product findProductByName(String name);

    @Query("select p from Product p where p.branch.business.id=?1")
    List<Product> findProductByBusiness(Integer businessId);



    Product findByNameAndBranchId(@NotEmpty @Size(min = 4,max = 20,message = "product name length should be between 4-20") String name, Integer branchId);
}
