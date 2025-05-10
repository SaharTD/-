package com.example.final_project.Repository;

import com.example.final_project.Model.CounterBox;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CounterBoxRepository extends JpaRepository<CounterBox,Integer> {

    CounterBox findCounterBoxById(Integer id);
}
