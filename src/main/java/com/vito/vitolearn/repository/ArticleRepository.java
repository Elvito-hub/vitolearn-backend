package com.vito.vitolearn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vito.vitolearn.domain.Article;
import com.vito.vitolearn.domain.Community;

public interface ArticleRepository extends JpaRepository<Article,Long> {
    @Query("select s from Article s where s.community=:community")
    List<Article> findByCommunityId(@Param("community") Community cm);

    @Query("select s from Article s where s.title LIKE ?1%")
    List<Article> searchByName(String title);
}
