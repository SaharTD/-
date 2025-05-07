package com.example.final_project.Service;

import com.example.final_project.Api.ApiException;
import com.example.final_project.Model.Auditor;
import com.example.final_project.Model.DTOAuditor;
import com.example.final_project.Repository.AuditorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditorService {

    private final AuditorRepository auditorRepository;

    public List<Auditor> getAllAuditors(){
        return auditorRepository.findAll();
    }

    public void addAuditor(DTOAuditor dtoAuditor){
        User user = new User();
        user.setName(dtoAuditor.getName());
        user.setUsername(dtoAuditor.getUsername());
        user.setPassword(dtoAuditor.getPassword());
        user.setEmail(dtoAuditor.getEmail());
        user.setRole("auditor");
        Auditor auditor = new Auditor(null, dtoAuditor.getSOCPA(), user);
        user.getAuditor().add(auditor);
        userRepository.save(user);
        auditorRepository.save(auditor);
    }

    public void updateAuditor(Integer auditorId, DTOAuditor dtoAuditor){
        User user = userRepository.findUserById(auditorId);
        if (user==null)
            throw new ApiException("auditor not found");
        Auditor auditor = auditorRepository.findAuditorsById(auditorId);
        user.setName(dtoAuditor.getName());
        user.setUsername(dtoAuditor.getUsername());
        user.setPassword(dtoAuditor.getPassword());
        user.setEmail(dtoAuditor.getEmail());
        auditor.setSOCPA(dtoAuditor.getSOCPA());

        userRepository.save(user);
        auditorRepository.save(auditor);
    }


    public void deleteAuditor(Integer auditorId){
        User user = userRepository.findUserById(auditorId);
        if (user==null)
            throw new ApiException("auditor not found");
        userRepository.delete(user);
//        auditorRepository.delete(auditor);
    }

}
