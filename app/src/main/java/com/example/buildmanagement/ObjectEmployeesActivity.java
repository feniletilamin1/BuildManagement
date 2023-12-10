package com.example.buildmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buildmanagement.DataAdatpters.AA_RVWorkersAdapter;
import com.example.buildmanagement.Models.Employee;
import com.example.buildmanagement.Services.BuildObjectServiceImpl;
import com.example.buildmanagement.Services.EmployeeServiceImpl;

import java.util.ArrayList;

public class ObjectEmployeesActivity extends AppCompatActivity implements RecyclerViewInterface {
    public static ArrayList<Employee> employeeObjectList;
    public static AA_RVWorkersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_employees);

        Toolbar toolbar = findViewById(R.id.toolBarObjectEmployees);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        int buildObjectId = getIntent().getIntExtra("OBJECT_ID", 0);

        setTitle("Рабочие объекта №" + buildObjectId);

        EmployeeServiceImpl employeeService = new EmployeeServiceImpl();

        employeeObjectList = new ArrayList(employeeService.getObjectEmployees(buildObjectId));

        RecyclerView recyclerView = findViewById(R.id.mRecyclerViewObjectEmployees);
        adapter = new AA_RVWorkersAdapter(this, employeeObjectList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        toolbar.setNavigationOnClickListener(view -> finish());

        Button addNewEmployeeBtn = findViewById(R.id.addNewEmployeeOnObjectBtn);

        addNewEmployeeBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, ChooseEmployeeActivity.class);

            intent.putExtra("OBJECT_ID", buildObjectId);

            startActivity(intent);
        });

    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onItemLongClick(int position) {
        int employeeId = employeeObjectList.get(position).getId();
        BuildObjectServiceImpl buildObjectService = new BuildObjectServiceImpl();
        buildObjectService.removeEmployeeFromObject(employeeId);
        employeeObjectList.remove(position);
        adapter.notifyItemRemoved(position);
    }
}