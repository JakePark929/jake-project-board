package com.jake.projectboard.repository.querydsl;

import com.jake.projectboard.domain.Article;
import com.jake.projectboard.domain.QArticle;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ArticleRepositoryCustomImpl extends QuerydslRepositorySupport implements ArticleRepositoryCustom {

    public ArticleRepositoryCustomImpl() {
        super(Article.class);
    }

    @Override
    public List<String> findAllDistinctHashtags() {
        QArticle article = QArticle.article;

//         qrs 가 제공 해줌 from
//         jpql, qrs 는 from 부터
//        JPQLQuery<String> query = from(article)
        return from(article)
                .distinct()
                .select(article.hashtag)
                .where(article.hashtag.isNotNull())
                .fetch();

//        return query.fetch();
    }
}
