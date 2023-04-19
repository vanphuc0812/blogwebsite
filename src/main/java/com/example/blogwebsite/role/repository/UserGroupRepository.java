package com.example.blogwebsite.role.repository;

import com.example.blogwebsite.role.model.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, UUID> {

//    @Query("select ug from UserGroup ug left join fetch ug.users")
//    List<UserGroup> findAllWithUsers();

    @Query("select ug from UserGroup ug left join ug.roles r where r.id = ?1")
    List<UserGroup> findByRoleId(UUID id);

    void deleteByName(String name);

    Optional<UserGroup> findByName(String name);

    @Query("SELECT ug FROM UserGroup ug WHERE " + "ug.name LIKE CONCAT ('%',:query,'%')")
    List<UserGroup> searchUserGroups(String query);
}
