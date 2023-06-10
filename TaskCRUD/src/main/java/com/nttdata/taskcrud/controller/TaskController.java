package com.nttdata.taskcrud.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.taskcrud.entity.TaskEntity;
import com.nttdata.taskcrud.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskEntity>> getAllTask() {
        List<TaskEntity> tasks = taskService.getAllTasks();
        if (tasks.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity getTaskById(@PathVariable("id") int id) {
        TaskEntity task = taskService.getTaskById(id);
        if (task == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @PostMapping
    public ResponseEntity<TaskEntity> saveTask(@Valid @RequestBody TaskEntity task, BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        TaskEntity taskDB = taskService.saveTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskDB);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskEntity> updateTask(@PathVariable("id") int id,@Valid @RequestBody TaskEntity task, BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        TaskEntity taskToUpdate = taskService.getTaskById(id);
        if (taskToUpdate == null)
            return ResponseEntity.notFound().build();
        taskToUpdate.setTitle(task.getTitle());
        taskToUpdate.setDescription(task.getDescription());
        taskToUpdate.setStartDate(task.getStartDate());
        taskToUpdate.setEndDate(task.getEndDate());
        taskToUpdate.setIdUser(task.getIdUser());
        TaskEntity updatedTask = taskService.saveTask(taskToUpdate);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTask(@PathVariable("id") int id) {
        TaskEntity taskToDelete = taskService.getTaskById(id);
        if (taskToDelete == null)
            return ResponseEntity.notFound().build();
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/title/{taskTitle}")
    public ResponseEntity<List<TaskEntity>> findByTaskTitle(@PathVariable("taskTitle") String taskTitle) {
        List<TaskEntity> tasks = taskService.findByTaskTitle(taskTitle);
        if (tasks.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/completed")
    public ResponseEntity<List<TaskEntity>> findByTaskCompleted() {
        List<TaskEntity> tasks = taskService.findByTaskCompleted();
        if (tasks.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<TaskEntity>> findByUserId(@PathVariable("id")int id) {
        List<TaskEntity> tasks = taskService.findByUserId(id);
        if (tasks.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<TaskEntity>> findByStartDateAfter(@PathVariable("date") Date date) {
        List<TaskEntity> tasks = taskService.findByStartDateAfter(date);
        if (tasks.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(tasks);
    }

    private String formatMessage(BindingResult result) {
        List<Map<String, String>> errors = result.getFieldErrors().stream()
                .map(err -> {
                    Map<String, String> error = new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());
        ErrorMenssage errorMenssage = ErrorMenssage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(errorMenssage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
