package com.vito.vitolearn.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vito.vitolearn.domain.Article;
import com.vito.vitolearn.domain.ArticleVotes;
import com.vito.vitolearn.domain.User;
import com.vito.vitolearn.repository.ArticleRepository;
import com.vito.vitolearn.repository.ArticleVotesRepository;
import com.vito.vitolearn.service.ArticleVoteService;


@Service
public class ArticleVotesImpl implements ArticleVoteService {
    @Autowired
    private ArticleVotesRepository articleRepository;
    @Override
    public ArticleVotes addVote(ArticleVotes av) {
        return articleRepository.save(av);
    }
    @Override
    public void removeVote(ArticleVotes av){
         articleRepository.delete(av);
    }
    @Override
    public List<ArticleVotes> searchVote(Article av, User us){
        return articleRepository.findByArticleAndUser(av,us);
    }
    @Override
    public List<ArticleVotes> getArticleVotesByArticle(Article ar){
        return articleRepository.findAllByArticle(ar);
    }
}
