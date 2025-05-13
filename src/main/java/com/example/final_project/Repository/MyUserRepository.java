package com.example.final_project.Repository;

import com.example.final_project.Model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser,Integer> {


    MyUser findUserByUsername(String username);

    MyUser findUserById(Integer id);

    MyUser findUserByIdAndRole(Integer id, String role);

}
