package com.example.blogwebsite.comment.service;

import com.example.blogwebsite.blogpost.model.Blog;
import com.example.blogwebsite.blogpost.repository.BlogRepository;
import com.example.blogwebsite.comment.dto.CommentDTO;
import com.example.blogwebsite.comment.dto.CommentSaveDTO;
import com.example.blogwebsite.comment.model.Comment;
import com.example.blogwebsite.comment.repository.CommentRepository;
import com.example.blogwebsite.common.exception.BWBusinessException;
import com.example.blogwebsite.common.service.GenericService;
import com.example.blogwebsite.common.util.BWMapper;
import com.example.blogwebsite.user.model.User;
import com.example.blogwebsite.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface CommentService extends GenericService<Comment, CommentDTO, UUID> {
    List<CommentDTO> getAllCommentsByBlogID(UUID blogId);

    List<CommentDTO> getNoParentCommentsByBlogID(UUID blogId);

    List<CommentDTO> getChilrenCommentsByParentID(UUID parentID);

    CommentDTO saveComment(CommentSaveDTO commentSaveDTO);

    void deleteComment(UUID commentID);
}

@Service
@Transactional
class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final BWMapper mapper;
    private final String BLOG_NOT_EXIST = "Blog is not exist";
    CommentServiceImpl(CommentRepository commentRepository, BlogRepository blogRepository, UserRepository userRepository, BWMapper mapper) {
        this.commentRepository = commentRepository;
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public JpaRepository<Comment, UUID> getRepository() {
        return commentRepository;
    }

    @Override
    public ModelMapper getMapper() {
        return mapper;
    }

    @Override
    public List<CommentDTO> getAllCommentsByBlogID(UUID blogId) {
        Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new BWBusinessException(BLOG_NOT_EXIST));

        return blog.getComments().stream().map(comment -> mapper.map(comment, CommentDTO.class)).toList();
    }

    @Override
    public List<CommentDTO> getNoParentCommentsByBlogID(UUID blogID) {
        Blog blog = blogRepository.findById(blogID).orElseThrow(() -> new BWBusinessException(BLOG_NOT_EXIST));
        return blog.getComments().stream()
                .filter(comment -> comment.getParent() == null)
                .map(comment -> mapper.map(comment, CommentDTO.class)).toList();
    }

    @Override
    public List<CommentDTO> getChilrenCommentsByParentID(UUID parentID) {
        return commentRepository.findByParent(parentID)
                .stream().map((comment) -> mapper.map(comment, CommentDTO.class)).toList();
    }

    @Override
    public CommentDTO saveComment(CommentSaveDTO commentSaveDTO) {
        Blog blog = blogRepository.findById(commentSaveDTO.getBlogID()).orElseThrow(() -> new BWBusinessException(BLOG_NOT_EXIST));
        User user = userRepository.findByUsername(commentSaveDTO.getUsername()).orElseThrow(() -> new BWBusinessException("User is not existed"));

        Comment comment = mapper.map(commentSaveDTO, Comment.class);
        comment.setUser(user);
        comment.setBlog(blog);
        if (commentSaveDTO.getParent() != null) {

            Comment parentComment = commentRepository.findById(commentSaveDTO.getParent()).orElseThrow(() -> new BWBusinessException("Parent comment is not existed"));
            comment.setParent(parentComment.getId());

        }

        blog.getComments().add(comment);

        return mapper.map(comment, CommentDTO.class);
    }

    @Override
    public void deleteComment(UUID commentID) {
        Comment comment = commentRepository.findById(commentID)
                .orElseThrow(() ->
                        new BWBusinessException("Comment is not existed.")
                );
        comment.getBlog().getComments().remove(comment);

        commentRepository.findByParent(commentID).forEach(children -> commentRepository.deleteById(children.getId()));
        commentRepository.deleteById(commentID);
    }
}