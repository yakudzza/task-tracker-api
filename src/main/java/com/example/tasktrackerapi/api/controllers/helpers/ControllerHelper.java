package com.example.tasktrackerapi.api.controllers.helpers;


import com.example.tasktrackerapi.api.exceptions.NotFoundException;
import com.example.tasktrackerapi.store.entities.ProjectEntity;
import com.example.tasktrackerapi.store.entities.TaskStateEntity;
import com.example.tasktrackerapi.store.repositories.ProjectRepository;
import com.example.tasktrackerapi.store.repositories.TaskStateRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
@Transactional
public class ControllerHelper {

    ProjectRepository projectRepository;
    TaskStateRepository taskStateRepository;

    public ProjectEntity getProjectOrThrowException(Long projectId) {
        return projectRepository
                .findById(projectId)
                .orElseThrow(() ->
                        new NotFoundException(
                                String.format(
                                        "Project with " + projectId + " doesn't exist"
                                )
                        ));
    }

    public TaskStateEntity getTaskEntityOrThrowException(Long taskStateId){
        return taskStateRepository
                .findById(taskStateId)
                .orElseThrow(() ->
                        new NotFoundException(
                                String.format(
                                        "Task state with " + taskStateId + " doesn't exist"
                                )
                        ));
    }
}
