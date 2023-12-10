package com.example.buildmanagement.DataAdatpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buildmanagement.Models.Order;
import com.example.buildmanagement.R;
import com.example.buildmanagement.RecyclerViewInterface;

import java.math.BigDecimal;
import java.util.ArrayList;

public class AA_RVOrdersAdapter extends RecyclerView.Adapter<AA_RVOrdersAdapter.MyViewHolder>{
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<Order> orders;

    public AA_RVOrdersAdapter(Context context, ArrayList<Order> orders, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.orders = orders;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public AA_RVOrdersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recycler_view_row_orders, parent, false);

        return new AA_RVOrdersAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull AA_RVOrdersAdapter.MyViewHolder holder, int position) {
        holder.tvName.setText(String.format("%s", orders.get(position).getName()));
        BigDecimal price = orders.get(position).getPrice() ;
        price = price.setScale(2, BigDecimal.ROUND_HALF_UP);
        holder.tvPrice.setText(price + " руб.");
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView avatarImage;
        TextView tvName, tvPrice;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            avatarImage = itemView.findViewById(R.id.avatarOrderIV);
            tvName = itemView.findViewById(R.id.nameObjectTV);
            tvPrice = itemView.findViewById(R.id.priceObjectTV);

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
