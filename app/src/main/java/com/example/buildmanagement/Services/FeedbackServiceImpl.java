package com.example.buildmanagement.Services;

import com.example.buildmanagement.Models.Feedback;
import com.example.buildmanagement.Repositories.FeedbackRepositoryImpl;

import java.util.List;

public class FeedbackServiceImpl implements FeedbackService {
    private FeedbackRepositoryImpl feedbackRepository = new FeedbackRepositoryImpl();

    @Override
    public List<Feedback> findAllById(int id) {
        return feedbackRepository.findAllById(id);
    }

    @Override
    public Feedback findOneById(int id) {
        return feedbackRepository.findOneById(id);
    }

    @Override
    public int save(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    @Override
    public void delete(int id) {
        feedbackRepository.delete(id);
    }
}
