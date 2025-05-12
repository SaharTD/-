//package com.example.final_project.Config;
//
//import com.example.final_project.Service.MyUserDetailsService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//
//@EnableWebSecurity
//@Configuration
//@RequiredArgsConstructor
//public class ConfigurationSecurity {
//
//    private final MyUserDetailsService myUserDetailsService;
//
//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider(){
//
//        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(myUserDetailsService);
//        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
//
//        return authenticationProvider;
//    }
//
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
//        httpSecurity.csrf().disable()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
//                .and()
//                .authenticationProvider(daoAuthenticationProvider())
//                .authorizeHttpRequests()
//                .requestMatchers("/api/v1/moyasar-payment/**","/api/v1/tax-payer/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .logout().logoutUrl("/api/v1/user/logout")
//                .deleteCookies("JSESSIONID")
//                .invalidateHttpSession(true)
//                .and()
//                .httpBasic();
//
//        return httpSecurity.build();
//
//        // requestMatcher for all users like (user / admin / customer...etc)
//        // without duplication
//
//    }
//
//}
