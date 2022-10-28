package com.jake.projectboard.dto;

import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.jake.projectboard.domain.Article} entity
 */
public record ArticleDto(
        LocalDateTime createdAt,
        String createdBy,
        String title,
        String content,
        String hashtag
//) implements Serializable { // 잭슨 프레임워크로 직렬화 하므로 필요없음
) {
    public static ArticleDto of(LocalDateTime createdAt, String createdBy, String title, String content, String hashtag) {
        return new ArticleDto(createdAt, createdBy, title, content, hashtag); // ctrl + space : suggestion
    }
}