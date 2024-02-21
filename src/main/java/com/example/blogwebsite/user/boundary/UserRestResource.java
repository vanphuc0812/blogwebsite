package com.example.blogwebsite.user.boundary;

import com.example.blogwebsite.common.util.ResponseUtil;
import com.example.blogwebsite.security.authorization.PlogOperation;
import com.example.blogwebsite.user.dto.UserDTO;
import com.example.blogwebsite.user.dto.UserDtoWithoutPassword;
import com.example.blogwebsite.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/UsersManagement")

public class UserRestResource {
    private final UserService userService;

    public UserRestResource(UserService userService) {
        this.userService = userService;
    }

    @PlogOperation(name = "FetchOperation")
    @GetMapping("/GetAllUser")
    public Object findAllUser() {
        return ResponseUtil.get(
                userService.findAllDto(UserDTO.class)
                , HttpStatus.OK
        );
    }

    @PlogOperation(name = "FetchOperation")
    @GetMapping("/GetUserByUsername")
    public Object getUserByUsername(@RequestParam String username) {
        return ResponseUtil.get(
                userService.getUserByUsername(username)
                , HttpStatus.OK
        );
    }

    @PlogOperation(name = "FetchOperation")
    @GetMapping("/GetSimpleUserByUsername")
    public Object getSimpleUserByUsername(@RequestParam String username) {
        return ResponseUtil.get(
                userService.getSimpleUserByUsername(username)
                , HttpStatus.OK
        );
    }

    @PlogOperation(name = "FetchOperation")
    @GetMapping("/SearchUsers")
    public Object searchUser(@RequestParam String keyword, @RequestParam String type) {
        return ResponseUtil.get(userService.searchUsers(keyword, type), HttpStatus.OK);
    }

    @PlogOperation(name = "FetchOperation")
    @GetMapping("/GetAllBlogsByUsername")
    public Object getAllBlogsByUsername(@RequestParam("username") String username) {
        return ResponseUtil.get(
                userService.getAllBlogsByUsername(username)
                , HttpStatus.OK
        );
    }

    @PlogOperation(name = "FetchOperation")
    @GetMapping("/GetAllUserGroupByUsername")
    public Object findAllUserGroupUsername(@RequestParam("username") String username) {
        return ResponseUtil.get(
                userService.findAllUserGroupUsername(username)
                , HttpStatus.OK
        );
    }

    @PlogOperation(name = "EditOperation")
    @PostMapping(path = "/saveUserAvatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Object saveUserAvatar(@RequestParam("username") String username, @RequestPart("avatar") MultipartFile avatar, HttpServletRequest request) {
        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();
        return ResponseUtil.get(userService.saveUserAvatar(username, avatar, baseUrl), HttpStatus.CREATED);
    }

    @PlogOperation(name = "EditOperation")
    @PutMapping("/UpdateUser")
    public Object update(@RequestBody UserDtoWithoutPassword user) {
        return ResponseUtil.get(userService.update(user), HttpStatus.OK);
    }

    @PlogOperation(name = "EditOperation")
    @PutMapping("/FollowUser")
    public Object followUser(@RequestParam("rootUser") String rootUsername, @RequestParam("followedUser") String followedUsername) {
        return ResponseUtil.get(userService.followUser(rootUsername, followedUsername), HttpStatus.OK);
    }

    @PlogOperation(name = "EditOperation")
    @PutMapping("/UnfollowUser")
    public Object unfollowUser(@RequestParam("rootUser") String rootUsername, @RequestParam("unfollowedUser") String unfollowedUsername) {
        return ResponseUtil.get(userService.unfollowUser(rootUsername, unfollowedUsername), HttpStatus.OK);
    }

    @PlogOperation(name = "EditOperation")
    @DeleteMapping("/DeleteUser")
    public Object delete(@RequestParam("username") String username) {
        userService.deleteByUserName(username);
        return HttpStatus.OK;
    }
}
