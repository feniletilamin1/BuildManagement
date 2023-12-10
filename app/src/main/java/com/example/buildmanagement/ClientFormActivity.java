package com.example.buildmanagement;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.buildmanagement.Helpers.ImageWorker;
import com.example.buildmanagement.Models.Client;
import com.example.buildmanagement.Services.ClientServiceImpl;
import com.example.buildmanagement.ui.ClientsFragment;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;

public class ClientFormActivity extends AppCompatActivity {
    EditText firstNameETV, lastNameETV, middleNameETV, phoneETV;
    ImageView clientAvatar;
    String photoDestinationPath;
    Uri uriImagePath;
    Button submitFormBTN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_form);

        ClientServiceImpl clientService = new ClientServiceImpl();
        int clientId = getIntent().getIntExtra("CLIENT_ID", 0);
        int clientPosition = getIntent().getIntExtra("CLIENT_POSITION", 0);

        firstNameETV = findViewById(R.id.clientFirstNameEditTextView);
        lastNameETV = findViewById(R.id.clientLastNameEditTextView);
        middleNameETV = findViewById(R.id.clientMiddleNameEditTextView);
        phoneETV = findViewById(R.id.clientPhoneEditTextView);
        submitFormBTN = findViewById(R.id.submitClientFormButton);
        clientAvatar = findViewById(R.id.clientFormImageView);

        Toolbar toolbar = findViewById(R.id.toolBarClients);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String action = getIntent().getStringExtra("ACTION");

        if(action.equals("ADD")) {
            setTitle("Добавление клиента");
            submitFormBTN.setText("Добавить клиента");
        }

        else if(action.equals("UPDATE")) {
            photoDestinationPath = getIntent().getStringExtra("CLIENT_PHOTO");
            firstNameETV.setText(getIntent().getStringExtra("CLIENT_FIRST_NAME"));
            lastNameETV.setText(getIntent().getStringExtra("CLIENT_LAST_NAME"));
            middleNameETV.setText(getIntent().getStringExtra("CLIENT_MIDDLE_NAME"));
            phoneETV.setText(getIntent().getStringExtra("CLIENT_PHONE"));
            clientAvatar.setImageURI(Uri.fromFile(new File(getFilesDir() + File.separator + photoDestinationPath)));
            uriImagePath = Uri.fromFile(new File(getFilesDir() + File.separator + photoDestinationPath));

            setTitle("Редактирование клиента");
            submitFormBTN.setText("Изменить клиента");
        }

        toolbar.setNavigationOnClickListener(view -> finish());

        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if(action.equals("UPDATE")) {
                            ImageWorker.filedDelete(getFilesDir() + File.separator + photoDestinationPath);
                        }
                        photoDestinationPath = "clientPhotos" + File.separator + ImageWorker.generateUniqeName() + ".jpeg";
                        uriImagePath = data.getData();
                        clientAvatar.setImageURI(uriImagePath);
                    }
                });

        clientAvatar.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            someActivityResultLauncher.launch(intent);
        });

        submitFormBTN.setOnClickListener(view -> {
            String firstName = firstNameETV.getText().toString();
            String lastName = lastNameETV.getText().toString();
            String middleName;

            if(middleNameETV.getText().toString().equals("")) {
                middleName = null;
            }
            else {
                middleName = middleNameETV.getText().toString();
            }

            String phone = phoneETV.getText().toString();

            if(validateData(lastName, firstName, phone, photoDestinationPath)) {
                Client client = new Client(firstName, lastName, middleName, phone, photoDestinationPath);
                if(action.equals("ADD")) {
                    int newClientId = clientService.create(client);
                    client.setId(newClientId);
                    ClientsFragment.clients.add(client);
                    if(ClientsFragment.clients.size() != 0) {
                        ClientsFragment.adapter.notifyItemInserted(ClientsFragment.clients.size() - 1);
                    }
                    else {
                        ClientsFragment.adapter.notifyItemInserted(ClientsFragment.clients.size());
                    }
                    try {
                        ImageWorker.saveFile(uriImagePath, getApplicationContext(), photoDestinationPath);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if(action.equals("UPDATE")) {
                    client.setId(clientId);
                    clientService.update(client);
                    ClientsFragment.clients.set(clientPosition, client);
                    ClientsDetailsActivity.client = clientService.findOneById(clientId);

                    ClientsDetailsActivity.firstNameTV.setText("Имя: " + firstName);
                    ClientsDetailsActivity.lastNameTV.setText("Фамилия: " + lastName);
                    ClientsDetailsActivity.avatarIV.setImageURI(uriImagePath);

                    ClientsDetailsActivity.phoneTV.setText("Телефон: " + phone);

                    if(middleName == null) {
                        ClientsDetailsActivity.middleNameTV.setText("Отчество: Отсутствует");
                    }
                    else {
                        ClientsDetailsActivity.middleNameTV.setText("Отчество: " + middleName);
                    }

                    ClientsFragment.adapter.notifyItemChanged(clientPosition);

                    try {
                        ImageWorker.saveFile(uriImagePath, getApplicationContext(), photoDestinationPath);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                finish();
            }
        });
    }

    private boolean validateData(String lastName, String firstName, String phone, String photoPath) {
        if(photoPath == null) {
            Snackbar.make(findViewById(R.id.formClientLayout), "Установите аватар!", Snackbar.LENGTH_LONG).show();
            return false;
        }
        else if(lastName.length() == 0) {
            lastNameETV.requestFocus();
            lastNameETV.setError("Поле обязательно к заполнению");
            return false;
        }
        else if(firstName.length() == 0) {
            firstNameETV.requestFocus();
            firstNameETV.setError("Поле обязательно к заполнению");
            return false;
        } else if (phone.length() == 0) {
            phoneETV.requestFocus();
            phoneETV.setError("Поле обязательно к заполнению");
            return false;
        }
        else if(!phone.matches("^\\+?[0-9\\-\\s]*$")){
            phoneETV.requestFocus();
            phoneETV.setError("Неверный формат телефона");
            return false;
        }
        return true;
    }
}