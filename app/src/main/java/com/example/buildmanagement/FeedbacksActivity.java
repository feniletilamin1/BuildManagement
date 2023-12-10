package com.example.buildmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buildmanagement.DataAdatpters.AA_RVFeedbacksAdapter;
import com.example.buildmanagement.Models.Feedback;
import com.example.buildmanagement.Services.FeedbackServiceImpl;

import java.util.ArrayList;

public class FeedbacksActivity extends AppCompatActivity implements RecyclerViewInterface {
    public static ArrayList<Feedback> feedbacksList;
    public static AA_RVFeedbacksAdapter adapter;
    private FeedbackServiceImpl feedbackService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedbacks);

        Toolbar toolbar = findViewById(R.id.toolBarFeedbacks);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Отзывы о работнике: ");

        int employeeId = getIntent().getIntExtra("EMPLOYEE_ID", 0);

        feedbackService = new FeedbackServiceImpl();
        feedbacksList = new ArrayList<>(feedbackService.findAllById(employeeId));

        RecyclerView recyclerView = findViewById(R.id.mRecyclerViewFeedbacks);
        adapter = new AA_RVFeedbacksAdapter(this, feedbacksList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button addFeedbackBtn = findViewById(R.id.addFeedbackBtn);

        addFeedbackBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, FeedbackFormActivity.class);

            intent.putExtra("EMPLOYEE_ID", employeeId);

            startActivity(intent);
        });

        toolbar.setNavigationOnClickListener(view -> finish());
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onItemLongClick(int position) {
        int feedbackId = feedbacksList.get(position).getId();
        feedbackService.delete(feedbackId);
        feedbacksList.remove(position);
        adapter.notifyItemRemoved(position);
    }
}