package com.example.buildmanagement;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.buildmanagement.Models.BuildObject;
import com.example.buildmanagement.Models.Employee;
import com.example.buildmanagement.Services.BuildObjectServiceImpl;
import com.example.buildmanagement.Services.EmployeeServiceImpl;

import java.io.File;

public class EmployeesDetailsActivity extends AppCompatActivity {
    public static TextView lastNameTV, firstNameTV, middleNameTV, phoneTV, postTV, specializationTV, statusTV;
    public static ImageView avatarIV;
    public static Employee employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);

        Toolbar toolbar = findViewById(R.id.toolBarWorkers);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        int id = getIntent().getIntExtra("ID", 0);
        int position = getIntent().getIntExtra("POSITION", 0);

        EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
        employee = employeeService.findOneById(id);

        setTitle("Работник № " + id);

        lastNameTV = findViewById(R.id.workerLastNameTextView);
        firstNameTV = findViewById(R.id.workerFirstNameTextView);
        middleNameTV = findViewById(R.id.workerMiddleNameTextView);
        phoneTV = findViewById(R.id.workerPhoneTextView);
        postTV = findViewById(R.id.workerPostTextView);
        specializationTV = findViewById(R.id.workerSpecializationTextView);
        statusTV= findViewById(R.id.workerStatusTextView);
        avatarIV = findViewById(R.id.workerImageView);
        TextView buildObjectTV = findViewById(R.id.workerBuildObjectTextView);

        if(employee.getBuildObjectId() != 0) {
            BuildObjectServiceImpl buildObjectService = new BuildObjectServiceImpl();
            BuildObject buildObject = buildObjectService.findOneById(employee.getBuildObjectId());
            buildObjectTV.setText("Объект: " + buildObject.getObjectName());
        }
        else {
            buildObjectTV.setText("Объект: " + "отсутствует");
        }

        lastNameTV.setText("Фамилия: " + employee.getLastName());
        firstNameTV.setText("Имя: " + employee.getFirstName());

        if(employee.getMiddleName() != null) {
            middleNameTV.setText("Отчество: " + employee.getMiddleName());
        }
        else {
            middleNameTV.setText("Отчество: Отсутствует");
        }

        phoneTV.setText("Телефон: " + employee.getPhone());
        postTV.setText("Должность: " + employee.getPost());
        specializationTV.setText("Специализация: " + employee.getSpeciality());
        statusTV.setText("Статус: " + employee.getWorkerStatus());
        avatarIV.setImageURI(Uri.fromFile(new File(getFilesDir() + File.separator + employee.getPhoto())));

        toolbar.setNavigationOnClickListener(view -> finish());

        Button updateEmployee = findViewById(R.id.updateEmployeeBtn);

        updateEmployee.setOnClickListener(view -> {
            Intent intent = new Intent(this, EmployeeFormActivity.class);

            intent.putExtra("ACTION", "UPDATE");
            intent.putExtra("EMPLOYEE_PHOTO", employee.getPhoto());
            intent.putExtra("EMPLOYEE_ID", employee.getId());
            intent.putExtra("EMPLOYEE_POSITION", position);
            intent.putExtra("EMPLOYEE_LAST_NAME", employee.getLastName());
            intent.putExtra("EMPLOYEE_FIRST_NAME", employee.getFirstName());
            intent.putExtra("EMPLOYEE_MIDDLE_NAME", employee.getMiddleName());
            intent.putExtra("EMPLOYEE_PHONE", employee.getPhone());
            intent.putExtra("EMPLOYEE_POST", employee.getPost());
            intent.putExtra("EMPLOYEE_SPECIALIZATION", employee.getSpeciality());
            intent.putExtra("EMPLOYEE_OBJECT_ID", employee.getBuildObjectId());
            intent.putExtra("EMPLOYEE_STATUS", employee.getWorkerStatus());

            startActivity(intent);
        });

        Button feedbacksBtn = findViewById(R.id.employeeFeedbacksBtn);

        feedbacksBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, FeedbacksActivity.class);

            intent.putExtra("EMPLOYEE_ID", employee.getId());

            startActivity(intent);
        });
    }
}