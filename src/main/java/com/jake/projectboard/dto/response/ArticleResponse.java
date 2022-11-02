package com.jake.projectboard.dto.response;

import com.jake.projectboard.domain.Article;
import com.jake.projectboard.dto.ArticleDto;

import java.io.Serializable;
import java.time.LocalDateTime;

// Response의 장점 각 레이어를 분리 시킴으로서 각 레이어의 독립성 보장, 유연한 코드
// 컨트롤러는 response request, service는 dto와 domain을 다루게 됨
public record ArticleResponse(
        Long id,
        String title,
        String content,
        String hashtag,
        LocalDateTime createdAt,
        String email,
        String nickname
) {
    public static ArticleResponse of(Long id, String title, String content, String hashtag, LocalDateTime createdAt, String email, String nickname) {
        return new ArticleResponse(id, title, content, hashtag, createdAt, email, nickname);
    }

    public static ArticleResponse from(ArticleDto dto) {
        String nickname = dto.userAccountDto().nickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = dto.userAccountDto().userId();
        }

        return new ArticleResponse(
                dto.id(),
                dto.title(),
                dto.content(),
                dto.hashtag(),
                dto.createdAt(),
                dto.userAccountDto().email(),
                nickname
        );
    }
}
