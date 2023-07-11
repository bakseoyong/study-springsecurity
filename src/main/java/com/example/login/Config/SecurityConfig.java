package com.example.login.Config;

import com.example.login.SecurityHandler.*;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

    private final AuthenticationSuccess authenticationSuccess;
    private final AuthenticationFailure authenticationFailure;
    private final LogoutExecute logoutExecute;
    private final LogoutSuccess logoutSuccess;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/login/form")
                .defaultSuccessUrl("/")
                .failureUrl("/login/form")
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/api/v1/login")
                .successHandler(authenticationSuccess)
                .failureHandler(authenticationFailure)
                .permitAll();

        http.logout()
                .logoutUrl("/api/v1/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true) //세션 무효화
                .deleteCookies("JSESSIONID") //로그아웃 성공시 제거할 쿠키명
                .addLogoutHandler(logoutExecute)
                .logoutSuccessHandler(logoutSuccess); //로그아웃 성공 후 핸들러

        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);


        return http.build();
    }

}
