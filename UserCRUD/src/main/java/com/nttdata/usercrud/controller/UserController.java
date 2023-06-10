package com.nttdata.usercrud.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.usercrud.entity.UserEntity;
import com.nttdata.usercrud.model.Task;
import com.nttdata.usercrud.service.UserService;
import io.swagger.v3.oas.models.OpenAPI;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        List<UserEntity> users = userService.getAllUsers();
        if (users.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable("id") int id) {
        UserEntity user = userService.getUserById(id);
        if (user == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/ageAbove/{age}")
    public ResponseEntity<List<UserEntity>> findByAge(@PathVariable("age") int age) {
        List<UserEntity> users = userService.findByAgeAbove(age);
        if (users.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/ageBelow/{age}")
    public ResponseEntity<List<UserEntity>> findByAgeBelow(@PathVariable("age") int age) {
        List<UserEntity> users = userService.findByAgeBelow(age);
        if (users.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<UserEntity> saveUser(@Valid @RequestBody UserEntity user, BindingResult result) {
        if (result.hasErrors())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        UserEntity savedUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);

    }

    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable("id") int id, @Valid @RequestBody UserEntity user, BindingResult result) {
        if (result.hasErrors())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        UserEntity userToUpdate = userService.getUserById(id);
        if (userToUpdate == null)
            return ResponseEntity.notFound().build();
        userToUpdate.setName(user.getName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setAge(user.getAge());
        UserEntity updatedUser = userService.saveUser(userToUpdate);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") int id) {
        UserEntity user = userService.getUserById(id);
        if (user == null)
            return ResponseEntity.notFound().build();
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<UserEntity>> findByName(@PathVariable("name") String name) {
        List<UserEntity> users = userService.findByName(name);
        if (users.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<List<Task>> getTasksByUserId(@PathVariable("id") int id) {
        List<Task> tasks = userService.getTasksByUserId(id);
        if (tasks.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(tasks);
    }



    @PostMapping("/task/{id}")
    public ResponseEntity<Task> saveTask(@PathVariable("id") int usuarioId, @Valid @RequestBody Task task) {
        Task savedTask = userService.saveTask(usuarioId, task);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

    @GetMapping("/taskCompleted")
    public ResponseEntity<Map<String, Object>> getUserByTaskCompleted() {
        Map<String, Object> response = userService.getUserByTaskCompleted();
        return ResponseEntity.ok(response);
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
