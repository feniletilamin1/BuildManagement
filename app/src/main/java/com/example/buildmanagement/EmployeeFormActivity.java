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
import com.example.buildmanagement.Models.Employee;
import com.example.buildmanagement.Services.EmployeeServiceImpl;
import com.example.buildmanagement.ui.EmployeesFragment;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;

public class EmployeeFormActivity extends AppCompatActivity {
    EditText firstNameETV, lastNameETV, middleNameETV, phoneETV, specializationETV, postETV;
    ImageView employeeAvatar;
    String photoDestinationPath;
    Uri uriImagePath;
    Button submitFormBTN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_form);

        Toolbar toolbar = findViewById(R.id.toolBarEmployees);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(view -> finish());

        String action = getIntent().getStringExtra("ACTION");

        EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
        int employeeId = getIntent().getIntExtra("EMPLOYEE_ID", 0);
        int employeePosition = getIntent().getIntExtra("EMPLOYEE_POSITION", 0);

        firstNameETV = findViewById(R.id.employeeFirstNameEditTextView);
        lastNameETV = findViewById(R.id.employeeLastNameEditTextView);
        middleNameETV = findViewById(R.id.employeeMiddleNameEditTextView);
        phoneETV = findViewById(R.id.employeePhoneEditTextView);
        postETV = findViewById(R.id.employeePostEditTextView);
        specializationETV = findViewById(R.id.employeeSpecialityEditTextView);
        submitFormBTN = findViewById(R.id.submitEmployeeFormButton);
        employeeAvatar = findViewById(R.id.employeeFormImageView);

        if(action.equals("ADD")) {
            setTitle("Добавление рабочего");
            submitFormBTN.setText("Добавить рабочего");
        }

        else if(action.equals("UPDATE")) {
            photoDestinationPath = getIntent().getStringExtra("EMPLOYEE_PHOTO");
            firstNameETV.setText(getIntent().getStringExtra("EMPLOYEE_FIRST_NAME"));
            lastNameETV.setText(getIntent().getStringExtra("EMPLOYEE_LAST_NAME"));
            middleNameETV.setText(getIntent().getStringExtra("EMPLOYEE_MIDDLE_NAME"));
            phoneETV.setText(getIntent().getStringExtra("EMPLOYEE_PHONE"));
            postETV.setText(getIntent().getStringExtra("EMPLOYEE_POST"));
            specializationETV.setText(getIntent().getStringExtra("EMPLOYEE_SPECIALIZATION"));
            employeeAvatar.setImageURI(Uri.fromFile(new File(getFilesDir() + File.separator + photoDestinationPath)));
            uriImagePath = Uri.fromFile(new File(getFilesDir() + File.separator + photoDestinationPath));

            setTitle("Редактирование рабочего");
            submitFormBTN.setText("Изменить рабочего");
        }

        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if(action.equals("UPDATE")) {
                            ImageWorker.filedDelete(getFilesDir() + File.separator + photoDestinationPath);
                        }
                        photoDestinationPath = "employeePhotos" + File.separator + ImageWorker.generateUniqeName() + ".jpeg";
                        uriImagePath = data.getData();
                        employeeAvatar.setImageURI(uriImagePath);
                    }
                });

        employeeAvatar.setOnClickListener(view -> {
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
            String post = postETV.getText().toString();
            String specialization = specializationETV.getText().toString();
            String status = "Свободен";

            if(validateData(lastName, firstName, phone, photoDestinationPath, post, specialization)) {
                Employee employee = new Employee(null, firstName, lastName, middleName, phone, status, specialization, post, photoDestinationPath);
                if(action.equals("ADD")) {
                    int newEmployeeId = employeeService.create(employee);
                    employee.setId(newEmployeeId);
                    EmployeesFragment.employees.add(employee);
                    if(EmployeesFragment.employees.size() != 0) {
                        EmployeesFragment.adapter.notifyItemInserted(EmployeesFragment.employees.size() - 1);
                    }
                    else {
                        EmployeesFragment.adapter.notifyItemInserted(EmployeesFragment.employees.size());
                    }
                    try {
                        ImageWorker.saveFile(uriImagePath, getApplicationContext(), photoDestinationPath);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if(action.equals("UPDATE")) {
                    employee.setBuildObjectId(getIntent().getIntExtra("EMPLOYEE_OBJECT_ID", 0));
                    employee.setWorkerStatus(getIntent().getStringExtra("EMPLOYEE_STATUS"));
                    employee.setId(employeeId);
                    employeeService.update(employee);
                    EmployeesFragment.employees.set(employeePosition, employee);

                    EmployeesDetailsActivity.firstNameTV.setText("Имя: " + firstName);
                    EmployeesDetailsActivity.lastNameTV.setText("Фамилия: " + lastName);
                    EmployeesDetailsActivity.avatarIV.setImageURI(uriImagePath);
                    EmployeesDetailsActivity.statusTV.setText("Статус: " + getIntent().getStringExtra("EMPLOYEE_STATUS"));
                    EmployeesDetailsActivity.specializationTV.setText("Специализация: " + specialization);
                    EmployeesDetailsActivity.postTV.setText("Должность: " + post);

                    EmployeesDetailsActivity.employee = employeeService.findOneById(employeeId);

                    if(middleName == null) {
                        EmployeesDetailsActivity.middleNameTV.setText("Отчество: Отсутствует");
                    }
                    else {
                        EmployeesDetailsActivity.middleNameTV.setText("Отчество: " + middleName);
                    }


                    EmployeesDetailsActivity.phoneTV.setText("Телефон: " + phone);

                    EmployeesFragment.adapter.notifyItemChanged(employeePosition);

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

    private boolean validateData(String lastName, String firstName, String phone, String photoPath, String post, String specialization) {
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
        }
        else if (phone.length() == 0) {
            phoneETV.requestFocus();
            phoneETV.setError("Поле обязательно к заполнению");
            return false;
        }
        else if(!phone.matches("^\\+?[0-9\\-\\s]*$")){
            phoneETV.requestFocus();
            phoneETV.setError("Неверный формат телефона");
            return false;
        }
        else if(specialization.length() == 0) {
            specializationETV.requestFocus();
            specializationETV.setError("Поле обязательно к заполнению");
            return false;
        }
        else if(post.length() == 0) {
            postETV.requestFocus();
            postETV.setError("Поле обязательно к заполнению");
            return false;
        }
        return true;
    }
}