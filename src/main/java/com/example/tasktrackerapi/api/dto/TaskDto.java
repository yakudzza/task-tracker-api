package com.example.tasktrackerapi.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.JoinColumn;
import lombok.*;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    @NonNull
    private Long id;

    @NonNull
    private String name;

    @NonNull
    @JsonProperty("created_at")
    private Instant createdAt;

    @JsonProperty("task_state_id")
    private Long taskStateId;

    @NonNull
    private String description;
}
