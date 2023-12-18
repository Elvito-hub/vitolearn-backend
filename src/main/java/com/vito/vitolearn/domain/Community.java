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

import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "Community")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Community {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long communityId;
    
    private String name;
    private String dateCreated;
    private Boolean isApproved;
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "userId") // Assuming userId is the primary key in User class
    private User creator;
    
    private String coverPhotoPath;

    // This field is for handling file uploads in Spring Boot
    private transient MultipartFile coverPhoto;
}
