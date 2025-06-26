package com.fitness.activityservice.dto;

import com.fitness.activityservice.model.ActivityType;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Map;
@Data
public class ActivityRequest {

    private String userId;
    private ActivityType type;
    private Integer duration;
    private Integer calories;
    private LocalDateTime startTime;
    private Map<String, Object> additionalMetrics;
    //private LocalDateTime createdAt;
    //private LocalDateTime updatedAt;

}
