package com.example.blogwebsite.comment.repository;

import com.example.blogwebsite.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {

    @Query("SELECT cmt FROM Comment cmt WHERE cmt.parent = ?1")
    List<Comment> findByParent(UUID parentID);
}
