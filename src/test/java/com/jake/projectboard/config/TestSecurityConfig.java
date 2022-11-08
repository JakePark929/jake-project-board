package com.jake.projectboard.config;

import com.jake.projectboard.domain.UserAccount;
import com.jake.projectboard.repository.UserAccountRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@Import(SecurityConfig.class)
public class TestSecurityConfig {
    @MockBean private UserAccountRepository userAccountRepository;

    @BeforeTestMethod // spring test를 할 때에만 인증테스트를 진행
    public void securitySetUp() {
        given(userAccountRepository.findById(anyString())).willReturn(Optional.of(UserAccount.of(
                "jakeTest",
                "pw",
                "jake-test@mail.com",
                "jake-test",
                "test memo"
        )));
    }
}
