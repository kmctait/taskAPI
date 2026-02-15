package com.mctait.api.controller;

import com.mctait.api.dto.UpdateStatusRequest;
import com.mctait.api.model.Task;
import com.mctait.api.service.ApiTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest Controller for the Task API
 * Endpoints are marked with Operation annotation for Swagger documentation
 */

@RestController
@RequestMapping("/taskapi")
@Tag(name = "Task API", description = "CRUD Operations on Tasks")
public class ApiTaskController {

    private final ApiTaskService apiTaskService;

    @Autowired
    public ApiTaskController(ApiTaskService apiTaskService) {
        this.apiTaskService = apiTaskService;
    }

    @Operation(summary = "Diagnostic to check API up and running")
    @GetMapping("/test")
    public String test() {
        return "Task API up and running...";
    }

    @Operation(summary = "Retrieve a task by its ID")
    @GetMapping("/get-task/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return apiTaskService.getTaskById(id)
                .map(ResponseEntity::ok)           // If found, return 200 OK with Task
                .orElseGet(() -> ResponseEntity.notFound().build()); // If not found, return 404
    }

    @Operation(summary = "Retrieve all Tasks in DB")
    @GetMapping("/get-all-tasks")
    public List<Task> getAllTasks() {
        return apiTaskService.getAllTasks();
    }

    @Operation(summary = "Create a new Task")
    @PostMapping("/create")
    public Task createTask(@Valid @RequestBody Task task) {
        return apiTaskService.addTask(task);
    }

    @Operation(summary = "Delete a Task by its ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        if (apiTaskService.deleteTask(id)) {
            return ResponseEntity.noContent().build(); // HTTP 204
        } else {
            return ResponseEntity.notFound().build(); // HTTP 404
        }
    }

    @Operation(summary = "Update Status of a Task given its ID")
    @PostMapping("/update-status/{id}")
    public ResponseEntity<String> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateStatusRequest request) {

        boolean updated = apiTaskService.updateTaskStatus(id, request.getStatus());

        if (updated) {
            return ResponseEntity.ok("Status updated successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
