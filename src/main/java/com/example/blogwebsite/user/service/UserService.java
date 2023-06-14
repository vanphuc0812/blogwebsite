package com.example.blogwebsite.user.service;

import com.example.blogwebsite.common.exception.BWBusinessException;
import com.example.blogwebsite.common.service.GenericService;
import com.example.blogwebsite.common.util.BWMapper;
import com.example.blogwebsite.common.util.CustomRandom;
import com.example.blogwebsite.common.util.FileUtil;
import com.example.blogwebsite.role.dto.UserGroupDTO;
import com.example.blogwebsite.security.jwt.JwtUtils;
import com.example.blogwebsite.user.dto.UserDTO;
import com.example.blogwebsite.user.dto.UserDTOWithToken;
import com.example.blogwebsite.user.dto.UserDtoWithoutPassword;
import com.example.blogwebsite.user.model.User;
import com.example.blogwebsite.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface UserService extends GenericService<User, UserDTO, UUID> {


    void deleteByUserName(String username);

    UserDtoWithoutPassword update(UserDtoWithoutPassword userDTO);

    List<UserGroupDTO> findAllUserGroupUsername(String username);

    UserDTOWithToken createUser(UserDTO dto);

    UserDTO getUserByUsername(String username);

    User findUserByUsername(String username);

    UserDTOWithToken saveUserAvatar(String username, MultipartFile file, String baseUrl);

    List<UserDTO> searchUsers(String query, String type);

    List<UserDTOWithToken> createUsers(List<UserDTO> userDTOs);

    UserDTO followUser(String rootUsername, String followedUsername);

    UserDTO unfollowUser(String rootUsername, String followedUsername);


}

@Service
@Transactional
class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final BWMapper mapper;
    private final JwtUtils jwtUtils;

    UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, BWMapper mapper, JwtUtils jwtUtils) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public JpaRepository<User, UUID> getRepository() {
        return userRepository;
    }

    @Override
    public ModelMapper getMapper() {
        return mapper;
    }

    @Override

    public void deleteByUserName(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new BWBusinessException("User is not existed.")
                );
        user.getUserGroups().forEach(userGroup -> userGroup.removeUser(user));
        userRepository.deleteByUsername(username);
    }

    public UserDtoWithoutPassword update(UserDtoWithoutPassword userDTO) {
        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new BWBusinessException("User not found"));
        user.setName(userDTO.getName());
        user.setUsername(userDTO.getUsername());
        user.setAvatar(userDTO.getAvatar());
        user.setGender(User.Gender.valueOf(userDTO.getGender()));
        return mapper.map(user, UserDtoWithoutPassword.class);
    }


    @Override
    public List<UserGroupDTO> findAllUserGroupUsername(String username) {
        List<UserGroupDTO> userGroupDTOs = new ArrayList<>();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new BWBusinessException("User is not existed.")
                );
        user.getUserGroups().forEach(
                userGroup -> userGroupDTOs.add(mapper.map(userGroup, UserGroupDTO.class))
        );
        return userGroupDTOs;
    }

    @Override
    public UserDTOWithToken createUser(UserDTO dto) {
        User user = mapper.map(dto, User.class);
        // encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setProvider(User.Provider.local);

        return mapper.map(
                userRepository.save(user),
                UserDTOWithToken.class
        );
    }

    @Override
    public List<UserDTOWithToken> createUsers(List<UserDTO> userDTOs) {

        return userDTOs.stream().map((userDTO) -> {
            User user = mapper.map(userDTO, User.class);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setProvider(User.Provider.local);
            user.setToken(jwtUtils.generateJwt(user.getUsername()));
            return mapper.map(
                    userRepository.save(user),
                    UserDTOWithToken.class
            );
        }).toList();
    }

    @Override
    public UserDTO followUser(String rootUsername, String followedUsername) {
        User rootUser = userRepository.findByUsername(rootUsername)
                .orElseThrow(() ->
                        new BWBusinessException("User is not existed.")
                );
        User followedUser = userRepository.findByUsername(followedUsername)
                .orElseThrow(() ->
                        new BWBusinessException("Followed User is not existed.")
                );
        rootUser.getFollowing().add(followedUsername);
        followedUser.getFollowed().add(rootUsername);

        return mapper.map(rootUser, UserDTO.class);

    }

    @Override
    public UserDTO unfollowUser(String rootUsername, String unfollowedUsername) {
        User rootUser = userRepository.findByUsername(rootUsername)
                .orElseThrow(() ->
                        new BWBusinessException("User is not existed.")
                );
        User followedUser = userRepository.findByUsername(unfollowedUsername)
                .orElseThrow(() ->
                        new BWBusinessException("Followed User is not existed.")
                );
        rootUser.getFollowing().remove(unfollowedUsername);
        followedUser.getFollowed().remove(rootUsername);

        return mapper.map(rootUser, UserDTO.class);

    }

    @Override
    public UserDTO getUserByUsername(String username) {
        return mapper.map(userRepository.findByUsername(username), UserDTO.class);
    }

    @Override
    public User findUserByUsername(String username) {

        return userRepository.findByUsername(username).orElseThrow(() -> new BWBusinessException("username is not existed"));
    }

    @Override
    public UserDTOWithToken saveUserAvatar(String username, MultipartFile file, String baseUrl) {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new BWBusinessException("User is not existed")
        );
        String avatar = CustomRandom.generateRandomFilename();
        FileUtil.saveFile(file, avatar);
        user.setAvatar(avatar);
        return mapper.map(user, UserDTOWithToken.class);
    }

    @Override
    public List<UserDTO> searchUsers(String query, String type) {
        if ("less".equals(type)) {
            return userRepository.search10Users(query)
                    .stream()
                    .map(model -> mapper.map(model, UserDTO.class))
                    .toList();
        } else {
            return userRepository.searchUsers(query)
                    .stream()
                    .map(model -> mapper.map(model, UserDTO.class))
                    .toList();
        }

    }


}