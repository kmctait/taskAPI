package com.mctait.api.service;

import com.mctait.api.model.Task;
import com.mctait.api.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ApiTaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public ApiTaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public boolean deleteTask(Long id) {
        // Check if the task exists
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);  // Delete the task
            return true;                    // Indicate deletion success
        }
        return false;                       // Task not found
    }

    @Modifying
    @Transactional
    public boolean updateTaskStatus(Long taskId, String newStatus) {
        return taskRepository.findById(taskId)
                .map(task -> {
                    task.setStatus(newStatus);
                    taskRepository.save(task);  // persists the change
                    return true;
                })
                .orElse(false);
    }
}
