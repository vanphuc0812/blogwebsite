package com.example.blogwebsite.blogpost.repository;

import com.example.blogwebsite.blogpost.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BlogRepository extends JpaRepository<Blog, UUID> {
    @Query("""
               SELECT p FROM Blog p
               WHERE p.title ILIKE CONCAT ('%',:query,'%') OR p.content ILIKE CONCAT ('%',:query,'%')
               ORDER BY
                 CASE
                   WHEN p.title ILIKE CONCAT('%', :query, '%') THEN 1
                   ELSE 2
                 END,
                 p.title ASC
            """)
    List<Blog> searchBlogPosts(String query);

    @Query("""
               SELECT p FROM Blog p
               WHERE p.title ILIKE CONCAT ('%',:query,'%') OR p.content ILIKE CONCAT ('%',:query,'%')
               ORDER BY
                 CASE
                   WHEN p.title ILIKE CONCAT('%', :query, '%') THEN 1
                   ELSE 2
                 END,
                 p.title ASC
               LIMIT 10
            """)
    List<Blog> search10BlogPosts(String query);

    @Query("SELECT p FROM Blog p WHERE p.user.username = :username")
    List<Blog> searchBlogPostByUsername(String username);

    Optional<Blog> findByTitle(String title);
}
