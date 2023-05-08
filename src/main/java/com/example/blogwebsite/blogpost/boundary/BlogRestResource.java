package com.example.blogwebsite.blogpost.boundary;

import com.example.blogwebsite.blogpost.dto.BlogDTO;
import com.example.blogwebsite.blogpost.service.BlogService;
import com.example.blogwebsite.common.util.ResponseUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/BlogsManagement")
public class BlogRestResource {
    private final BlogService blogService;

    public BlogRestResource(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/GetAllBlogs")
    public Object getAllBlogs() {
        return ResponseUtil.get(
                blogService.findAllDto(BlogDTO.class)
                , HttpStatus.OK
        );
    }

    @GetMapping("/GetBlogsByUsername")
    public Object getBlogsByUser(@RequestParam String username) {
        return ResponseUtil.get(
                blogService.findBlogsByUsername(username)
                , HttpStatus.OK
        );
    }

    @PostMapping("/SaveBlog")
    public Object saveBlog(@RequestBody @Valid BlogDTO blogDTO) {
        return ResponseUtil.get(blogService.save(blogDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/DeleteBlog")
    public Object deleteBlog(@RequestParam UUID blogID) {
        return ResponseUtil.get(
                blogService.removeBlog(blogID)
                , HttpStatus.OK
        );
    }
}
