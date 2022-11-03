package com.jake.projectboard.service;

import com.jake.projectboard.domain.Article;
import com.jake.projectboard.domain.UserAccount;
import com.jake.projectboard.domain.type.SearchType;
import com.jake.projectboard.dto.ArticleDto;
import com.jake.projectboard.dto.ArticleWithCommentsDto;
import com.jake.projectboard.dto.UserAccountDto;
import com.jake.projectboard.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
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
    @DisplayName("검색어 없이 게시글을 검색하면, 게시글 페이지를 반환한다. + 페이지네이션 - 정렬")
    @Test
    void givenNoSearchParameters_whenSearchingArticles_thenReturnsArticlePage() {
        // Given
        Pageable pageable = Pageable.ofSize(20);
        given(articleRepository.findAll(pageable)).willReturn(Page.empty());

        // When
//        List<ArticleDto> articles = sut.searchArticles(SearchType.TITLE, "search keyword"); // 제목, 본문, ID, 닉네임, 해시태그 - 하나의 DTO로?
        Page<ArticleDto> articles = sut.searchArticles(null, null, pageable); // 제목, 본문, ID, 닉네임, 해시태그 - 하나의 DTO로?

        // Then
        assertThat(articles).isEmpty(); // 실제로는 실패하는 테스트를 작성해야 함.. 필요로 하는..
        then(articleRepository).should().findAll(pageable);
    }

    @DisplayName("검색어와 함께 게시글을 검색하면, 게시글 페이지를 반환한다.")
    @Test
    void givenSearchParameters_whenSearchingArticles_thenReturnsArticlePage() {
        // Given
        SearchType searchType = SearchType.TITLE;
        String searchKeyword = "title";
        Pageable pageable = Pageable.ofSize(20);
        given(articleRepository.findByTitleContaining(searchKeyword, pageable)).willReturn(Page.empty());

        // When
        Page<ArticleDto> articles = sut.searchArticles(searchType, searchKeyword, pageable);

        // Then
        assertThat(articles).isEmpty();
        then(articleRepository).should().findByTitleContaining(searchKeyword, pageable);
    }

    @DisplayName("검색어 없이 게시글을 해시태그 검색하면, 빈 페이지를 반환한다.")
    @Test
    void givenNoSearchParameters_whenSearchingArticlesViaHashtag_thenReturnsEmptyPage() {
        // Given
        Pageable pageable = Pageable.ofSize(20);

        // When
        Page<ArticleDto> articles = sut.searchArticlesViaHashtag(null, pageable);

        // Then
        assertThat(articles).isEqualTo(Page.empty(pageable));
        then(articleRepository).shouldHaveNoInteractions();
    }

    @DisplayName("게시글을 해시태그 검색하면, 게시글 페이지를 반환한다.")
    @Test
    void givenHashtag_whenSearchingArticlesViaHashtag_thenReturnsArticlesPage() {
        // Given
        String hashtag = "#java";
        Pageable pageable = Pageable.ofSize(20);
        given(articleRepository.findByHashtag(hashtag, pageable)).willReturn(Page.empty(pageable));

        // When
        Page<ArticleDto> articles = sut.searchArticlesViaHashtag(hashtag, pageable);

        // Then
        assertThat(articles).isEqualTo(Page.empty(pageable));
        then(articleRepository).should().findByHashtag(hashtag, pageable);
    }


    @DisplayName("게시글을 조회하면, 게시글을 반환한다.")
    @Test
    void givenArticleId_whenSearchingArticle_thenReturnsArticle() {
        // Given
        Long articleId = 1L;
        Article article = createArticle();

        given(articleRepository.findById(articleId)).willReturn(Optional.of(article));

        // When
//        ArticleDto articles = sut.searchArticle(1L); // articleId
        ArticleWithCommentsDto dto = sut.getArticle(articleId); // articleId

        // Then
//        assertThat(articles).isNotNull(); // 실제로는 실패하는 테스트를 작성해야 함.. 필요로 하는..
        assertThat(dto)
                .hasFieldOrPropertyWithValue("title", article.getTitle())
                .hasFieldOrPropertyWithValue("content", article.getContent())
                .hasFieldOrPropertyWithValue("hashtag", article.getHashtag());
        then(articleRepository).should().findById(articleId);
    }

    @DisplayName("없는 게시글을 조회하면, 예외를 던진다.")
    @Test
    void givenNonexistentArticleId_whenSearchingArticle_thenThrowsException() {
        // Given
        Long articleId = 0L;
        given(articleRepository.findById(articleId)).willReturn(Optional.empty());

        // When
        Throwable t = catchThrowable(() -> sut.getArticle(articleId));

        // Then
        assertThat(t)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("게시글이 없습니다 - articleId: " + articleId);
        then(articleRepository).should().findById(articleId);
    }

    @DisplayName("게시글 정보를 입력하면, 게시글을 생성한다")
    @Test
    void givenArticleInfo_whenSavingArticle_thenSavesArticle() {
        // Given
//        ArticleDto dto = ArticleDto.of(LocalDateTime.now(), "Jake", "title", "content", "#java");
//        willDoNothing().given(articleRepository).save(any(Article.class)); //BDDMockito -> save호출에 대한 명시
        ArticleDto dto = createArticleDto();
        given(articleRepository.save(any(Article.class))).willReturn(createArticle()); //BDDMockito -> save호출에 대한 명시
        

        // When
//        sut.saveArticle(ArticleDto.of(LocalDateTime.now(), "Jake", "title", "content", "#java"));
        sut.saveArticle(dto);

        // Then
        then(articleRepository).should().save(any(Article.class));
        // 소셔블 테스트 - DB 까지 내려가는 것
        // 솔리터리 테스트 -DB 까지는 안내려감
    }

    @DisplayName("게시글의 수정 정보를 입력하면, 게시글을 수정한다")
    @Test
    void givenModifiedInfo_whenUpdatingArticle_thenUpdatesArticle() {
        // Given
//        ArticleDto dto = ArticleDto.of(LocalDateTime.now(), "Jake", "title", "content", "#java");
//        willDoNothing().given(articleRepository).save(any(Article.class)); //BDDMockito -> save호출에 대한 명시
//        given(articleRepository.save(any(Article.class))).willReturn(null); //BDDMockito -> save호출에 대한 명시
        Article article = createArticle();
        ArticleDto dto = createArticleDto("새 타이틀", "새 내용", "springboot");
        // boot 2.7 부터 getOne 없어짐
        given(articleRepository.getReferenceById(dto.id())).willReturn(article);

        // When
//        sut.updateArticle(1L, ArticleUpdateDto.of("title", "content", "#java"));
        sut.updateArticle(dto);

        // Then
//        then(articleRepository).should().save(any(Article.class));
        assertThat(article)
                .hasFieldOrPropertyWithValue("title", dto.title())
                .hasFieldOrPropertyWithValue("content", dto.content())
                .hasFieldOrPropertyWithValue("hashtag", dto.hashtag());
        then(articleRepository).should().getReferenceById(dto.id());
    }

    @DisplayName("없는 게시글의 수정 정보를 입력하면, 경고 로그를 찍고 아무 것도 하지 않는다.")
    @Test
    void givenNonexistentArticleInfo_whenUpdatingArticle_thenLogsWarningAndDoesNothing() {
        // Given
        ArticleDto dto = createArticleDto("새 타이틀", "새 내용", "#springboot");
        given(articleRepository.getReferenceById(dto.id())).willThrow(EntityNotFoundException.class);

        // When
        sut.updateArticle(dto);

        // Then
        then(articleRepository).should().getReferenceById(dto.id());
    }

    @DisplayName("게시글의 ID를 입력하면, 게시글을 삭제한다")
    @Test
    void givenArticleId_whenDeletingArticle_thenDeletesArticle() {
        // Given
        Long articleId = 1L;
//        willDoNothing().given(articleRepository).delete(any(Article.class)); // 코드적 명시
        willDoNothing().given(articleRepository).deleteById(articleId); // 코드적 명시

        // When
        sut.deleteArticle(1L);

        // Then
//        then(articleRepository).should().delete(any(Article.class));
        then(articleRepository).should().deleteById(articleId);
    }

    @DisplayName("게시글 수를 조회하면, 게시글 수를 반환한다")
    @Test
    void givenNothing_whenCountingArticles_thenReturnsArticleCount() {
        // Given
        long expected = 0L;
        given(articleRepository.count()).willReturn(expected);

        // When
        long actual = sut.getArticleCount();

        // Then
        assertThat(actual).isEqualTo(expected);
        then(articleRepository).should().count();
    }

    @DisplayName("해시태그를 조회하면, 유니크 해시태그 리스트를 반환한다")
    @Test
    void givenNothing_whenCalling_thenReturnsHashtags() {
        // Given
        List<String> expectedHashtags = List.of("#java", "#spring", "#boot");
        given(articleRepository.findAllDistinctHashtags()).willReturn(expectedHashtags);

        // When
        List<String> actualHashtags = sut.getHashtags();

        // Then
        assertThat(actualHashtags).isEqualTo(expectedHashtags);
        then(articleRepository).should().findAllDistinctHashtags();
    }

    // 픽스쳐
    private UserAccount createUserAccount() {
        return UserAccount.of(
                "jake",
                "password",
                "jake@email.com",
                "Jake",
                null
        );
    }

    private Article createArticle() {
        return Article.of(
                createUserAccount(),
                "title",
                "content",
                "#java"
        );
    }

    private ArticleDto createArticleDto() {
        return createArticleDto("title", "content", "#java");
    }

    private ArticleDto createArticleDto(String title, String content, String hashtag) {
        return ArticleDto.of(1L,
                createUserAccountDto(),
                title,
                content,
                hashtag,
                LocalDateTime.now(),
                "Jake",
                LocalDateTime.now(),
                "Jake");
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
//                1L,
                "jake",
                "password",
                "jake@mail.com",
                "Jake",
                "This is memo",
                LocalDateTime.now(),
                "jake",
                LocalDateTime.now(),
                "jake"
        );
    }
}
