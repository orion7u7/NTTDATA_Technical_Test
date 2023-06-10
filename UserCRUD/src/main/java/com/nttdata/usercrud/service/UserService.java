package com.nttdata.usercrud.service;

import com.nttdata.usercrud.client.TaskFeignClient;
import com.nttdata.usercrud.entity.UserEntity;
import com.nttdata.usercrud.model.Task;
import com.nttdata.usercrud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {



    private final TaskFeignClient taskFeignClient;

    private final UserRepository userRepository;


    public List<Task> getTasksByUserId(int id) {
        List<Task> tasks = taskFeignClient.getTasksByUserId(id);
        return tasks;
    }

    public Task saveTask(int idUser,Task task) {
        task.setIdUser(idUser);
        Task newTask = taskFeignClient.saveTask(task);
        return newTask;
    }

    public Map<String,Object> getUserByTaskCompleted() {
        Map<String, Object> result = new HashMap<>();
        List<Task> tasks = taskFeignClient.findByTaskCompleted();
        List<UserEntity> users = new ArrayList<>();

        for (Task task : tasks) {
            List<UserEntity> taskUsers = userRepository.findByIds(List.of(task.getIdUser()));
            users.addAll(taskUsers);
        }
        result.put("tasks", tasks);
        result.put("users", users);
        return result;
    }
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserEntity saveUser(UserEntity user) {
        return userRepository.save(user);
    }

    public void deleteUser(int id) {userRepository.deleteById(id);}

    public List<UserEntity> findByName(String name) {
        return userRepository.findByName(name);
    }

    public List<UserEntity> findByAgeBelow(int age) {return userRepository.findByAgeBelow(age);}

    public List<UserEntity> findByAgeAbove(int age) {return userRepository.findByAgeAbove(age);}


}
