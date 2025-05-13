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
    public DaoAuthenticationProvider daoAuthenticationProvider() {

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
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
                .requestMatchers(
                        "/api/v1/tax-payer/tax-payer-register")
                .permitAll()
                .requestMatchers("/api/v1/user/update")
                .hasAnyAuthority("ADMIN","AUDITOR","TAXPAYER","ACCOUNTANT")
                .requestMatchers(
                        "/api/v1/business/add-business",
                        "/api/v1/branch/add/",
                        "/api/v1/tax-payer/add-accountant/",
                        "/api/v1/accountant/assign-accountant-to-branch/accountant/",
                        "/api/v1/moyasar-payment/pay-tax/tax-report/{{taxReportId}}",
                        "/api/v1/tax-payer/get-all-tax-payers/{{taxPayerId}}",
                        "/api/v1/tax-payer/tax-payer-register",
                        "/api/v1/tax-payer/update/{{taxPayerId}}",
                        "/api/v1/tax-payer/delete/{{taxPayerId}}",
                        "/api/v1/tax-payer/add-accountant/{{taxPayerID}}/{{branchId}}",
                        "/api/v1/tax-payer/activate-accountant/tax-payer/{{taxPayerId}}/accountant/{{accountantId}}",
                        "/api/v1/tax-payer/de-activate-accountant/tax-payer/{{taxPayerId}}/accountant/{{accountantId}}",
                        "/api/v1/tax-payer/de-activate-accountant/tax-payer/{{taxPayerId}}/accountant/{{accountantId}}",
                        "/api/v1/tax-payer/taxpayers/{{taxPayerId}}/accountants",
                        "/api/v1/tax-payer/block-inactive-accountant/{{taxPayerId}}/{{accountantId}}",
                        "/api/v1/business/get-all-business/{{auditId}}",
                        "/api/v1/business/get-my-business/{{taxPayerId}}/{{bId}}",
                        "/api/v1/business/get-number-of-branches/{{taxPayerId}}/{{bI}}",
                        "/api/v1/business/get-my-businesses/{{taxPayerId}}",
                        "/api/v1/business/add-business/{{taxPayerId}}",
                        "/api/v1/business/update/{{taxPayerId}}/{{businessId}}",
                        "/api/v1/business/delete/{{taxPayerId}}/{{businessId}}",
                        "/api/v1/business/sales-business/{{taxPayerId}}/{{businessId}}",
                        "/api/v1/business/business-revenue/{{taxPayerId}}/{{businessId}}")  //24
                .hasAuthority("TAXPAYER")
                .requestMatchers(
                        "/api/v1/product/add/",
                        "/api/v1/accountant/get-accountant-by-branch/{{branchId}}",
                        "/api/v1/accountant/get-accountant-by-business/{{businessId}}",
                        "/api/v1/accountant/assign-accountant-to-branch/accountant/{{accountantId}}/{{branchId}}",
                        "/api/v1/accountant/update/{{id}}",
                        "/api/v1/accountant/delete/{{id}}",
                        "/api/v1/accountant/restock-product/accountant/{{accountantId}}/product/{{productId}}/amount/{{amount}}",
                        "/api/v1/counterbox/open/{{boxId}}/api/v1/counterbox/close/{{boxId}}",
                        "/api/v1/counterbox/create",
                        "/api/v1/counterbox/get",
                        "/api/v1/counterbox/get/{{id}}",
                        "/api/v1/counterbox/update/{{id}}",
                        "/api/v1/counterbox/delete/{{id}}",
                        "/api/v1/counterbox/close-opened-counter-box")  // 14
                .hasAuthority("ACCOUNTANT")
                .requestMatchers(
                        "/api/v1/taxReports/add",
                        "/api/v1/taxReports/get",
                        "/api/v1/taxReports/update",
                        "/api/v1/taxReports/delete",
                        "/api/v1/taxReports/apply-penalty/**",
                        "/api/v1/taxReports/apply-2month-penalty/**",
                        "/api/v1/taxReports/apply-legal-action/**",
                        "/api/v1/taxReports/due-payment",
                        "/api/v1/taxReports/reports/**",
                        "/api/v1/taxReports/report-count/**",
                        "/api/v1/taxReports/approval-rate/**",
                        "/api/v1/taxReports/bulk-approve/**",
                        "/api/v1/taxReports/latest-report",
                        "/api/v1/taxReports/unapproved",
                        "/api/v1/taxReports/*/payment-status",
                        "/api/v1/auditor/create-tax-report/**",
                        "/api/v1/auditor/activate-business/**",
                        "/api/v1/auditor/approve-tax-report/**",
                        "/api/v1/auditor/reject-tax-report/**") //19
                .hasAuthority("AUDITOR")
                .requestMatchers(
                        "/api/v1/taxReports/reports/{{auditorId}}",
                        "/api/v1/taxReports/approval-rate/{{auditorId}}")
                .hasAuthority("ADMIN")
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
