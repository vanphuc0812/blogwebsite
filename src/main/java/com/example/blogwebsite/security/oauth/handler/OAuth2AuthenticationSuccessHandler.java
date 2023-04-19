package com.example.blogwebsite.security.oauth.handler;

import com.example.blogwebsite.common.util.ResponseUtil;
import com.example.blogwebsite.common.util.TCHMapper;
import com.example.blogwebsite.security.jwt.JwtUtils;
import com.example.blogwebsite.security.oauth.user.UserPrinciple;
import com.example.blogwebsite.user.dto.UserDTOWithToken;
import com.example.blogwebsite.user.model.User;
import com.example.blogwebsite.user.repository.UserRepository;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@Slf4j
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final TCHMapper mapper;

    public OAuth2AuthenticationSuccessHandler(UserRepository userRepository, JwtUtils jwtUtils, TCHMapper mapper) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.mapper = mapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        UserPrinciple oauthUser = (UserPrinciple) authentication.getPrincipal();

        Optional<User> user = userRepository.findByEmail(oauthUser.getEmail());
        UserDTOWithToken userDto = mapper.map(user, UserDTOWithToken.class);
        String token = jwtUtils.generateJwt(oauthUser.getUsername());
        userDto.setToken(token);

        Object responseObject = ResponseUtil.get(userDto, HttpStatus.OK);
        Gson gson = new Gson();
        String userJson = gson.toJson(responseObject);

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(userJson);
        out.flush();
    }
}
