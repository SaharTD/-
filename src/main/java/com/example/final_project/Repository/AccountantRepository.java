package com.example.final_project.Repository;

import com.example.final_project.Model.Accountant;
import com.example.final_project.Model.Branch;
import com.example.final_project.Model.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountantRepository extends JpaRepository<Accountant,Integer>{

    Accountant findAccountantById(Integer id);

    List<Accountant> findAccountantByBranch(Branch branch);
    List<Accountant> findAccountantByBusiness(Business business);
    List<Accountant> findAccountantByBusinessAndBranch(Business business, Branch branch);


    @Query(value = """
    SELECT 
        t.id AS taxPayerId,
        t.commercial_registration AS commercialRegistration,
        a.employee_id AS employeeId,
        a.is_active AS isActive,
        b.id AS branchId
    FROM tax_payer t
    JOIN business biz ON t.id = biz.tax_payer_id
    JOIN branch b ON biz.id = b.business_id
    JOIN accountant a ON b.id = a.branch_id
    WHERE t.id = :taxPayerId
""", nativeQuery = true)
    List<Object[]> findAccountantsByTaxPayerId(@Param("taxPayerId") Integer taxPayerId);

}
