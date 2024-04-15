package com.example.tasktrackerapi.api.controllers;


import com.example.tasktrackerapi.api.controllers.helpers.ControllerHelper;
import com.example.tasktrackerapi.api.dto.AckDto;
import com.example.tasktrackerapi.api.dto.TaskDto;
import com.example.tasktrackerapi.api.dto.TaskStateDto;
import com.example.tasktrackerapi.api.exceptions.BadRequestException;
import com.example.tasktrackerapi.api.exceptions.NotFoundException;
import com.example.tasktrackerapi.api.factories.ProjectDtoFactory;
import com.example.tasktrackerapi.api.factories.TaskDtoFactory;
import com.example.tasktrackerapi.store.entities.ProjectEntity;
import com.example.tasktrackerapi.store.entities.TaskEntity;
import com.example.tasktrackerapi.store.entities.TaskStateEntity;
import com.example.tasktrackerapi.store.repositories.ProjectRepository;
import com.example.tasktrackerapi.store.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@RestController
public class TaskController {

    public static final String GET_TASK = "/api/projects/api/task-states/{task_state_id}/tasks";
    public static final String CREATE_TASK = "/api/projects/api/task-states/{task_state_id}/tasks";
    public static final String UPDATE_TASK = "/api/tasks/{task_id}";
    private static final String DELETE_TASK = "/api/tasks/{task_id}";

    TaskRepository taskRepository;
    TaskDtoFactory taskDtoFactory;
    ControllerHelper controllerHelper;

    @GetMapping(GET_TASK)
    public List<TaskDto> getTask(@PathVariable(name = "task_state_id") Long project_id){
        TaskStateEntity taskState = controllerHelper.getTaskEntityOrThrowException(project_id);

        taskState.getTasks();

        return taskState
                .getTasks()
                .stream()
                .map(taskDtoFactory::makeTaskDto)
                .collect(Collectors.toList());
    }

    @PostMapping(CREATE_TASK)
    public TaskDto createTask(
            @PathVariable(name = "task_state_id") Long taskStateId,
            @RequestParam(name = "task_name") String taskName,
            @RequestParam(name = "task_description") String task_description) {

        if (taskName.trim().isEmpty()) {
            throw new BadRequestException("Task name can't be empty.");
        }

        TaskStateEntity taskState = controllerHelper.getTaskEntityOrThrowException(taskStateId);

        TaskEntity task = taskRepository.saveAndFlush(
                TaskEntity.builder()
                        .name(taskName)
                        .taskState(taskState)
                        .description(task_description)
                        .taskState(taskState)
                        .build()
        );

        return taskDtoFactory.makeTaskDto(task);
    }

    @PatchMapping(UPDATE_TASK)
    public TaskDto updateTask(
            @PathVariable(name = "task_id") Long taskId,
            @RequestParam(name = "task_name") String taskName,
            @RequestParam(name = "task_description") String taskDescription) {

        if (taskName.trim().isEmpty()){
            throw new BadRequestException("Task state name can't be empty.");
        }


        TaskEntity task = taskRepository
                .findById(taskId)
                .orElseThrow(() ->
                    new NotFoundException(
                            String.format(
                                    "Task with \"%s\" id doesn't exist.",
                                    taskId
                            )
                    )
                );

        if (taskName != null && !taskName.trim().isEmpty()) {
            task.setName(taskName);
        }

        if (taskDescription != null && !taskDescription.trim().isEmpty()) {
            task.setDescription(taskDescription);
        }

        TaskEntity updatedTask = taskRepository.saveAndFlush(task);


        return taskDtoFactory.makeTaskDto(updatedTask);

    }

    @DeleteMapping(DELETE_TASK)
    public AckDto deleteTask(@PathVariable(name = "task_id") Long taskId){
        TaskEntity task = taskRepository
                .findById(taskId)
                .orElseThrow(() ->
                        new NotFoundException(
                                String.format(
                                        "Task with \"%s\" id doesn't exist.",
                                        taskId
                                )
                        )
                );

        taskRepository.delete(task);
        return AckDto.builder().answer(true).build();
    }


}
