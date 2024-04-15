package com.example.tasktrackerapi.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskStateDto {

    @NonNull
    private Long id;

    @NonNull
    private String name;

    @JsonProperty("left_task_state_id")
    private Long leftTaskStateId;

    @JsonProperty("light_task_state_id")
    private Long rightTaskStateId;

    @NonNull
    @JsonProperty("created_at")
    private Instant createdAt;

    @NonNull
    List<TaskDto> tasks;
}
