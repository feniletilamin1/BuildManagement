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

import com.example.buildmanagement.Models.Client;
import com.example.buildmanagement.R;
import com.example.buildmanagement.RecyclerViewInterface;

import java.io.File;
import java.util.ArrayList;

public class AA_RVClientsAdapter extends RecyclerView.Adapter<AA_RVClientsAdapter.MyViewHolder>{
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<Client> clients;

    public AA_RVClientsAdapter(Context context, ArrayList<Client> clients, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.clients = clients;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public AA_RVClientsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recycler_view_row_clients, parent, false);

        return new AA_RVClientsAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull AA_RVClientsAdapter.MyViewHolder holder, int position) {
        holder.tvFullName.setText(String.format("%s %s", clients.get(position).getLastName(),
                clients.get(position).getFirstName()));
        holder.tvPhone.setText(clients.get(position).getPhone());
        holder.avatarImage.setImageURI(Uri.fromFile(new File(context.getApplicationContext().getFilesDir() + File.separator + clients.get(position).getPhoto())));
    }

    @Override
    public int getItemCount() {
        return clients.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView avatarImage;
        TextView tvFullName, tvPhone;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            avatarImage = itemView.findViewById(R.id.avatarClientIV);
            tvFullName = itemView.findViewById(R.id.fullNameClientTV);
            tvPhone = itemView.findViewById(R.id.clientPhoneTV);

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
