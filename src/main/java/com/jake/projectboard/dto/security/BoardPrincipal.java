package com.jake.projectboard.dto.security;

import com.jake.projectboard.dto.UserAccountDto;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public record BoardPrincipal(
        String username,
        String password,
        Collection<? extends GrantedAuthority> authorities,
        String email,
        String nickname,
        String memo
) implements UserDetails {

    public static BoardPrincipal of(String username,
                                    String password,
                                    String email,
                                    String nickname,
                                    String memo) {
        Set<RoleType> roleTypes = Set.of(RoleType.USER);

        return new BoardPrincipal(
                username,
                password,
                roleTypes.stream()
                        .map(RoleType::getName) // 이름을 불러옴
                        .map(SimpleGrantedAuthority::new) // 이미 주어진 권한정보 기본 구현체
                        .collect(Collectors.toUnmodifiableSet()),
                email,
                nickname,
                memo
        );
    }

    public static BoardPrincipal from(UserAccountDto dto) {
        return BoardPrincipal.of(
                dto.userId(),
                dto.userPassword(),
                dto.email(),
                dto.nickname(),
                dto.memo()
        );
    }

    public UserAccountDto toDto() {
        return UserAccountDto.of(
                username,
                password,
                email,
                nickname,
                memo
        );
    }

    @Override public String getUsername() { return username; }
    @Override public String getPassword() { return password; }
    // 권한의 기능을 따로 설정하지 않을 예정 로그인 하는 사용자가 어떤 권한을 가지고 있는지?
    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }

    @Override public boolean isAccountNonExpired() { return true; } // 실제로 만료됬는가?
    @Override public boolean isAccountNonLocked() { return true; } // Lock 이 됬는가?
    @Override public boolean isCredentialsNonExpired() { return true; } // 크리덴셜 기한 만료됬는가?
    @Override public boolean isEnabled() { return true; } // 활성화 되어 있는가?

    public enum RoleType {
        USER("ROLE_USER");
        @Getter private final String name;

        RoleType(String name) {
            this.name = name;
        }
    }
}
