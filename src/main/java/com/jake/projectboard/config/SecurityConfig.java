package com.jake.projectboard.config;

import com.jake.projectboard.dto.UserAccountDto;
import com.jake.projectboard.dto.security.BoardPrincipal;
import com.jake.projectboard.repository.UserAccountRepository;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
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
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//                        .anyRequest().permitAll() // 전체 들어갈 수 있게 허용
//                        .antMatchers() // 1.5 이전 버전
                        .mvcMatchers(
                                HttpMethod.GET,
                                "/",
                                "/articles",
                                "/articles/search-hashtag"
                        ).permitAll() // ** 문법등을 사용하는 패턴 기능 제공 메소드
                        .anyRequest().authenticated()
                )
//                .formLogin();
                .formLogin().and()
                .logout()
                        .logoutSuccessUrl("/")
                        .and()
//                .csrf().disable() 가볍게 테스트용일 때는 시큐리티 기능을 생략해서 개발
                .build();
//        return http.build();
    } // 시큐리티 관리하에

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        // static resource, css - js - 제외할 수 있음
//        return (web) -> web.ignoring().antMatchers("/css");
//        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
//    } // 정적리소스 시큐리티 검사에서 제외

    @Bean
    public UserDetailsService userDetailsService(UserAccountRepository userAccountRepository) {
        return username -> userAccountRepository
                .findById(username)
                .map(UserAccountDto::from)
                .map(BoardPrincipal::from)
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다 - username: " + username));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
