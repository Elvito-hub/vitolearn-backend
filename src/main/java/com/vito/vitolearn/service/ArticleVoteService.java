package com.vito.vitolearn.service;

import java.util.List;

import com.vito.vitolearn.domain.Article;
import com.vito.vitolearn.domain.ArticleVotes;
import com.vito.vitolearn.domain.User;

public interface ArticleVoteService {
    ArticleVotes addVote(ArticleVotes av);
    void removeVote(ArticleVotes av);
    List<ArticleVotes> searchVote(Article av, User us);
    List<ArticleVotes> getArticleVotesByArticle(Article ar);
}
