package com.mctait.api.repository;

import com.mctait.api.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Layer interface for Task objects
 */

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
