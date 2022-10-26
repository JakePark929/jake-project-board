package com.jake.projectboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/articles")
@Controller
public class ArticleController {
    // 실제코드 에서는 Autowired 생략 가능
    // ctrl+shift+f12 전체화면
    // ctrl+shift+f9 recompile
    @GetMapping
    public String articles(ModelMap map) {
        map.addAttribute("articles", List.of());
        return "articles/index";
    }

    @GetMapping("/{articleId}")
    public String articles(@PathVariable Long articleId, ModelMap map) {
        map.addAttribute("article", "article"); // TODO: 구현할 때 실제 데이터를 넣어줘야 함
        map.addAttribute("articleComments", List.of());
        return "articles/detail";
    }
}
