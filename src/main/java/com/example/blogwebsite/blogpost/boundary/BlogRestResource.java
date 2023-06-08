package com.example.blogwebsite.blogpost.boundary;

import com.example.blogwebsite.blogpost.dto.BlogDTO;
import com.example.blogwebsite.blogpost.dto.BlogSaveDTO;
import com.example.blogwebsite.blogpost.dto.BlogUpdateDTO;
import com.example.blogwebsite.blogpost.service.BlogService;
import com.example.blogwebsite.common.util.ResponseUtil;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("/GetAllBlogsPageable")
    public Object getAllBlogsPageable(Pageable page) {
        return ResponseUtil.get(
                blogService.findAllDto(page, BlogDTO.class)
                , HttpStatus.OK
        );
    }

    @GetMapping("/SearchBlogs")
    public Object searchBlogs(@RequestParam String keyword, @RequestParam String type) {
        return ResponseUtil.get(
                blogService.searchBlogs(keyword, type)
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
    public Object saveBlog(@RequestBody @Valid BlogSaveDTO blogDTO) {
        return ResponseUtil.get(blogService.save(blogDTO), HttpStatus.CREATED);
    }

    @PostMapping("/SaveBlogs")
    public Object saveBlogs(@RequestBody @Valid List<BlogSaveDTO> blogDTOs) {
        return ResponseUtil.get(blogService.saveMultiple(blogDTOs), HttpStatus.CREATED);
    }

    @DeleteMapping("/DeleteBlog")
    public Object deleteBlog(@RequestParam UUID blogID) {
        blogService.deleteBlog(blogID);
        return HttpStatus.OK;

    }

    @PutMapping("/UpdateBlog")
    public Object updateBlog(@RequestBody BlogUpdateDTO blogDTO) {
        return ResponseUtil.get(
                blogService.updateBlog(blogDTO)
                , HttpStatus.OK
        );
    }
}
