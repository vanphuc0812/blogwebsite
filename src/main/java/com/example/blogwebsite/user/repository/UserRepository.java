package com.example.blogwebsite.user.repository;

import com.example.blogwebsite.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("select (count(u) > 0) from User u where u.username = ?1")
    boolean existsByUsername(String username);

    @Query("select (count(u) > 0) from User u where u.email = ?1")
    boolean existsByEmail(String email);

    void deleteByUsername(String username);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Query("""
            SELECT u FROM User u
            WHERE u.username LIKE CONCAT ('%',:query,'%') OR u.name LIKE CONCAT ('%',:query,'%')
            ORDER BY
                 CASE
                   WHEN u.name ILIKE CONCAT('%', :query, '%') THEN 1
                   ELSE 2
                 END,
                 u.name ASC
            """)
    List<User> searchUsers(String query);

    @Query("""
            SELECT u FROM User u
            WHERE u.username LIKE CONCAT ('%',:query,'%') OR u.name LIKE CONCAT ('%',:query,'%')
            ORDER BY
                 CASE
                   WHEN u.name ILIKE CONCAT('%', :query, '%') THEN 1
                   ELSE 2
                 END,
                 u.name ASC
            LIMIT 10
            """)
    List<User> search10Users(String query);
}