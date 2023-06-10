package com.nttdata.usercrud.client;

import com.nttdata.usercrud.model.Task;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "TaskCRUD", url = "http://localhost:8082/task")
public interface TaskFeignClient {

    @GetMapping
    List<Task> getTasksByUserId(int id);

    @PostMapping
    Task saveTask(@RequestBody Task task);

    @GetMapping("/completed")
    List<Task> findByTaskCompleted();

}
