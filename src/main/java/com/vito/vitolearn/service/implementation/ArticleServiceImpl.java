package com.vito.vitolearn.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vito.vitolearn.domain.Article;
import com.vito.vitolearn.domain.Community;
import com.vito.vitolearn.repository.ArticleRepository;
import com.vito.vitolearn.service.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Article createArticle(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public Article updateArticle(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public Article searchArticle(Article article) {
        Optional<Article> optionalArticle = articleRepository.findById(article.getArticleId());
        return optionalArticle.orElse(null);
    }

    @Override
    public List<Article> articles() {
        return articleRepository.findAll();
    }

    @Override
    public List<Article> getArticlesByCommunity(Community cm){
        return articleRepository.findByCommunityId(cm);
    }
}
