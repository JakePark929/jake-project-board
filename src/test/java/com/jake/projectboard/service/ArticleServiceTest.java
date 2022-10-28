package com.jake.projectboard.service;

import com.jake.projectboard.domain.Article;
import com.jake.projectboard.domain.type.SearchType;
import com.jake.projectboard.dto.ArticleDto;
import com.jake.projectboard.dto.ArticleUpdateDto;
import com.jake.projectboard.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

// 스프링부트 컨텍스트에 의존 하지 않고 필요한 의존성만 Mocking하여 가볍게 테스트 하는 라이브러리 - Mockito
@DisplayName("비지니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {
    // 시스템 언더 테스트 - 테스트 대상
    @InjectMocks private ArticleService sut;

    @Mock private ArticleRepository articleRepository;

    /**
     * 검색 [O]
     * 각 게시글 페이지로 이동 [O]
     * 페이지네이션 [O]
     * 홈 버튼 -> 게시판 페이지로 리다이렉션 [O]
     * 정렬 기능 [O]
     */
    @DisplayName("게시글을 검색하면, 게시글 리스트를 반환한다. + 페이지네이션 - 정렬")
    @Test
    void givenSearchParameters_whenSearchingArticles_thenReturnsArticleList() {
        // Given

        // When
//        List<ArticleDto> articles = sut.searchArticles(SearchType.TITLE, "search keyword"); // 제목, 본문, ID, 닉네임, 해시태그 - 하나의 DTO로?
        Page<ArticleDto> articles = sut.searchArticles(SearchType.TITLE, "search keyword"); // 제목, 본문, ID, 닉네임, 해시태그 - 하나의 DTO로?

        // Then
        assertThat(articles).isNotNull(); // 실제로는 실패하는 테스트를 작성해야 함.. 필요로 하는..
    }

    @DisplayName("게시글을 조회하면, 게시글을 반환한다.")
    @Test
    void givenArticleId_whenSearchingArticle_thenReturnsArticle() {
        // Given

        // When
        ArticleDto articles = sut.searchArticle(1L); // articleId

        // Then
        assertThat(articles).isNotNull(); // 실제로는 실패하는 테스트를 작성해야 함.. 필요로 하는..
    }

    @DisplayName("게시글 정보를 입력하면, 게시글을 생성한다")
    @Test
    void givenArticleInfo_whenSavingArticle_thenSavesArticle() {
        // Given
//        ArticleDto dto = ArticleDto.of(LocalDateTime.now(), "Jake", "title", "content", "#java");
//        willDoNothing().given(articleRepository).save(any(Article.class)); //BDDMockito -> save호출에 대한 명시
        given(articleRepository.save(any(Article.class))).willReturn(null); //BDDMockito -> save호출에 대한 명시

        // When
        sut.saveArticle(ArticleDto.of(LocalDateTime.now(), "Jake", "title", "content", "#java"));

        // Then
        then(articleRepository).should().save(any(Article.class));
        // 소셔블 테스트 - DB 까지 내려가는 것
        // 솔리터리 테스트 -DB 까지는 안내려감
    }

    @DisplayName("게시글의 ID와 수정 정보를 입력하면, 게시글을 수정한다")
    @Test
    void givenArticleIdAndModifiedInfo_whenSavingArticle_thenUpdatesArticle() {
        // Given
//        ArticleDto dto = ArticleDto.of(LocalDateTime.now(), "Jake", "title", "content", "#java");
//        willDoNothing().given(articleRepository).save(any(Article.class)); //BDDMockito -> save호출에 대한 명시
        given(articleRepository.save(any(Article.class))).willReturn(null); //BDDMockito -> save호출에 대한 명시

        // When
        sut.updateArticle(1L, ArticleUpdateDto.of("title", "content", "#java"));

        // Then
        then(articleRepository).should().save(any(Article.class));
    }

    @DisplayName("게시글의 ID를 입력하면, 게시글을 삭제한다")
    @Test
    void givenArticleId_whenDeletingArticle_thenDeletesArticle() {
        // Given
        willDoNothing().given(articleRepository).delete(any(Article.class)); // 코드적 명시

        // When
        sut.deleteArticle(1L);

        // Then
        then(articleRepository).should().delete(any(Article.class));
    }
}
