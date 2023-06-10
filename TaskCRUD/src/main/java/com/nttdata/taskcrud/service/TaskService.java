package com.nttdata.taskcrud.service;

import com.nttdata.taskcrud.entity.TaskEntity;
import com.nttdata.taskcrud.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<TaskEntity> getAllTasks() {
        return taskRepository.findAll();
    }

    public TaskEntity getTaskById(int id) {
        return taskRepository.findById(id).orElse(null);
    }

    public TaskEntity saveTask(TaskEntity task) {
        return taskRepository.save(task);
    }

    public void deleteTask(int id) {taskRepository.deleteById(id);}

    public List<TaskEntity> findByTaskTitle(String taskTitle) {
        return taskRepository.findByTaskTitle(taskTitle);
    }

    public List<TaskEntity> findByTaskCompleted() {
        return taskRepository.findByTaskCompleted();
    }


    public List<TaskEntity> findByUserId(int id) {
        return taskRepository.findByUserId(id);
    }

    public List<TaskEntity> findByStartDateAfter(Date date) {
        return taskRepository.findByStartDateAfter(date);
    }

}
