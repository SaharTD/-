package com.example.final_project;

import com.example.final_project.DTO.DTOAuditor;
import com.example.final_project.Model.Auditor;
import com.example.final_project.Model.User;
import com.example.final_project.Repository.AuditorRepository;
import com.example.final_project.Repository.MyUserRepository;
import com.example.final_project.Service.AuditorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuditorServiceTest{



    @InjectMocks
    AuditorService auditorService;

    @Mock
    AuditorRepository auditorRepository;

    @Mock
    MyUserRepository myUserRepository;

    User user;


    Auditor auditor1,auditor2,auditor3;

    DTOAuditor dtoAuditor;

    List<Auditor> auditors;

    @BeforeEach
    void setUP(){
        user=new User(null,"khalid","khalid1","Kk1234","khalidss@gmail.com","Auditor",null,null,null);
        auditor1 = new Auditor(null,"Aa",user,null);
        auditor2=new Auditor(null,"Kk",user,null);
        auditor3=new Auditor(null,"Bb",user,null);
        auditors=new ArrayList<>();
        auditors.add(auditor1);
        auditors.add(auditor2);
        auditors.add(auditor3);

        dtoAuditor=new DTOAuditor("ali","ali12","ali12","ali123","Aaa");

    }



//    @Test
//    public void getAllAuditors(){
//        when(myUserRepository.findUserById(user.getId())).thenReturn(user);
//        when(auditorRepository.findAuditorsById(user.getId())).thenReturn(auditor1);
//
//        List<Auditor> auditorList=auditorService.getAllAuditors();
//        Assertions.assertEquals(auditorList,auditorList);
//        verify(myUserRepository,times(1)).findUserById(user.getId());
//        verify(myUserRepository,times(1)).findUserById(user.getId());
//    }
//
//
//
//
//    @Test
//    public void addAuditor(){
//        when(myUserRepository.findUserById(user.getId())).thenReturn(user);
//
//        auditorService.addAuditor(user.getId(),auditor2);
//        verify(myUserRepository,times(1)).findUserById(user.getId());
//        verify(auditorRepository,times(1)).save(auditor2);
//
//    }


    @Test
    public void getAllAuditors_success(){
        when(myUserRepository.findUserByIdAndRole(user.getId(), "ADMIN")).thenReturn(user);
        when(auditorRepository.findAll()).thenReturn(auditors);

        List<Auditor> result = auditorService.getAllAuditors(user.getId());

        Assertions.assertEquals(3, result.size());
        Assertions.assertEquals("Aa", result.get(0).getSOCPA());
        verify(myUserRepository, times(1)).findUserByIdAndRole(user.getId(), "ADMIN");
        verify(auditorRepository, times(1)).findAll();
    }



    @Test
    public void addAuditor(){
        when(myUserRepository.findUserByIdAndRole(user.getId(), "ADMIN")).thenReturn(user);

        auditorService.addAuditor(user.getId(), dtoAuditor);

        verify(myUserRepository, times(1)).findUserByIdAndRole(user.getId(), "ADMIN");
        verify(auditorRepository, times(1)).save(any(Auditor.class));
    }



}
