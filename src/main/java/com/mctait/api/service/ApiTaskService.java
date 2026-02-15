package com.mctait.api.service;

import com.mctait.api.model.Task;
import com.mctait.api.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class to handle CRUD operations via JPA Layer
 */

@Service
public class ApiTaskService {

    private final TaskRepository taskRepository;

    // List of valid and enforced statuses
    // In future could be in separate class or DB
    private static final List<String> VALID_STATUSES = List.of(
            "To Do", "In Progress", "Completed", "Cancelled"
    );

    @Autowired
    public ApiTaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Return all Tasks
     */
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    /**
     * Add a new Task to the DB
     */
    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    /**
     * Retrieve a Task given its ID
     */
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    /**
     * Delete a Task
     * First make sure it exists by finding it before deleting
     */
    public boolean deleteTask(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Update the status of an existing Task
     * Status must conform to one of four values
     * Verify Task exists before updating
     */
    @Modifying
    @Transactional
    public boolean updateTaskStatus(Long id, String newStatus) {
        if (!VALID_STATUSES.contains(newStatus)) {
            return false;
        }

        Optional<Task> taskOpt = taskRepository.findById(id);
        if (taskOpt.isPresent()) {
            Task task = taskOpt.get();
            task.setStatus(newStatus);
            taskRepository.save(task);
            return true;
        }
        return false;
    }
}
