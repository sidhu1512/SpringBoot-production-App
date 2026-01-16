package com.example.fitness.controller;

import com.example.fitness.dto.RecommendationRequest;
import com.example.fitness.model.Activity;
import com.example.fitness.model.Recommendation;
import com.example.fitness.service.ActivityService;
import com.example.fitness.service.RecommendationService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommendation")
public class RecommendationController {

    public final RecommendationService recommendationService;
    public final ActivityService activityService;

    @PostMapping("/generate")
    public ResponseEntity<Recommendation> generateRecommendation(
            @RequestBody RecommendationRequest request){
        Recommendation recommendation = recommendationService.generateRecommendation(request);
        return ResponseEntity.ok(recommendation);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Recommendation>> getUserRecommendations(
            @PathVariable String userId
    ){
         List<Recommendation> recommendationList
                 = recommendationService.getUserRecommendations(userId);
         return ResponseEntity.ok(recommendationList);
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<List<Recommendation>> getActivityRecommendation(
            @PathVariable String activityId
    ){
        List<Recommendation> recommendationList
                = recommendationService.getActivityRecommendation(activityId);
        return ResponseEntity.ok(recommendationList);
    }

}
