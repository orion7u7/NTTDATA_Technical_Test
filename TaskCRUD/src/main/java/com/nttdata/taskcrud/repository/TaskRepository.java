package com.nttdata.taskcrud.repository;

import com.nttdata.taskcrud.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {

    @Query("SELECT t FROM TaskEntity t WHERE t.title LIKE %?1%")
    List<TaskEntity> findByTaskTitle(String taskTitle);

    @Query("SELECT t FROM TaskEntity t WHERE t.endDate IS NOT null")
    List<TaskEntity> findByTaskCompleted();

    @Query("SELECT t FROM TaskEntity t WHERE t.idUser = ?1")
    List<TaskEntity> findByUserId(int id);

    @Query("SELECT t FROM TaskEntity t WHERE t.startDate > ?1")
    List<TaskEntity> findByStartDateAfter(Date date);
}
