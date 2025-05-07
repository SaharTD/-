package com.example.final_project.Repository;

import com.example.final_project.Model.User;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyUserRepository extends JpaRepository<User,Integer> {


    User findUserByUsername(String username);

    User findUserById(Integer id);
}
