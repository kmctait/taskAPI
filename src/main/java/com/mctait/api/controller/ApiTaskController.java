package com.mctait.api.controller;

import com.mctait.api.dto.UpdateStatusRequest;
import com.mctait.api.model.Task;
import com.mctait.api.service.ApiTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        return "Task API up and running...";
    }

    @GetMapping("/get-task/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return apiTaskService.getTaskById(id)
                .map(ResponseEntity::ok)           // If found, return 200 OK with Task
                .orElseGet(() -> ResponseEntity.notFound().build()); // If not found, return 404
    }

    @GetMapping("/get-all-tasks")
    public List<Task> getAllTasks() {
        return apiTaskService.getAllTasks();
    }

    @PostMapping("/create")
    public Task createTask(@RequestBody Task task) {
        return apiTaskService.addTask(task);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        if (apiTaskService.deleteTask(id)) {
            return ResponseEntity.noContent().build(); // HTTP 204
        } else {
            return ResponseEntity.notFound().build(); // HTTP 404
        }
    }

    @PatchMapping("/update-status/{id}")
    public ResponseEntity<String> updateStatus(
            @PathVariable Long id,
            @RequestBody UpdateStatusRequest request) {

        boolean updated = apiTaskService.updateTaskStatus(id, request.getStatus());

        if (updated) {
            return ResponseEntity.ok("Status updated successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
