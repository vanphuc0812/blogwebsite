package com.example.blogwebsite.user.boundary;

import com.example.blogwebsite.common.util.ResponseUtil;
import com.example.blogwebsite.user.dto.UserDTO;
import com.example.blogwebsite.user.dto.UserDtoWithoutPassword;
import com.example.blogwebsite.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/UsersManagement")

public class UserRestResource {
    private final UserService userService;

    public UserRestResource(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/GetAllUser")
    public Object findAllUser() {
        return ResponseUtil.get(
                userService.findAllDto(UserDTO.class)
                , HttpStatus.OK
        );
    }


    @GetMapping("/GetUserByUsername")
    public Object getUserByUsername(@RequestParam String username) {
        return ResponseUtil.get(
                userService.getUserByUsername(username)
                , HttpStatus.OK
        );
    }


    @GetMapping("/common/Search")
    public Object searchUser(@RequestParam("query") String query) {
        return ResponseUtil.get(userService.searchUsers(query), HttpStatus.OK);
    }


    @GetMapping("/GetAllUserGroupByUsername")
    public Object findAllUserGroupUsername(@RequestParam("username") String username) {
        return ResponseUtil.get(
                userService.findAllUserGroupUsername(username)
                , HttpStatus.OK
        );
    }


    @PostMapping("/SaveUser")
    public Object saveUser(@RequestBody @Valid UserDTO userDTO) {
        return ResponseUtil.get(
                userService.createUser(userDTO)
                , HttpStatus.OK
        );
    }

    @PostMapping("/SaveUsers")
    public Object saveUsers(@RequestBody @Valid List<UserDTO> userDTOs) {
        return ResponseUtil.get(
                userService.createUsers(userDTOs)
                , HttpStatus.OK
        );
    }

    @PostMapping(path = "/saveUserAvatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Object saveUserAvatar(@RequestParam("username") String username, @RequestPart("avatar") MultipartFile avatar, HttpServletRequest request) {
        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();
        return ResponseUtil.get(userService.saveUserAvatar(username, avatar, baseUrl), HttpStatus.CREATED);
    }


    @PutMapping("/UpdateUser")
    public Object update(@RequestBody UserDtoWithoutPassword user) {
        return ResponseUtil.get(userService.update(user), HttpStatus.OK);
    }


    @DeleteMapping("/DeleteUser")
    public Object delete(@RequestParam("username") String username) {
        userService.deleteByUserName(username);
        return HttpStatus.OK;
    }
}
