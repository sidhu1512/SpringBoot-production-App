package com.example.fitness.repository;

import com.example.fitness.dto.ActivityResponse;
import com.example.fitness.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, String> {

    List<Activity> findByUserId(String userId);

    Activity getActivityById(String id);
}
