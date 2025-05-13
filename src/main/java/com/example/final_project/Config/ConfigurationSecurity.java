package com.example.final_project.Config;

import com.example.final_project.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class ConfigurationSecurity {

    private final MyUserDetailsService myUserDetailsService;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){

        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(myUserDetailsService);
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());

        return authenticationProvider;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers ("/api/v1/tax-report/print/{reportId}").permitAll()
                .requestMatchers("/api/v1/business/add-business",
                        "/api/v1/branch/add/",
                        "api/v1/tax-payer/add-accountant/",
                        "api/v1/accountant/assign-accountant-to-branch/accountant/").hasAuthority("TAXPAYER")
                .requestMatchers("/api/v1/product/add/").hasAuthority("ACCOUNTANT")
                //   AUDITOR tax report
//                .requestMatchers(
//                        "/api/v1/taxReports/add",
//                        "/api/v1/taxReports/get",
//                        "/api/v1/taxReports/update",
//                        "/api/v1/taxReports/delete")
//                .hasAuthority("AUDITOR")
//
//                .requestMatchers( "/api/v1/taxReports/apply-penalty/**",
//                        "/api/v1/taxReports/apply-2month-penalty/**",
//                        "/api/v1/taxReports/apply-legal-action/**",
//                        "/api/v1/taxReports/due-payment",
//                        "/api/v1/taxReports/reports/**",
//                        "/api/v1/taxReports/report-count/**",
//                        "/api/v1/taxReports/approval-rate/**",
//                        "/api/v1/taxReports/bulk-approve/**",
//                        "/api/v1/taxReports/latest-report",
//                        "/api/v1/taxReports/unapproved",
//                        "/api/v1/taxReports/*/payment-status",
//                        "/api/v1/auditor/create-tax-report/**",
//                        "/api/v1/auditor/activate-business/**",
//                        "/api/v1/auditor/approve-tax-report/**",
//                        "/api/v1/auditor/reject-tax-report/**"
//                ).hasRole("AUDITOR")
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutUrl("/api/v1/user/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();

        return httpSecurity.build();
    }


}
