package com.jake.projectboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;

// Spring Boot 2.7 부터의 변화
// Auto-Configuration jetbrain youtrack-jira
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//@EnableWebSecurity // 안넣어도됨 auto config
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        WebSecurityConfigurerAdapter 없어짐!

        return http
//                .authorizeRequests().antMatchers("/**").permitAll()
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // 전체 들어갈 수 있게 허용
//                .formLogin();
                .formLogin().and()
                .build();
//        return http.build();
    }
}
