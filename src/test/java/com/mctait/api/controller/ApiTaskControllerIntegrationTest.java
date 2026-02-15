package com.mctait.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mctait.api.dto.UpdateStatusRequest;
import com.mctait.api.model.Task;
import com.mctait.api.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Calendar;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests mocking the calling of API endpoints with expected results
 * Makes use of in-memory H2 database
 */

@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
class ApiTaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        taskRepository.deleteAll();
    }

    @Test
    void createTask_shouldReturnCreatedTask() throws Exception {

        Task task = new Task();
        task.setTitle("Integration Task");
        task.setDescription("Testing create endpoint");
        task.setStatus("Pending");
        task.setDueDate(Calendar.getInstance().getTime());

        mockMvc.perform(post("/taskapi/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Integration Task"))
                .andExpect(jsonPath("$.status").value("Pending"));
    }

    @Test
    void getAllTasks_shouldReturnList() throws Exception {

        Task task = new Task();
        task.setTitle("Stored Task");
        task.setStatus("Pending");
        task.setDescription("This is my stored task");
        task.setDueDate(Calendar.getInstance().getTime());
        taskRepository.save(task);

        mockMvc.perform(get("/taskapi/get-all-tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Stored Task"));
    }

    @Test
    void getTaskById_shouldReturnTask() throws Exception {

        Task task = new Task();
        task.setTitle("Find Me");
        task.setStatus("Pending");
        task.setDescription("This is my pending task");
        task.setDueDate(Calendar.getInstance().getTime());
        task = taskRepository.save(task);

        mockMvc.perform(get("/taskapi/get-task/" + task.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Find Me"));
    }

    @Test
    void getTaskById_shouldReturn404IfNotFound() throws Exception {

        mockMvc.perform(get("/taskapi/get-task/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateStatus_shouldUpdateSuccessfully() throws Exception {

        Task task = new Task();
        task.setTitle("Task To Update");
        task.setStatus("Pending");
        task.setDescription("This is task to update");
        task.setDueDate(Calendar.getInstance().getTime());
        task = taskRepository.save(task);

        UpdateStatusRequest request = new UpdateStatusRequest();
        request.setStatus("Completed");

        mockMvc.perform(post("/taskapi/update-status/" + task.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Status updated successfully."));
    }

    @Test
    void updateStatus_shouldReturn404IfNotFound() throws Exception {

        UpdateStatusRequest request = new UpdateStatusRequest();
        request.setStatus("Completed");

        mockMvc.perform(post("/taskapi/update-status/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteTask_shouldReturnNoContent() throws Exception {

        Task task = new Task();
        task.setTitle("Task To Delete");
        task.setStatus("Pending");
        task.setDescription("This is the task to delete");
        task.setDueDate(Calendar.getInstance().getTime());

        task = taskRepository.save(task);

        mockMvc.perform(delete("/taskapi/delete/" + task.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteTask_shouldReturn404IfNotFound() throws Exception {

        mockMvc.perform(delete("/taskapi/delete/999"))
                .andExpect(status().isNotFound());
    }
}
