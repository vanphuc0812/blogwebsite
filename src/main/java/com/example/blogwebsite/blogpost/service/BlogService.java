package com.example.blogwebsite.blogpost.service;

import com.example.blogwebsite.blogpost.dto.BlogDTO;
import com.example.blogwebsite.blogpost.dto.BlogUpdateDTO;
import com.example.blogwebsite.blogpost.model.Blog;
import com.example.blogwebsite.blogpost.repository.BlogRepository;
import com.example.blogwebsite.common.exception.BWBusinessException;
import com.example.blogwebsite.common.service.GenericService;
import com.example.blogwebsite.common.util.BWMapper;
import com.example.blogwebsite.user.model.User;
import com.example.blogwebsite.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface BlogService extends GenericService<Blog, BlogDTO, UUID> {

    List<BlogDTO> findBlogsByUsername(String username);

    BlogDTO save(BlogDTO blogDTO);

    void deleteBlog(UUID blogID);

    BlogUpdateDTO updateBlog(BlogUpdateDTO blogDTO);
}

@Service
@Transactional
class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final BWMapper mapper;

    BlogServiceImpl(BlogRepository blogRepository, UserRepository userRepository, BWMapper mapper) {
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public JpaRepository<Blog, UUID> getRepository() {
        return blogRepository;
    }

    @Override
    public ModelMapper getMapper() {
        return mapper;
    }

    @Override
    public List<BlogDTO> findBlogsByUsername(String username) {
        return blogRepository.searchBlogPostByUsername(username)
                .stream()
                .map(blog -> mapper.map(blog, BlogDTO.class))
                .toList();
    }

    @Override
    public BlogDTO save(BlogDTO blogDTO) {
        User user = userRepository.findByUsername(blogDTO.getUsername()).orElseThrow(() ->
                new BWBusinessException("User is not existed")
        );

        Blog blog = mapper.map(blogDTO, Blog.class);
        blog.setUser(user);
        return mapper.map(blogRepository.save(blog), BlogDTO.class);
    }

    @Override
    public void deleteBlog(UUID blogID) {
        Blog blog = blogRepository.findById(blogID).orElseThrow(() -> new BWBusinessException("Could not find blog"));
        User user = blog.getUser();
        user.getBlogs().remove(blog);
        blogRepository.deleteById(blogID);
    }

    @Override
    public BlogUpdateDTO updateBlog(BlogUpdateDTO blogDTO) {
        Blog blog = blogRepository.findById(blogDTO.getId()).orElseThrow(() -> new BWBusinessException(""));
        if (blogDTO.getTitle() != null) blog.setTitle(blogDTO.getTitle());
        if (blogDTO.getContent() != null) blog.setContent(blogDTO.getContent());
        return mapper.map(blog, BlogUpdateDTO.class);
    }
}