package com.nttdata.usercrud.client;

import com.nttdata.usercrud.model.Task;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Date;
import java.util.List;

@FeignClient(name = "TaskCRUD", url = "http://localhost:8082/task")
public interface TaskFeignClient {

    @GetMapping
    List<Task> getTasksByUserId(int id);

    @PostMapping
    Task saveTask(@RequestBody Task task);

    @GetMapping("/completed")
    List<Task> findByTaskCompleted();

    @GetMapping("/date/{date}")
    List<Task> findByStartDateAfter(@PathVariable Date date);

}
