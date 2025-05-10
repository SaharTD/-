package com.example.final_project.Repository;

import com.example.final_project.Model.TaxReports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaxReportsRepository extends JpaRepository<TaxReports,Integer> {

    TaxReports findTaxReportsById(Integer id);

    List<TaxReports> findAllByPaymentDate(LocalDate paymentDate);

    List<TaxReports> findAllByPaymentDateIsNotNullAndStatusNot(String status);

    List<TaxReports> findAllByAuditorId(Integer auditorId);

    Long countByAuditorId(Integer auditorId);

    Long countByAuditorIdAndStatus(Integer auditorId, String status);

    List<TaxReports> findTopByAuditorIdOrderByEnd_dateDesc(Integer auditorId);


}
