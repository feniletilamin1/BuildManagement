package com.example.buildmanagement.DataAdatpters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buildmanagement.Models.Employee;
import com.example.buildmanagement.R;
import com.example.buildmanagement.RecyclerViewInterface;

import java.io.File;
import java.util.ArrayList;

public class AA_RVWorkersAdapter extends RecyclerView.Adapter<AA_RVWorkersAdapter.MyViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<Employee> employees;

    public AA_RVWorkersAdapter(Context context, ArrayList<Employee> employees, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.employees = employees;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recycler_view_row_workers, parent, false);

        return new AA_RVWorkersAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull AA_RVWorkersAdapter.MyViewHolder holder, int position) {
        holder.tvFullName.setText(String.format("%s %s", employees.get(position).getLastName(),
                employees.get(position).getFirstName()));
        holder.tvSpecialization.setText(employees.get(position).getSpeciality());
        holder.tvStatus.setText(employees.get(position).getWorkerStatus());
        holder.avatarImage.setImageURI(Uri.fromFile(new File(context.getFilesDir() +
                File.separator + employees.get(position).getPhoto())));
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView avatarImage;
        TextView tvFullName, tvSpecialization, tvStatus;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            avatarImage = itemView.findViewById(R.id.avatarWorkerIV);
            tvFullName = itemView.findViewById(R.id.fullNameWorkerTV);
            tvSpecialization = itemView.findViewById(R.id.specializationWorkerTV);
            tvStatus = itemView.findViewById(R.id.statusTV);

            itemView.setOnClickListener(view -> {
                if(recyclerViewInterface != null) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onItemClick(position);
                    }
                }
            });

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
