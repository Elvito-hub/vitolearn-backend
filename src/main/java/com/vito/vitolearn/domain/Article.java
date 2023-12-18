package com.vito.vitolearn.domain;

import jakarta.persistence.Column;
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
@Table(name = "Article")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleId;

    private String title;
    private String content;
    private String dateCreated;
    private String thumbnail;
    
    
    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "userId") // Assuming userId is the primary key in User class
    private User creator;
    
    @ManyToOne
    @JoinColumn(name = "community_id", referencedColumnName = "communityId") // Assuming communityId is the primary key in Community class
    private Community community;

    // Getters and setters
    // You can generate them using your IDE or manually write them for each field
}

