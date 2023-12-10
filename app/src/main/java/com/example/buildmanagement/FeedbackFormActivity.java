package com.example.buildmanagement;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.buildmanagement.Models.Feedback;
import com.example.buildmanagement.Services.FeedbackServiceImpl;

public class FeedbackFormActivity extends AppCompatActivity {
    EditText textETV, scoreETV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_form);

        FeedbackServiceImpl feedbackService = new FeedbackServiceImpl();
        int employeeId = getIntent().getIntExtra("EMPLOYEE_ID", 0);

        textETV = findViewById(R.id.feedbackTextEditTextView);
        scoreETV = findViewById(R.id.feedbackScoreEditTextView);

        Toolbar toolbar = findViewById(R.id.toolBarAddFeedback);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Добавление отзыва:");

        Button submitFormBtn = findViewById(R.id.submitFeedbackFormButton);

        submitFormBtn.setOnClickListener(view -> {
            String text = textETV.getText().toString();
            String score = scoreETV.getText().toString();

            if(validateData(score)) {
                Feedback feedback = new Feedback(text, Integer.parseInt(score), employeeId);

                int newFeedbackId = feedbackService.save(feedback);
                feedback.setId(newFeedbackId);

                FeedbacksActivity.feedbacksList.add(feedback);
                if(FeedbacksActivity.feedbacksList.size() != 0) {
                    FeedbacksActivity.adapter.notifyItemInserted(FeedbacksActivity.feedbacksList.size() - 1);
                }
                else {
                    FeedbacksActivity.adapter.notifyItemInserted(FeedbacksActivity.feedbacksList.size());
                }

                finish();
            }
        });

        toolbar.setNavigationOnClickListener(view -> finish());
    }

    private boolean validateData(String score) {
        if(score.length() == 0) {
            scoreETV.requestFocus();
            scoreETV.setError("Поле обязательно к заполнению");
            return false;
        }
        else if (!isNumeric(score)) {
            scoreETV.requestFocus();
            scoreETV.setError("Введите число");
            return false;
        }
        else if(Integer.parseInt(score) < 1 | Integer.parseInt(score) > 5) {
            scoreETV.requestFocus();
            scoreETV.setError("Введите число не меньше 1 и не больше 5");
            return false;
        }
        return true;
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}