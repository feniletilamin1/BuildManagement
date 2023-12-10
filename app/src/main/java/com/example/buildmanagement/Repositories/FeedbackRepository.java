package com.example.buildmanagement.Repositories;

import com.example.buildmanagement.Models.Feedback;

import java.util.List;

public interface FeedbackRepository {
    List<Feedback> findAllById(int id);
    Feedback findOneById(int id);
    int save(Feedback feedback);
    int delete(int id);
}
