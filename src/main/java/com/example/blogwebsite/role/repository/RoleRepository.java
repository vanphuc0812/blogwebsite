package com.example.blogwebsite.role.repository;

import com.example.blogwebsite.role.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    void deleteByCode(String code);

    Optional<Role> findByName(String name);

    Optional<Role> findByCode(String code);

    @Query("select (count(r) > 0) from Role r where r.id = ?1")
    boolean existsById(UUID id);

    @Query("SELECT r FROM Role r WHERE " + "r.name LIKE CONCAT ('%',:query,'%')")
    List<Role> searchRoles(String query);
}
