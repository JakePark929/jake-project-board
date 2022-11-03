package com.jake.projectboard.dto;

import com.jake.projectboard.domain.Article;
import com.jake.projectboard.domain.ArticleComment;
import com.jake.projectboard.domain.UserAccount;

import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.jake.projectboard.domain.ArticleComment} entity
 */
public record ArticleCommentDto(
        Long id,
        Long articleId,
        UserAccountDto userAccountDto,
        String content,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {
    // new 로 만드는데 영속화 되지 않은 객체를 entity에 만들어 넣기 전까지 null로 만들어 넣는 메소드
    public static ArticleCommentDto of(Long articleId,
                                       UserAccountDto userAccountDto,
                                       String content) {
        return new ArticleCommentDto(null, articleId, userAccountDto, content, null, null, null, null);
    }

    public static ArticleCommentDto of(Long id,
                             Long articleId,
                             UserAccountDto userAccountDto,
                             String content,
                             LocalDateTime createdAt,
                             String createdBy,
                             LocalDateTime modifiedAt,
                             String modifiedBy) {
        return new ArticleCommentDto(id, articleId, userAccountDto, content, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static ArticleCommentDto from(ArticleComment entity) {
        return new ArticleCommentDto(
                entity.getId(),
                entity.getArticle().getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    public ArticleComment toEntity(Article article, UserAccount userAccount) {
        return ArticleComment.of(
                article,
//                userAccountDto.toEntity(),
                userAccount,
                content
        );
    }
}