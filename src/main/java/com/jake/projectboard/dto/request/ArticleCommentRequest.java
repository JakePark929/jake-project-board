package com.jake.projectboard.dto.request;

import com.jake.projectboard.dto.ArticleCommentDto;
import com.jake.projectboard.dto.UserAccountDto;

/**
 * A DTO for the {@link com.jake.projectboard.domain.ArticleComment} entity
 */
public record ArticleCommentRequest(Long articleId, String content) {
    public static ArticleCommentRequest of(Long articleId, String content) {
        return new ArticleCommentRequest(articleId, content);
    }

    public ArticleCommentDto toDto(UserAccountDto userAccountDto) {
        return ArticleCommentDto.of(
                articleId,
                userAccountDto,
                content
        );
    }
}
