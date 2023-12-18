package com.vito.vitolearn.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    //private String userName;
    private String password;
    private String role;
    @Enumerated(EnumType.STRING)
    private Egender gender;
    private String dob;
    private String dateJoined;
    @Column(nullable = true) // Adding nullable attribute to allow null values
    private String profilePic;
    private String phoneNumber;
}
