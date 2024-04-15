package com.example.tasktrackerapi.api.factories;


import com.example.tasktrackerapi.api.dto.TaskDto;
import com.example.tasktrackerapi.store.entities.TaskEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskDtoFactory {

    public TaskDto makeTaskDto(TaskEntity entity){

        return TaskDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .description(entity.getDescription())
                .taskStateId(entity.getTaskStateId())
                .build();
    }
}
