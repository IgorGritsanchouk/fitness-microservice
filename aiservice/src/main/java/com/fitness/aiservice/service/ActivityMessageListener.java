package com.fitness.aiservice.service;

import com.fitness.aiservice.model.Activity;
import com.fitness.aiservice.model.Recommendation;
import com.fitness.aiservice.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityMessageListener {

    private final ActivityAIService aiService;
    private final RecommendationRepository recommendationRepository;

    @RabbitListener(queues= "activity.queue")
    public void processActivity(Activity activity){

        log.info("Received activity from activity.queue: {}, user id: {}", activity.getId(), activity.getUserId());
        //log.info("Generated Recommendation: {}", aiService.generateRecommendation(activity));
        // saving to mongo db 2
        Recommendation recommendation= aiService.generateRecommendation(activity);
        recommendationRepository.save(recommendation);
    }
}
