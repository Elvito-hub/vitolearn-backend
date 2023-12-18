package com.vito.vitolearn.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vito.vitolearn.domain.User;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {
   @Query("SELECT u FROM User u WHERE u.userName = :username")
   Optional<User> findByUsername(String username);
   @Query("SELECT c FROM User c WHERE c.email = :email")
   List<User> findByEmail(@Param("email") String email);
}
