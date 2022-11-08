package com.jake.projectboard.config;

import com.jake.projectboard.dto.security.BoardPrincipal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.util.Optional;

@EnableJpaAuditing // 자동 생성으로 넣어 줌
@Configuration // 각종 설정을 잡음
public class JpaConfig {
    @Bean
    public AuditorAware<String> auditorAware() {
//        return () -> Optional.of("jake"); // TODO: 스프링 시큐리티로 인증 기능을 붙이게 될 때, 수정예정
        return () -> Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
//                .map(x -> (BoardPrincipal) x) // 타입캐스팅
                .map(BoardPrincipal.class::cast)
                .map(BoardPrincipal::getUsername);
    }
}
