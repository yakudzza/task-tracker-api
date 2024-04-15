package com.example.tasktrackerapi.store.repositories;

import com.example.tasktrackerapi.store.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

}
