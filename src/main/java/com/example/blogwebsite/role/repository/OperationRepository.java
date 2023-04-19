package com.example.blogwebsite.role.repository;

import com.example.blogwebsite.role.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OperationRepository extends JpaRepository<Operation, UUID> {
    Optional<Operation> findByName(String name);

    void deleteByCode(String code);

    @Query("select o from Operation o left join o.roles r where r.id = ?1")
    List<Operation> findByRoleId(UUID roleId);

    @Query("select o from Operation o left join o.roles r left join r.userGroups g left join g.users u where u.username = ?2 and o.name = ?1")
    List<Operation> findAllByNameAndUsername(String operationName, String username);

    Optional<Operation> findByCode(String code);

    @Query("SELECT op FROM Operation op WHERE " + " op.name LIKE CONCAT ('%',:query,'%')")
    List<Operation> searchOperations(String query);
}
