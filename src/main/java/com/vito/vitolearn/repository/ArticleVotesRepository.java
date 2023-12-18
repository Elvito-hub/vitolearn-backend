package com.vito.vitolearn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vito.vitolearn.domain.Article;
import com.vito.vitolearn.domain.ArticleVotes;
import com.vito.vitolearn.domain.Community;
import com.vito.vitolearn.domain.User;

public interface ArticleVotesRepository extends JpaRepository<ArticleVotes,Long> {
    @Query("SELECT av FROM ArticleVotes av WHERE av.article = :article")
    List<ArticleVotes> findAllByArticle(@Param("article") Article article);
    @Query("SELECT av FROM ArticleVotes av WHERE av.article = :article and av.creator= :creator")
    List<ArticleVotes> findByArticleAndUser(@Param("article") Article article, @Param("creator") User us);
}
