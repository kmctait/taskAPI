package com.mctait.api.service;

import com.mctait.api.model.Task;
import com.mctait.api.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class) // JUnit 5 + Mockito
class ApiTaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private ApiTaskService taskService;

    @Test
    void addTask_shouldReturnSavedTask() {
        Task task = new Task();
        task.setTitle("Unit Test Task");

        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.addTask(task);

        assertNotNull(result);
        assertEquals("Unit Test Task", result.getTitle());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void getAllTasks_shouldReturnList() {
        Task task1 = new Task();
        task1.setTitle("Task 1");
        Task task2 = new Task();
        task2.setTitle("Task 2");

        when(taskRepository.findAll()).thenReturn(List.of(task1, task2));

        List<Task> result = taskService.getAllTasks();

        assertEquals(2, result.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void getTaskById_shouldReturnTaskIfExists() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Find Me");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Optional<Task> found = taskService.getTaskById(1L);

        assertTrue(found.isPresent());
        assertEquals("Find Me", found.get().getTitle());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void deleteTask_shouldReturnTrueIfExists() {
        when(taskRepository.existsById(1L)).thenReturn(true);

        boolean deleted = taskService.deleteTask(1L);

        assertTrue(deleted);
        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteTask_shouldReturnFalseIfNotExists() {
        when(taskRepository.existsById(2L)).thenReturn(false);

        boolean deleted = taskService.deleteTask(2L);

        assertFalse(deleted);
        verify(taskRepository, never()).deleteById(anyLong());
    }

    @Test
    void updateTaskStatus_shouldUpdateIfExists() {
        Task task = new Task();
        task.setId(1L);
        task.setStatus("To Do");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);

        boolean updated = taskService.updateTaskStatus(1L, "In Progress");

        assertTrue(updated);
        assertEquals("In Progress", task.getStatus());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void updateTaskStatus_shouldReturnFalseIfNotExists() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        boolean updated = taskService.updateTaskStatus(99L, "To Do");

        assertFalse(updated);
        verify(taskRepository, never()).save(any());
    }
}
