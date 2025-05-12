package com.example.final_project.Service;


import com.example.final_project.Api.ApiException;
import com.example.final_project.Model.User;
import com.example.final_project.Repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final MyUserRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = authRepository.findUserByUsername(username);
        if (user==null)
            throw new ApiException("wrong username or password"); //must be like this because security by agreement
        return user;
    }

}
