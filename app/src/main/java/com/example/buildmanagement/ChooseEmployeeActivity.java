package com.example.buildmanagement;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.buildmanagement.Models.Employee;
import com.example.buildmanagement.Services.BuildObjectServiceImpl;
import com.example.buildmanagement.Services.EmployeeServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class ChooseEmployeeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_employee);

        Toolbar toolbar = findViewById(R.id.toolBarChooseEmployee);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(view -> finish());

        int buildObjectId = getIntent().getIntExtra("OBJECT_ID", 0);

        EmployeeServiceImpl employeeService = new EmployeeServiceImpl();

        setTitle("Свободные работники");

        Spinner employeeSpinner = findViewById(R.id.employeesSpinner);

        List<Employee> employeesFreeList = employeeService.getFreeEmployees();
        ArrayList<String> employeesStr = new ArrayList<>();

        for (Employee employee: employeesFreeList) {
            employeesStr.add(String.format("%d | %s %s %s", employee.getId(), employee.getLastName(), employee.getFirstName(), employee.getMiddleName()));
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, employeesStr);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        employeeSpinner.setAdapter(spinnerAdapter);

        Button addNewEmployeeBtn = findViewById(R.id.buttonAddObjectEmployee);

        addNewEmployeeBtn.setOnClickListener(view -> {
            String selectedItem =  employeeSpinner.getSelectedItem().toString();
            String[] strings = selectedItem.split(" ");
            int employeeId = Integer.parseInt(strings[0]);
            Employee currentEmployee = employeeService.findOneById(employeeId);
            currentEmployee.setWorkerStatus("Занят");
            currentEmployee.setBuildObjectId(buildObjectId);

            BuildObjectServiceImpl buildObjectService = new BuildObjectServiceImpl();

            buildObjectService.addEmployeeOnObject(buildObjectId, employeeId);

            ObjectEmployeesActivity.employeeObjectList.add(currentEmployee);
            if(ObjectEmployeesActivity.employeeObjectList.size() != 0) {
                ObjectEmployeesActivity.adapter.notifyItemChanged(ObjectEmployeesActivity.employeeObjectList.size() -1);
            }
            else {
                ObjectEmployeesActivity.adapter.notifyItemChanged(ObjectEmployeesActivity.employeeObjectList.size());
            }

            finish();
        });
    }
}