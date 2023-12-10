package com.example.buildmanagement.DataAdatpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buildmanagement.Models.BuildObject;
import com.example.buildmanagement.R;
import com.example.buildmanagement.RecyclerViewInterface;

import java.math.BigDecimal;
import java.util.ArrayList;

public class AA_RVObjectsAdapter extends RecyclerView.Adapter<AA_RVObjectsAdapter.MyViewHolder> {
    RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<BuildObject> objects;

    public AA_RVObjectsAdapter(Context context, ArrayList<BuildObject> objects, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.objects = objects;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public AA_RVObjectsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recycler_view_row_objects, parent, false);

        return new AA_RVObjectsAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull AA_RVObjectsAdapter.MyViewHolder holder, int position) {
        holder.tvName.setText(String.format("%s", objects.get(position).getObjectName()));
        holder.tvStatus.setText(String.format("%s", objects.get(position).getStatus()));
        BigDecimal price = objects.get(position).getPrice();
        price = price.setScale(2, BigDecimal.ROUND_HALF_UP);
        holder.tvPrice.setText(String.format("%s руб.", price));
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView avatarImage;
        TextView tvName, tvPrice, tvStatus;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            avatarImage = itemView.findViewById(R.id.avatarClientIV);
            tvName = itemView.findViewById(R.id.nameObjectTV);
            tvPrice = itemView.findViewById(R.id.priceObjectTV);
            tvStatus = itemView.findViewById(R.id.statusObjectTV);

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
