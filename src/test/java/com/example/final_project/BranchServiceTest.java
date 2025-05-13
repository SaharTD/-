package com.example.final_project;

import com.example.final_project.Model.Branch;
import com.example.final_project.Model.Business;
import com.example.final_project.Repository.BranchRepository;
import com.example.final_project.Service.BranchService;
import com.example.final_project.Api.ApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BranchServiceTest {

    @InjectMocks
    BranchService branchService;

    @Mock
    BranchRepository branchRepository;

    Business business;
    Branch branch1, branch2;
    List<Branch> branchList;

    @BeforeEach
    void setUp() {
        business = new Business();
        business.setId(1);

        branch1 = new Branch();
        branch1.setId(1);
        branch1.setBusiness(business);

        branch2 = new Branch();
        branch2.setId(2);
        branch2.setBusiness(business);

        branchList = Arrays.asList(branch1, branch2);
    }

    @Test
    public void getBranchesByTaxPayerId_success() {
        when(branchRepository.findBranchesByBusinessTaxPayerId(business.getId())).thenReturn(branchList);

        List<Branch> result = branchService.getTaxPayerBranches(business.getId());

        Assertions.assertThat(result.size()).isEqualTo(2);
        verify(branchRepository, times(1)).findBranchesByBusinessTaxPayerId(business.getId());
    }

    @Test
    public void getBranchesByTaxPayerId_noBranches_throwException() {
        when(branchRepository.findBranchesByBusinessTaxPayerId(business.getId())).thenReturn(List.of());

        Assertions.assertThatThrownBy(() ->
                        branchService.getTaxPayerBranches(business.getId()))
                .isInstanceOf(ApiException.class)
                .hasMessage("no branches belong to this tax payer");

        verify(branchRepository, times(1)).findBranchesByBusinessTaxPayerId(business.getId());
    }
}
