package com.example.final_project.Repository;

import com.example.final_project.Model.Accountant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountantRepository extends JpaRepository<Accountant,Integer>{

    Accountant findAccountantById(Integer id);

}
