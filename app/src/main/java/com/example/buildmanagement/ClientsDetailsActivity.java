package com.example.buildmanagement;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.buildmanagement.Models.Client;
import com.example.buildmanagement.Services.ClientServiceImpl;

import java.io.File;

public class ClientsDetailsActivity extends AppCompatActivity {

    public static TextView lastNameTV, firstNameTV, middleNameTV, phoneTV;
    public static ImageView avatarIV;

    public static Client client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients_details);

        Toolbar toolbar = findViewById(R.id.toolBarClients);
        setSupportActionBar(toolbar);
        int position = getIntent().getIntExtra("POSITION", 0);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        int id = getIntent().getIntExtra("ID", 0);

        ClientServiceImpl clientService = new ClientServiceImpl();

        client = clientService.findOneById(id);

        setTitle("Клиент № " + id);

        lastNameTV = findViewById(R.id.clientLastNameTextView);
        firstNameTV = findViewById(R.id.clientFirstNameTextView);
        middleNameTV = findViewById(R.id.clientMiddleNameTextView);
        phoneTV = findViewById(R.id.clientPhoneTextView);
        avatarIV = findViewById(R.id.clientImageView);

        lastNameTV.setText("Фамилия: " + client.getLastName());
        firstNameTV.setText("Имя: " + client.getFirstName());
        if(client.getMiddleName() == null) {
            middleNameTV.setText("Отчество: Отсутствует");
        }
        else {
            middleNameTV.setText("Отчество: " + client.getMiddleName());
        }
        phoneTV.setText("Телефон: " + client.getPhone());
        avatarIV.setImageURI(Uri.fromFile(new File(getFilesDir() + File.separator + client.getPhoto())));

        toolbar.setNavigationOnClickListener(view -> finish());

        Button updateClientBtn = findViewById(R.id.updateClientBtn);

        updateClientBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, ClientFormActivity.class);

            intent.putExtra("ACTION", "UPDATE");
            intent.putExtra("CLIENT_PHOTO", client.getPhoto());
            intent.putExtra("CLIENT_ID", client.getId());
            intent.putExtra("CLIENT_POSITION", position);
            intent.putExtra("CLIENT_LAST_NAME", client.getLastName());
            intent.putExtra("CLIENT_FIRST_NAME", client.getFirstName());
            intent.putExtra("CLIENT_MIDDLE_NAME", client.getMiddleName());
            intent.putExtra("CLIENT_PHONE", client.getPhone());

            startActivity(intent);
        });
    }
}