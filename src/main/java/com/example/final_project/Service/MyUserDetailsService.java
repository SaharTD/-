package com.example.final_project.Service;


import com.example.spring_security.Api.ApiException;
import com.example.spring_security.Model.User;
import com.example.spring_security.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = authRepository.findUserByUsername(username);
        if (user==null)
            throw new ApiException("wrong username or password"); //must be like this because security by agreement

        return user;
    }
}
