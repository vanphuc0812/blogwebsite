package com.example.blogwebsite.blogpost.boundary;

import com.example.blogwebsite.blogpost.dto.BlogDTO;
import com.example.blogwebsite.blogpost.dto.BlogSaveDTO;
import com.example.blogwebsite.blogpost.dto.BlogUpdateDTO;
import com.example.blogwebsite.blogpost.service.BlogService;
import com.example.blogwebsite.common.util.ResponseUtil;
import com.example.blogwebsite.security.authorization.PlogOperation;
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

    @PlogOperation(name = "FetchOperation")
    @GetMapping("/GetBlogById")
    public Object getBlogById(@RequestParam UUID id) {
        return ResponseUtil.get(
                blogService.findBlogById(id)
                , HttpStatus.OK
        );
    }

    @PlogOperation(name = "FetchOperation")
    @GetMapping("/GetAllBlogs")
    public Object getAllBlogs() {
        return ResponseUtil.get(
                blogService.findAllDto(BlogDTO.class)
                , HttpStatus.OK
        );
    }

    @PlogOperation(name = "FetchOperation")
    @GetMapping("/GetAllBlogsPageable")
    public Object getAllBlogsPageable(Pageable page) {
        return ResponseUtil.get(
                blogService.findAllDto(page, BlogDTO.class)
                , HttpStatus.OK
        );
    }

    @PlogOperation(name = "FetchOperation")
    @GetMapping("/SearchBlogs")
    public Object searchBlogs(@RequestParam String keyword, @RequestParam String type) {
        return ResponseUtil.get(
                blogService.searchBlogs(keyword, type)
                , HttpStatus.OK
        );
    }

    @PlogOperation(name = "FetchOperation")
    @GetMapping("/GetBlogsByUsername")
    public Object getBlogsByUser(@RequestParam String username) {
        return ResponseUtil.get(
                blogService.findBlogsByUsername(username)
                , HttpStatus.OK
        );
    }

    @PlogOperation(name = "EditOperation")
    @PostMapping("/SaveBlog")
    public Object saveBlog(@RequestBody @Valid BlogSaveDTO blogDTO) {
        return ResponseUtil.get(blogService.save(blogDTO), HttpStatus.CREATED);
    }

    @PlogOperation(name = "EditOperation")
    @PostMapping("/SaveBlogs")
    public Object saveBlogs(@RequestBody @Valid List<BlogSaveDTO> blogDTOs) {
        return ResponseUtil.get(blogService.saveMultiple(blogDTOs), HttpStatus.CREATED);
    }

    @PlogOperation(name = "EditOperation")
    @DeleteMapping("/DeleteBlog")
    public Object deleteBlog(@RequestParam UUID blogID) {
        blogService.deleteBlog(blogID);
        return HttpStatus.OK;

    }

    @PlogOperation(name = "EditOperation")
    @PutMapping("/UpdateBlog")
    public Object updateBlog(@RequestBody BlogUpdateDTO blogDTO) {
        return ResponseUtil.get(
                blogService.updateBlog(blogDTO)
                , HttpStatus.OK
        );
    }
}
