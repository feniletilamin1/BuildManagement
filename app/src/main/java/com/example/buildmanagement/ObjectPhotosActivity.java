package com.example.buildmanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buildmanagement.DataAdatpters.AA_RVObjectPhotosAdapter;
import com.example.buildmanagement.Helpers.ImageWorker;
import com.example.buildmanagement.Models.ObjectPhoto;
import com.example.buildmanagement.Services.BuildObjectServiceImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ObjectPhotosActivity extends AppCompatActivity implements RecyclerViewInterface{
    private ArrayList<ObjectPhoto> objectPhotoList;
    private AA_RVObjectPhotosAdapter adapter;
    private BuildObjectServiceImpl buildObjectService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_photos);

        Toolbar toolbar = findViewById(R.id.toolBarObjectPhotos);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Фотографии объекта: ");

        int objectId = getIntent().getIntExtra("OBJECT_ID", 0);

        buildObjectService = new BuildObjectServiceImpl();

        objectPhotoList = new ArrayList<>(buildObjectService.getObjectPhotos(objectId));

        RecyclerView recyclerView = findViewById(R.id.mRecyclerViewObjectPhotos);
        adapter = new AA_RVObjectPhotosAdapter(ObjectPhotosActivity.this, objectPhotoList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ObjectPhotosActivity.this));

        Button addNewPhotoBtn = findViewById(R.id.addNewPhotoObjectBtn);

        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        try {
                            String photoPath = "objectImages" + File.separator + ImageWorker.generateUniqeName() + ".jpeg";
                            ImageWorker.saveFile(data.getData(), getApplicationContext(), photoPath);
                            ObjectPhoto objectPhoto = new ObjectPhoto(objectId, photoPath);
                            int id = (int)buildObjectService.addObjectPhoto(objectPhoto);

                            objectPhoto.setId(id);
                            objectPhotoList.add(objectPhoto);
                            if(objectPhotoList.size() != 0) {
                                adapter.notifyItemInserted(objectPhotoList.size() - 1);
                            }
                            else {
                                adapter.notifyItemInserted(objectPhotoList.size());
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

        addNewPhotoBtn.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            someActivityResultLauncher.launch(intent);
        });

        toolbar.setNavigationOnClickListener(view -> finish());
    }


    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onItemLongClick(int position) {
        int photoId = objectPhotoList.get(position).getId();
        String photoPath = objectPhotoList.get(position).getPhotoPath();
        buildObjectService.deletePhoto(photoId);
        objectPhotoList.remove(position);
        adapter.notifyItemRemoved(position);
        ImageWorker.filedDelete(getFilesDir() + File.separator + photoPath);
    }
}