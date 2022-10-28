package com.jake.projectboard.service;

import com.jake.projectboard.domain.type.SearchType;
import com.jake.projectboard.dto.ArticleDto;
import com.jake.projectboard.dto.ArticleUpdateDto;
import com.jake.projectboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor // 필수 필드 생성자 자동생성
@Transactional
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
//    public List<ArticleDto> searchArticles(SearchType title, String search_keyword) {
    public Page<ArticleDto> searchArticles(SearchType title, String search_keyword) {
//        return List.of(
//               ArticleDto.of(LocalDateTime.now(), "Jake","새 글","")
//        );
//        return articleRepository.findAll()
//        return  List.of();
        return  Page.empty();
    }

    @Transactional(readOnly = true)
    public ArticleDto searchArticle(long l) {
        return null;
    }

    public void saveArticle(ArticleDto dto) {
    }

    public void updateArticle(long articleId, ArticleUpdateDto dto) {
    }

    public void deleteArticle(long articleId) {
    }
}
