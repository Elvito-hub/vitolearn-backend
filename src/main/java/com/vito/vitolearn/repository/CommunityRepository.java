package com.vito.vitolearn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vito.vitolearn.domain.Article;
import com.vito.vitolearn.domain.ArticleVotes;
import com.vito.vitolearn.domain.Community;

public interface CommunityRepository extends JpaRepository<Community,Long> {
    @Query("SELECT av FROM Community av WHERE av.isApproved = true")
    List<Community> findallApprovedCommunites();
}
