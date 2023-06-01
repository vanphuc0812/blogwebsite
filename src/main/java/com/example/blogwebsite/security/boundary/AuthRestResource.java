package com.example.blogwebsite.security.boundary;

import com.example.blogwebsite.common.util.ResponseUtil;
import com.example.blogwebsite.security.dto.ChangePasswordDto;
import com.example.blogwebsite.security.dto.LoginDTO;
import com.example.blogwebsite.security.service.AuthService;
import com.example.blogwebsite.user.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;


@RestController
@RequestMapping("/auth")

public class AuthRestResource {
    private final AuthService authService;

    public AuthRestResource(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/validateToken")
    public Object validateToken(@RequestParam String token) {
        return ResponseUtil.get(
                authService.validateToken(token)
                , HttpStatus.OK
        );
    }

    @PostMapping("/login")
    public Object login(@RequestBody @Valid LoginDTO loginDTO) {
        return ResponseUtil.get(authService.login(loginDTO), HttpStatus.OK);
    }


    @PostMapping("/register")
    public Object registerCustomer(@RequestBody @Valid UserDTO userDTO) {
        return ResponseUtil.get(
                authService.registerCustomer(userDTO)
                , HttpStatus.OK
        );
    }


    @PostMapping("/forgotPassword")
    public Object forgotPassword(@RequestParam String email, String feHomePage, HttpServletRequest request) {
        return ResponseUtil.get(
                authService.forgotPassword(email, feHomePage, request.getScheme() + "://" + request.getHeader("Host"))
                , HttpStatus.OK
        );
    }


    @GetMapping("/resetPassword")
    public ResponseEntity<Object> resetPassword(@RequestParam String code, String redirectUri, HttpServletRequest request) throws URISyntaxException {
        if (authService.resetPassword(code)) {
            URI uri = new URI(redirectUri);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(uri);
            return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
        } else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @PostMapping("/changePassword")
    public Object changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        return ResponseUtil.get(
                authService.changePassword(changePasswordDto.getUsername(), changePasswordDto.getNewPassword(), changePasswordDto.getOldPassword())
                , HttpStatus.OK
        );
    }
}
