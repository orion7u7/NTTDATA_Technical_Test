package com.nttdata.usercrud.repository;

import com.nttdata.usercrud.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Query("SELECT u FROM UserEntity u WHERE u.name LIKE %?1%")
    List<UserEntity> findByName(String name);

    @Query("SELECT u FROM UserEntity u WHERE u.age < ?1")
    List<UserEntity> findByAgeBelow(int age);

    @Query("SELECT u FROM UserEntity u WHERE u.age > ?1")
    List<UserEntity> findByAgeAbove(int age);

    @Query( "SELECT u FROM UserEntity u WHERE u.id in :ids")
    List<UserEntity> findByIds(List<Integer> ids);
}
