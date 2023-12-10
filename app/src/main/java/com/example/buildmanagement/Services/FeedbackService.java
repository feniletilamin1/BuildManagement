package com.example.buildmanagement.Services;

import com.example.buildmanagement.Models.Feedback;

import java.util.List;

public interface FeedbackService {
    List<Feedback> findAllById(int id);
    Feedback findOneById(int id);
    int save(Feedback feedback);
    void delete(int id);
}
