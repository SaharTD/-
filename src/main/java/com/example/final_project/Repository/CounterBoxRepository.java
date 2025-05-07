package com.example.final_project.Repository;

import com.example.final_project.Model.CounterBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CounterBoxRepository extends JpaRepository<CounterBox,Integer> {
    CounterBox findCounterBoxById(Integer id);
}
