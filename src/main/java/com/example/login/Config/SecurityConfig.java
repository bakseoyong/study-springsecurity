package com.example.login.Config;

import com.example.login.Auth.SecurityHandler.*;
import com.example.login.SecurityHandler.*;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

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
                .expressionHandler(webSecurityExpressionHandler())

                .anyRequest().authenticated();

        http.formLogin() // 인증이 필요한 페이지가 있다면 redirect
                .loginPage("/login/form")
                .defaultSuccessUrl("/")
                .failureUrl("/login/form")
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/api/v1/login") //loginProcessingUrl은 로그인 진행 시 어떤 경로에서 인증 시스템을 진행할 것인지를 설정하는 메소드이다.
        //스프링에서는 기본 경로가 /login으로 설정되어 있으며 이 프로세스는 UserDetailsService로 향한다.
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

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_ADMIN > ROLE_STAFF \n ROLE_STAFF > ROLE_USER";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy());
        return expressionHandler;
    }

}
