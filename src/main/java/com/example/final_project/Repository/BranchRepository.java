package com.example.final_project.Repository;

import com.example.final_project.Model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends JpaRepository<Branch,Integer> {

    Branch findBranchesById(Integer id);

    @Query("select b from Branch b where b.business.id=?1")
    Branch findBranchesByBusinessId(Integer businessId);
}
