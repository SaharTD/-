package com.example.final_project.ServiceTest;

import com.example.final_project.DTO.DTOAuditor;
import com.example.final_project.Model.Auditor;
import com.example.final_project.Model.MyUser;
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

    MyUser myUser;


    Auditor auditor1,auditor2,auditor3;

    DTOAuditor dtoAuditor;

    List<Auditor> auditors;

    @BeforeEach
    void setUP(){
        myUser =new MyUser(null,"khalid","khalid1","Kk1234","khalidss@gmail.com","Auditor",null,null,null);
        auditor1 = new Auditor(null,"Aa", myUser,null);
        auditor2=new Auditor(null,"Kk", myUser,null);
        auditor3=new Auditor(null,"Bb", myUser,null);
        auditors=new ArrayList<>();
        auditors.add(auditor1);
        auditors.add(auditor2);
        auditors.add(auditor3);

        dtoAuditor=new DTOAuditor("ali","ali12","ali12","ali123","Aaa");

    }






    @Test
    public void getAllAuditors_success(){
        when(myUserRepository.findUserByIdAndRole(myUser.getId(), "ADMIN")).thenReturn(myUser);
        when(auditorRepository.findAll()).thenReturn(auditors);

        List<Auditor> result = auditorService.getAllAuditors(myUser.getId());

        Assertions.assertEquals(3, result.size());
        Assertions.assertEquals("Aa", result.get(0).getSOCPA());
        verify(myUserRepository, times(1)).findUserByIdAndRole(myUser.getId(), "ADMIN");
        verify(auditorRepository, times(1)).findAll();
    }



    @Test
    public void addAuditor(){
        when(myUserRepository.findUserByIdAndRole(myUser.getId(), "ADMIN")).thenReturn(myUser);

        auditorService.addAuditor(myUser.getId(), dtoAuditor);

        verify(myUserRepository, times(1)).findUserByIdAndRole(myUser.getId(), "ADMIN");
        verify(auditorRepository, times(1)).save(any(Auditor.class));
    }



}
