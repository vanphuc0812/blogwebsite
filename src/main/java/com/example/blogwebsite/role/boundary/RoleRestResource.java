package com.example.blogwebsite.role.boundary;

import com.example.blogwebsite.common.util.ResponseUtil;
import com.example.blogwebsite.role.dto.RoleDTO;
import com.example.blogwebsite.role.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("api/RolesManagement")

public class RoleRestResource {
    private final RoleService service;

    public RoleRestResource(RoleService roleService) {
        this.service = roleService;
    }


    @GetMapping("/GetAllRoles")
    public Object findAll() {
        return ResponseUtil.get(service.findAllDto(RoleDTO.class), HttpStatus.OK);
    }


    @GetMapping("/GetAllRolesPaging")
    public Object findAllDtoPaging(@RequestParam("size") int size,
                                   @RequestParam("index") int index) {
        return ResponseUtil.get(
                service.findAllDto(Pageable.ofSize(size).withPage(index), RoleDTO.class)
                , HttpStatus.OK
        );
    }


    @GetMapping("/common/Search")
    public Object searchRole(@RequestParam("query") String query) {
        return ResponseUtil.get(service.searchRole(query), HttpStatus.OK);
    }


    @GetMapping("/GetAllRolesWithOperations")
    public Object findAllWithOperations() {
        return ResponseUtil.get(service.findAllWithOperations(), HttpStatus.OK);
    }


    @GetMapping("/GetAllRolesWithUsergroups")
    public Object findAllWithUsergroups() {
        return ResponseUtil.get(service.findAllWitUsergroups(), HttpStatus.OK);
    }


    @GetMapping("{role-id}/GetOperationsWithRoleId")
    public Object getOperationsWithRole(
            @PathVariable("role-id") UUID roleId) {
        return ResponseUtil.get(
                service.getOperationsWithRoleId(roleId)
                , HttpStatus.OK
        );
    }


    @GetMapping("{role-id}/GetUserGroupsWithRoleId")
    public Object getUserGroupsWithRole(
            @PathVariable("role-id") UUID roleId) {
        return ResponseUtil.get(
                service.getUserGroupsWithRoleId(roleId)
                , HttpStatus.OK
        );
    }


    @PostMapping("/SaveRole")
    public Object save(@RequestBody @Valid RoleDTO roleDTO) {
        return ResponseUtil.get(service.save(roleDTO), HttpStatus.CREATED);
    }


    @PostMapping("{role-id}/AddOperations")
    public Object addOperations(
            @RequestBody List<UUID> ids,
            @PathVariable("role-id") UUID roleId) {
        return ResponseUtil.get(
                service.addOperations(roleId, ids)
                , HttpStatus.OK
        );
    }


    @DeleteMapping("{role-id}/RemoveOperations")
    public Object removeOperations(
            @RequestBody List<UUID> ids,
            @PathVariable("role-id") UUID roleId) {
        return ResponseUtil.get(
                service.removeOperations(roleId, ids)
                , HttpStatus.OK
        );
    }


    @PostMapping("{role-id}/addUserGroup")
    public Object addUserGroup(
            @RequestBody List<UUID> ids,
            @PathVariable("role-id") UUID roleId) {
        return ResponseUtil.get(
                service.addUserGroup(roleId, ids)
                , HttpStatus.OK
        );
    }


    @PutMapping("/UpdateRole")
    public Object update(@RequestBody RoleDTO roleDTO) {
        return ResponseUtil.get(service.update(roleDTO), HttpStatus.OK);
    }


    @DeleteMapping("/DeleteRole")
    public Object delete(@RequestParam("code") String code) {
        service.deleteByCode(code);
        return HttpStatus.OK;
    }


    @DeleteMapping("{role-id}/removeUserGroup")
    public Object removeUserGroup(
            @RequestBody List<UUID> ids,
            @PathVariable("role-id") UUID roleId) {
        return ResponseUtil.get(
                service.removeUserGroup(roleId, ids)
                , HttpStatus.OK
        );
    }

}
