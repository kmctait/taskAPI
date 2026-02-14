package com.mctait.api.controller;

import com.mctait.api.model.Task;
import com.mctait.api.service.ApiTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/taskapi")
public class ApiTaskController {

    private final ApiTaskService apiTaskService;

    @Autowired
    public ApiTaskController(ApiTaskService apiTaskService) {
        this.apiTaskService = apiTaskService;
    }

    @GetMapping("/test")
    public String test() {
        return "foo";
    }

    @GetMapping("/get-all-tasks")
    public List<Task> getAllTasks() {
        return apiTaskService.getAllTasks();
    }

    @PostMapping("/create")
    public Task createTask(@RequestBody Task task) {
        return apiTaskService.addTask(task);
    }
}
