package com.vito.vitolearn.service;

import java.util.List;

import com.vito.vitolearn.domain.Article;
import com.vito.vitolearn.domain.Community;

public interface ArticleService {
    Article createArticle(Article ar);
    Article updateArticle(Article ar);
    Article searchArticle(Article ar);
    List<Article> articles();
    List<Article> getArticlesByCommunity(Community cm);
    List<Article> searchArticles(Article cm);

}
