package com.jake.projectboard.service;

import com.jake.projectboard.domain.type.SearchType;
import com.jake.projectboard.dto.ArticleDto;
import com.jake.projectboard.dto.ArticleWithCommentsDto;
import com.jake.projectboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor // 필수 필드 생성자 자동생성
@Transactional
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
//    public List<ArticleDto> searchArticles(SearchType title, String search_keyword) {
    public Page<ArticleDto> searchArticles(SearchType title, String searchKeyword, Pageable pageable) {
//        return List.of(
//               ArticleDto.of(LocalDateTime.now(), "Jake","새 글","")
//        );
//        return articleRepository.findAll()
//        return  List.of();
        return  Page.empty();
    }

    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticle(Long articleId) {
        return null;
    }

    public void saveArticle(ArticleDto dto) {
    }

    public void updateArticle(ArticleDto dto) {
    }

    public void deleteArticle(long articleId) {
    }
}
