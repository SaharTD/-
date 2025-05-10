package com.example.final_project.Repository;

import com.example.final_project.Model.Accountant;
import com.example.final_project.Model.Branch;
import com.example.final_project.Model.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountantRepository extends JpaRepository<Accountant,Integer>{

    Accountant findAccountantById(Integer id);

    List<Accountant> findAccountantByBranch(Branch branch);
    List<Accountant> findAccountantByBusiness(Business business);
    List<Accountant> findAccountantByBusinessAndBranch(Business business, Branch branch);


}
