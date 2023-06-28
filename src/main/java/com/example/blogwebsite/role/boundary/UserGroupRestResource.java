package com.example.blogwebsite.role.boundary;

import com.example.blogwebsite.common.util.ResponseUtil;
import com.example.blogwebsite.role.dto.UserGroupDTO;
import com.example.blogwebsite.role.model.UserGroup;
import com.example.blogwebsite.role.service.UserGroupService;
import com.example.blogwebsite.security.authorization.PlogOperation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/UserGroupsManagement")

public class UserGroupRestResource {
    private final UserGroupService service;

    public UserGroupRestResource(UserGroupService service) {
        this.service = service;
    }

    @PlogOperation(name = "RoleManagement")
    @GetMapping("/GetAllUserGroups")
    public ResponseEntity<?> findAllUserGroup() {
        return ResponseUtil.get(
                service.findAllDto(UserGroupDTO.class)
                , HttpStatus.OK
        );
    }

    @PlogOperation(name = "RoleManagement")
    @GetMapping("/GetAllUserGroupIncludeUsers")
    public ResponseEntity<?> findAllUserGroupIncludedUsers() {
        return ResponseUtil.get(
                service.findAllDtoIncludeUsers()
                , HttpStatus.OK
        );
    }

    @PlogOperation(name = "RoleManagement")
    @GetMapping("/GetUserGroupByName")
    public ResponseEntity<?> findUserGroupByName(@RequestParam("name") String name) {
        return ResponseUtil.get(
                service.findUserGroupByNameDTO(name)
                , HttpStatus.OK
        );
    }

    @PlogOperation(name = "RoleManagement")
    @GetMapping("/common/Search")
    public Object searchUserGroup(@RequestParam("query") String query) {
        return ResponseUtil.get(service.searchUserGroups(query), HttpStatus.OK);
    }

    @PlogOperation(name = "RoleManagement")
    @PostMapping("/SaveUserGroup")
    public ResponseEntity<?> saveUserGroup(@RequestBody @Valid UserGroupDTO userGroupDto) {
        return ResponseUtil.get(
                service.save(userGroupDto, UserGroup.class, UserGroupDTO.class)
                , HttpStatus.OK
        );
    }

    @PlogOperation(name = "RoleManagement")
    @PostMapping("{user-group-id}/AddUsers")
    public ResponseEntity<?> addUsers(
            @PathVariable("user-group-id") UUID userGroupId,
            @RequestBody List<UUID> ids) {
        return ResponseUtil.get(
                service.addUsers(userGroupId, ids)
                , HttpStatus.OK
        );
    }

    @PlogOperation(name = "RoleManagement")
    @PostMapping("{user-group-id}/RemoveUsers")
    public ResponseEntity<?> removeUsers(
            @PathVariable("user-group-id") UUID userGroupId,
            @RequestBody List<UUID> ids) {
        return ResponseUtil.get(
                service.removeUsers(userGroupId, ids)
                , HttpStatus.OK
        );
    }

    @PlogOperation(name = "RoleManagement")
    @PutMapping("/UpdateUserGroup")
    public Object update(@RequestBody @Valid UserGroupDTO userGroupDTO) {
        return ResponseUtil.get(service.update(userGroupDTO), HttpStatus.OK);
    }

    @PlogOperation(name = "RoleManagement")
    @DeleteMapping("/DeleteUserGroup")
    public Object delete(@RequestParam("name") String name) {
        service.deleteByName(name);
        return HttpStatus.OK;
    }
}
