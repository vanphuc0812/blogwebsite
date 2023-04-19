package com.example.blogwebsite.role.service;

import com.example.blogwebsite.common.exception.TCHBusinessException;
import com.example.blogwebsite.common.service.GenericService;
import com.example.blogwebsite.common.util.TCHMapper;
import com.example.blogwebsite.role.dto.OperationDTO;
import com.example.blogwebsite.role.model.Operation;
import com.example.blogwebsite.role.model.Role;
import com.example.blogwebsite.role.repository.OperationRepository;
import com.example.blogwebsite.role.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface OperationService extends GenericService<Operation, OperationDTO, UUID> {

    List<Operation> findAll(List<UUID> operationIds);

    void deleteByCode(String code);


    List<Operation> findByRoleId(UUID roleId);

    OperationDTO update(OperationDTO operationDTO);

    Object saveOperations(List<OperationDTO> operationDTOS, UUID roleId);

    OperationDTO save(OperationDTO operationDTO);

    List<OperationDTO> searchOperation(String query);
}

@Service
@Transactional
class OperationServiceImpl implements OperationService {
    private final OperationRepository operationRepository;
    private final RoleRepository roleRepository;
    private final TCHMapper mapper;
    private TCHBusinessException roleIsNotExisted = new TCHBusinessException("role is not existed");

    OperationServiceImpl(OperationRepository operationRepository, RoleRepository roleRepository, TCHMapper mapper) {
        this.operationRepository = operationRepository;
        this.roleRepository = roleRepository;
        this.mapper = mapper;
    }

    @Override
    public JpaRepository<Operation, UUID> getRepository() {
        return operationRepository;
    }

    @Override
    public ModelMapper getMapper() {
        return mapper;
    }

    @Override
    public List<Operation> findAll(List<UUID> operationIds) {
        return operationRepository.findAllById(operationIds);
    }


    @Override
    public void deleteByCode(String code) {
        operationRepository.deleteByCode(code);
    }

    @Override
    public List<Operation> findByRoleId(UUID roleId) {
        return operationRepository.findByRoleId(roleId);
    }

    @Override
    public OperationDTO update(OperationDTO operationDTO) {
        Operation operation = operationRepository.findById(operationDTO.getId())
                .orElseThrow(() -> new TCHBusinessException("Operation not found"));
        operation.setName(operationDTO.getName());
        operation.setCode(operationDTO.getCode());
        operation.setDescription(operationDTO.getDescription());
        return mapper.map(operation, OperationDTO.class);
    }

    @Override
    public Object saveOperations(List<OperationDTO> operationDTOS, UUID roleId) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> roleIsNotExisted);

        List<Operation> operations = operationDTOS.stream().map(model -> mapper.map(model, Operation.class))
                .toList();
        operations.forEach(role::addOperation);

        return operationRepository.saveAll(operations)
                .stream()
                .map(model -> mapper.map(model, OperationDTO.class))
                .toList();
    }

    @Override
    public OperationDTO save(OperationDTO operationDTO) {
        Operation operation = mapper.map(operationDTO, Operation.class);
        return mapper.map(operationRepository.save(operation), OperationDTO.class);
    }

    @Override
    public List<OperationDTO> searchOperation(String query) {
        List<Operation> operations = operationRepository.searchOperations(query);
        List<OperationDTO> operationDTOS = operations
                .stream()
                .map(model -> mapper.map(model, OperationDTO.class))
                .toList();
        return operationDTOS;
    }

}
