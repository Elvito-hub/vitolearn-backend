package com.vito.vitolearn.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ArticleVotes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleVotes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleVoteId;

    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "userId") // Assuming userId is the primary key in User class
    private User creator;

    @ManyToOne
    @JoinColumn(name = "article_id", referencedColumnName = "articleId") // Assuming userId is the primary key in User class
    private Article article;

    @Nullable // Add this annotation to allow null values
    private String comment;


    private String dateCreated;
    private Long vote;


}
