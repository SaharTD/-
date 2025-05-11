package com.example.final_project;

import com.example.final_project.Model.Auditor;
import com.example.final_project.Model.TaxReports;
import com.example.final_project.Repository.AuditorRepository;
import com.example.final_project.Repository.TaxReportsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TaxReportsRepositoryTest {


    @Autowired
    TaxReportsRepository taxReportsRepository;

    @Autowired
    AuditorRepository auditorRepository;

    Auditor auditor;
    TaxReports report1, report2, report3;

    @BeforeEach
    void setUp() {
        auditor = new Auditor(null,"ahmed",null,null);
        auditorRepository.save(auditor);

        report1 = new TaxReports(null, 1000.0, LocalDateTime.now().minusDays(5), LocalDateTime.now().minusDays(3), LocalDate.now(), "Approved", null, null, auditor, null);
        report2 = new TaxReports(null, 2000.0, LocalDateTime.now().minusDays(10), LocalDateTime.now().minusDays(8), LocalDate.now(), "Pending", null, null, auditor, null);
        report3 = new TaxReports(null, 1500.0, LocalDateTime.now().minusDays(15), LocalDateTime.now().minusDays(12), null, "Rejected", null, null, auditor, null);

        taxReportsRepository.saveAll(List.of(report1, report2, report3));
    }






}
