package com.example.buildmanagement.DataAdatpters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buildmanagement.Models.ObjectPhoto;
import com.example.buildmanagement.R;
import com.example.buildmanagement.RecyclerViewInterface;

import java.io.File;
import java.util.ArrayList;

public class AA_RVObjectPhotosAdapter extends RecyclerView.Adapter<AA_RVObjectPhotosAdapter.MyViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    private Context context;
    private ArrayList<ObjectPhoto> objectPhotos;

    public AA_RVObjectPhotosAdapter(Context context, ArrayList<ObjectPhoto> objectPhotos, RecyclerViewInterface recyclerViewInterface) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.context = context;
        this.objectPhotos = objectPhotos;
    }

    @NonNull
    @Override
    public AA_RVObjectPhotosAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recycler_view_row_object_photos, parent, false);

        return new AA_RVObjectPhotosAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull AA_RVObjectPhotosAdapter.MyViewHolder holder, int position) {
        holder.photoIV.setImageURI(Uri.fromFile(new File(context.getApplicationContext().getFilesDir() + File.separator + objectPhotos.get(position).getPhotoPath())));
    }

    @Override
    public int getItemCount() {
        return objectPhotos.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView photoIV;
        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            photoIV = itemView.findViewById(R.id.objectPhotoIV);

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
