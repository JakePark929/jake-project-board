package com.jake.projectboard.dto;

import com.jake.projectboard.domain.Article;
import com.jake.projectboard.domain.UserAccount;

import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.jake.projectboard.domain.Article} entity
 */
public record ArticleDto(
        Long id,
        UserAccountDto userAccountDto,
        String title,
        String content,
        String hashtag,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
//) implements Serializable { // 잭슨 프레임워크로 직렬화 하므로 필요없음
) {
    public static ArticleDto of(UserAccountDto userAccountDto,
                                String title,
                                String content,
                                String hashtag) {
        return new ArticleDto(null, userAccountDto, title, content, hashtag, null, null, null, null);
    }
    public static ArticleDto of(Long id,
                                UserAccountDto userAccountDto,
                                String title,
                                String content,
                                String hashtag,
                                LocalDateTime createdAt,
                                String createdBy,
                                LocalDateTime modifiedAt,
                                String modifiedBy) {
        return new ArticleDto(id, userAccountDto, title, content, hashtag, createdAt, createdBy, modifiedAt, modifiedBy);
    }
    
    // article은 entity를 몰라도 됨..
    // Mapping 을 해줌
    public static ArticleDto from(Article entity) {
        return new ArticleDto(
                entity.getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getTitle(),
                entity.getContent(),
                entity.getHashtag(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

//    public Article toEntity() {
    public Article toEntity(UserAccount userAccount) {
        return Article.of(
//                userAccountDto.toEntity(),
                userAccount,
                title,
                content,
                hashtag
        );
    }
}