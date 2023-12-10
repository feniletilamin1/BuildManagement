package com.example.buildmanagement.DataAdatpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buildmanagement.Models.Feedback;
import com.example.buildmanagement.R;
import com.example.buildmanagement.RecyclerViewInterface;

import java.util.ArrayList;

public class AA_RVFeedbacksAdapter extends RecyclerView.Adapter<AA_RVFeedbacksAdapter.MyViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<Feedback> feedbacks;

    public AA_RVFeedbacksAdapter(Context context, ArrayList<Feedback> feedbacks, RecyclerViewInterface recyclerViewInterface) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.context = context;
        this.feedbacks = feedbacks;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recycler_view_row_feedbacks, parent, false);

        return new AA_RVFeedbacksAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull AA_RVFeedbacksAdapter.MyViewHolder holder, int position) {
        holder.scoreTV.setText("Оценка: " + feedbacks.get(position).getScore() + "/5");
        holder.textTV.setText("Текст: " + feedbacks.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return feedbacks.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView scoreTV;
        TextView textTV;
        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            scoreTV = itemView.findViewById(R.id.scoreFeedbackTextView);
            textTV = itemView.findViewById(R.id.textFeedbackTextView);

            itemView.setOnLongClickListener(view -> {
                if(recyclerViewInterface != null) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onItemLongClick(position);
                    }
                }
                return true;
            });
        }
    }
}
