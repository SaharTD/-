package com.example.final_project.Repository;

import com.example.final_project.Model.Branch;
import com.example.final_project.Model.CounterBox;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CounterBoxRepository extends JpaRepository<CounterBox,Integer> {

    CounterBox findCounterBoxById(Integer id);

    List<CounterBox> findByCloseDatetimeIsNull();

    @Query("select c from CounterBox c where c.openDatetime + 11 hour <now() and c.status='Opened'")
    List<CounterBox> findCounterBoxByOpenDatetime();

    CounterBox findByBranch(Branch branch);

}
