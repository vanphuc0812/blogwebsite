package com.example.blogwebsite.user.service;

import com.example.blogwebsite.common.exception.TCHBusinessException;
import com.example.blogwebsite.common.service.GenericService;
import com.example.blogwebsite.common.util.TCHMapper;
import com.example.blogwebsite.file.FileService;
import com.example.blogwebsite.role.dto.UserGroupDTO;
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

    List<UserDTO> searchUsers(String query);
}

@Service
@Transactional
class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TCHMapper tchMapper;
    private final FileService fileService;

    UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, TCHMapper tchMapper, FileService fileService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.tchMapper = tchMapper;
        this.fileService = fileService;
    }

    @Override
    public JpaRepository<User, UUID> getRepository() {
        return userRepository;
    }

    @Override
    public ModelMapper getMapper() {
        return tchMapper;
    }

    @Override

    public void deleteByUserName(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new TCHBusinessException("User is not existed.")
                );
        user.getUserGroups().forEach(userGroup -> userGroup.removeUser(user));
        userRepository.deleteByUsername(username);
    }

    public UserDtoWithoutPassword update(UserDtoWithoutPassword userDTO) {
        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new TCHBusinessException("User not found"));
        user.setName(userDTO.getName());
        user.setUsername(userDTO.getUsername());
        user.setBirth(userDTO.getBirth());
        user.setAvatar(userDTO.getAvatar());
        user.setPhone(userDTO.getPhone());
        user.setAddress(userDTO.getAddress());
        user.setGender(User.Gender.valueOf(userDTO.getGender()));
        return tchMapper.map(user, UserDtoWithoutPassword.class);
    }


    @Override
    public List<UserGroupDTO> findAllUserGroupUsername(String username) {
        List<UserGroupDTO> userGroupDTOs = new ArrayList<>();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new TCHBusinessException("User is not existed.")
                );
        user.getUserGroups().forEach(
                userGroup -> userGroupDTOs.add(tchMapper.map(userGroup, UserGroupDTO.class))
        );
        return userGroupDTOs;
    }

    @Override
    public UserDTOWithToken createUser(UserDTO dto) {
        User user = tchMapper.map(dto, User.class);
        // encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setProvider(User.Provider.local);

        return tchMapper.map(
                userRepository.save(user),
                UserDTOWithToken.class
        );
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        return tchMapper.map(userRepository.findByUsername(username), UserDTO.class);
    }

    @Override
    public User findUserByUsername(String username) {

        return userRepository.findByUsername(username).orElseThrow(() -> new TCHBusinessException("username is not existed"));
    }

    @Override
    public UserDTOWithToken saveUserAvatar(String username, MultipartFile file, String baseUrl) {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new TCHBusinessException("User is not existed")
        );
        fileService.init();
        fileService.save(file);
        String urlLoadFile = baseUrl + "/api/Files/" + file.getOriginalFilename();
        user.setAvatar(urlLoadFile);
        return tchMapper.map(user, UserDTOWithToken.class);
    }

    @Override
    public List<UserDTO> searchUsers(String query) {
        List<User> users = userRepository.searchUsers(query);
        List<UserDTO> userDTOS = users
                .stream()
                .map(model -> tchMapper.map(model, UserDTO.class))
                .toList();
        return userDTOS;
    }

}