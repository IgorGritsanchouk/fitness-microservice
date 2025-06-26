package com.fitness.activityservice.service;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.model.Activity;
import com.fitness.activityservice.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityService {
    //private final Logger logger= LoggerFactory.getLogger(ActivityService.class);

    private final ActivityRepository activityRepository;
    private final UserValidationService userValidationService;
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.exchange.routing.key}")
    private String routingKey;

    public ActivityResponse trackActivity(ActivityRequest request){

        // using user microservice here
        boolean  isValidUser= userValidationService.validateUser(request.getUserId());
        if(!isValidUser){

            log.error("Invalid user with id:, {} ", request.getUserId());
            throw new RuntimeException("User does not exist, userId: "+ request.getUserId());
        }

        Activity activity = Activity.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .duration(request.getDuration())
                .calories(request.getCalories())
                .startTime(request.getStartTime())
                .additionalMetrics(request.getAdditionalMetrics())
                .build();

        Activity savedActivity = activityRepository.save(activity);

        // Publish to RabbitMQ for AI Processing
        try{
            rabbitTemplate.convertAndSend(exchange, routingKey, savedActivity);
            log.info("Published activity in to exchange: {} activity id: {}",exchange, savedActivity.getId());
        } catch(Exception e){
            log.error("Failed to publish activity to RabbitMQ : {}", e.getMessage());
        }

        log.info("Saved Activity, {}", activity.getId() );
        return mapToResponse(savedActivity);
    }

    private ActivityResponse mapToResponse(Activity activity){

        ActivityResponse response= new ActivityResponse();
        response.setId(activity.getId());
        response.setUserId(activity.getUserId());
        response.setType(activity.getType());
        response.setDuration(activity.getDuration());
        response.setCalories(activity.getCalories());
        response.setStartTime(activity.getStartTime());
        response.setAdditionalMetrics(activity.getAdditionalMetrics());
        response.setCreateAt(activity.getCreatedAt());
        response.setUpdatedAt(activity.getUpdatedAt());

        return response;
    }

    public List<ActivityResponse> getUserActivities(String userId) {

        List<Activity> activities= activityRepository.findByUserId(userId);
        return activities.stream()
                .map(this::mapToResponse)
                .toList();
                //.collect(Collectors.toList());
    }

    public ActivityResponse getActivityById(String activityId){

        log.info("getting activity by Id : {}", activityId);
        return activityRepository.findById(activityId)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Activity not found with id: "+ activityId));
    }

}
