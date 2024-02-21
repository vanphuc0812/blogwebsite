package com.example.blogwebsite.role.boundary;

import com.example.blogwebsite.common.util.ResponseUtil;
import com.example.blogwebsite.role.dto.OperationDTO;
import com.example.blogwebsite.role.service.OperationService;
import com.example.blogwebsite.security.authorization.PlogOperation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/OperationsManagement")

public class OperationRestResource {
    private final OperationService operationService;

    public OperationRestResource(OperationService operationService) {
        this.operationService = operationService;
    }

    @PlogOperation(name = "RoleManagement")
    @GetMapping("/GetAllOperations")
    public Object findAll() {
        return ResponseUtil.get(operationService.findAllDto(OperationDTO.class), HttpStatus.OK);
    }

    @PlogOperation(name = "RoleManagement")
    @PostMapping("/SaveOperation")
    public Object save(@RequestBody @Valid OperationDTO dto) {
        return ResponseUtil.get(
                operationService.save(dto)
                , HttpStatus.OK
        );
    }

    @PlogOperation(name = "RoleManagement")
    @GetMapping("/common/Search")
    public Object searchOperation(@RequestParam("query") String query) {
        return ResponseUtil.get(operationService.searchOperation(query), HttpStatus.OK);
    }

    @PlogOperation(name = "RoleManagement")
    @PostMapping("/SaveOperations/{role-id}")
    public Object saveOperations(@RequestBody List<OperationDTO> dtos, @PathVariable("role-id") UUID roleId) {
        return ResponseUtil.get(
                operationService.saveOperations(dtos, roleId)
                , HttpStatus.OK
        );
    }

    @PlogOperation(name = "RoleManagement")
    @PutMapping("/UpdateOperation")
    public Object update(@RequestBody OperationDTO operation) {
        return ResponseUtil.get(operationService.update(operation), HttpStatus.OK);
    }

    @PlogOperation(name = "RoleManagement")
    @DeleteMapping("/DeleteOperation")
    public Object delete(@RequestParam("code") String code) {
        operationService.deleteByCode(code);
        return HttpStatus.OK;
    }
}
