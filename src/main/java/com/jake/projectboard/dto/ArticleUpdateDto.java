package com.jake.projectboard.dto;

/**
 * A DTO for the {@link com.jake.projectboard.domain.Article} entity
 */
public record ArticleUpdateDto(
        String title,
        String content,
        String hashtag
//) implements Serializable { // 잭슨 프레임워크로 직렬화 하므로 필요없음
) {
    public static ArticleUpdateDto of(String title, String content, String hashtag) {
        return new ArticleUpdateDto(title, content, hashtag); // ctrl + space : suggestion
    }
}