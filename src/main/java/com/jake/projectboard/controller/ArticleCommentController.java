package com.jake.projectboard.controller;

import com.jake.projectboard.dto.UserAccountDto;
import com.jake.projectboard.dto.request.ArticleCommentRequest;
import com.jake.projectboard.dto.request.ArticleRequest;
import com.jake.projectboard.service.ArticleCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
public class ArticleCommentController {
    private final ArticleCommentService articleCommentService;

    // 댓글 추가
    @PostMapping ("/new")
    public String postNewArticleComment(ArticleCommentRequest articleCommentRequest) {
        // TODO: 인증 정보를 넣어줘야 한다.
        articleCommentService.saveArticleComment(articleCommentRequest.toDto(UserAccountDto.of(
                "jake", "pw", "jake@mail.com", "null", "null"
        )));
        return "redirect:/articles/" + articleCommentRequest.articleId();
    }

    // 댓글 삭제 - form 태그에서 delete 나 put 이 들어갈 상황은 없다!
    @PostMapping ("/{commentId}/delete")
    public String deleteArticleComment(@PathVariable Long commentId, Long articleId) {
        articleCommentService.deleteArticleComment(commentId);
        return "redirect:/articles/" + articleId;
    }
}
