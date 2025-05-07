package com.example.final_project.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TaxReports {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @PositiveOrZero(message = "Total tax must be zero or positive")
    private double totalTax;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime start_date;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime end_date;


    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate paymentDate;

    @Pattern(regexp = "Pendimg|Approved|Paid")
    private String status;


    @OneToOne
    @PrimaryKeyJoinColumn
    private Payment payment;


    @ManyToOne
    @JoinColumn(name = "business_id",referencedColumnName = "id")
    @JsonIgnore
    private Business business;

    @ManyToOne
    @JoinColumn(name = "audit_id",referencedColumnName = "id")
    @JsonIgnore
    private Audit audit;
}
