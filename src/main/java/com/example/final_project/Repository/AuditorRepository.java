package com.example.final_project.Repository;

import com.example.final_project.Model.Auditor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditorRepository extends JpaRepository<Auditor,Integer> {

    Auditor findAuditorsById(Integer id);

}
