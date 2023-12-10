package com.example.buildmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.buildmanagement.Helpers.DateFormatter;
import com.example.buildmanagement.Models.BuildObject;
import com.example.buildmanagement.Models.Client;
import com.example.buildmanagement.Models.Employee;
import com.example.buildmanagement.Services.BuildObjectServiceImpl;
import com.example.buildmanagement.Services.ClientServiceImpl;
import com.example.buildmanagement.Services.EmployeeServiceImpl;
import com.example.buildmanagement.ui.ObjectsFragment;

import java.math.BigDecimal;

public class ObjectsDetailsActivity extends AppCompatActivity {
    public static TextView startDateTV, endDateTV, statusTV, nameTV, categoryTV, priceTV, phoneTV, indexTV, regionTV, cityTV, streetTV, homeTV, flatTV, finishDateTV;
    public static BuildObject buildObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objects_details);

        Toolbar toolbar = findViewById(R.id.toolBarObjects);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Button updateObjectBtn = findViewById(R.id.updateObjectBtn);
        Button finishButton = findViewById(R.id.buttonObjectFinish);
        Button employeesObjectBtn = findViewById(R.id.objectEmployeesBtn);

        int id = getIntent().getIntExtra("ID", 0);
        int position = getIntent().getIntExtra("POSITION", 0);

        BuildObjectServiceImpl buildObjectService = new BuildObjectServiceImpl();
        EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
        ClientServiceImpl clientService = new ClientServiceImpl();
        buildObject = buildObjectService.findOneById(id);

        Employee foreman = employeeService.findOneById(buildObject.getForemanId());
        Client client = clientService.findOneById(buildObject.getClientId());

        if(buildObject.getStatus().equals("Завершён")) {
            updateObjectBtn.setVisibility(View.GONE);
            finishButton.setVisibility(View.GONE);
            employeesObjectBtn.setVisibility(View.GONE);
        }

        setTitle("Объект № " + id);

        TextView foremanTV = findViewById(R.id.foremanTV);
        TextView clientTV = findViewById(R.id.clientTV);

        if(client != null) {
            String clientMiddleName = "";

            if(client.getMiddleName() != null) {
                clientMiddleName = client.getMiddleName();

            }

            clientTV.setText("Клиент: " + client.getLastName() + " " + client.getFirstName() + " " + clientMiddleName);
        }
        else {
            clientTV.setText("Клиент: Удален");
        }

        if(foreman != null) {
            String foremanMiddleName = "";

            if(foreman.getMiddleName() != null) {
                foremanMiddleName = foreman.getMiddleName();
            }

            foremanTV.setText("Прораб: " + foreman.getLastName() + " " + foreman.getFirstName() + " " + foremanMiddleName);
        }
        else {
            foremanTV.setText("Прораб: Удален");
        }

        startDateTV = findViewById(R.id.objectStartDateTextView);
        endDateTV = findViewById(R.id.objectEndDateTextView);
        statusTV = findViewById(R.id.objectStatusTextView);
        nameTV = findViewById(R.id.objectNameTextView);
        categoryTV = findViewById(R.id.objectCategoryTextView);
        priceTV = findViewById(R.id.objectPriceTextView);
        phoneTV = findViewById(R.id.objectPhoneTextView);
        indexTV = findViewById(R.id.objectIndexRegionTextView);
        cityTV = findViewById(R.id.objectCityTextView);
        streetTV = findViewById(R.id.objectStreetTextView);
        homeTV = findViewById(R.id.objectHomeTextView);
        flatTV = findViewById(R.id.objectFlatTextView);
        regionTV = findViewById(R.id.objectRegionTextView);
        finishDateTV = findViewById(R.id.objectFinishDateTextView);

        startDateTV.setText("Дата начала работ: " + DateFormatter.getFormatedStringDate(buildObject.getStartDate()) + " г.");
        endDateTV.setText("Дата окончания работ: " + DateFormatter.getFormatedStringDate(buildObject.getEndDate()) + " г.");
        statusTV.setText("Статус: " + buildObject.getStatus());
        nameTV.setText("Название: " + buildObject.getObjectName());
        categoryTV.setText("Категория: " + buildObject.getObjectCategory());
        regionTV.setText("Область: " + buildObject.getRegion());

        BigDecimal price = buildObject.getPrice();
        price = price.setScale(2, BigDecimal.ROUND_HALF_UP);

        priceTV.setText("Стоимость работ: " + price  + " руб.");
        phoneTV.setText("Телефон: " + buildObject.getPhone());
        indexTV.setText("Индекс: " + buildObject.getIndexRegion());
        cityTV.setText("Город: " + buildObject.getCity());
        streetTV.setText("Улица: " + buildObject.getStreet());
        homeTV.setText("Дом: " + buildObject.getFlat());

        if(buildObject.getFlat() != 0) {
            flatTV.setText("Квартира: " + buildObject.getFlat());
        }
        else {
            flatTV.setText("Квартира: не укаазана");
        }

        if(buildObject.getFinishDate() == null) {
            finishDateTV.setText("Работы завершены: Ещё в работе");
        }
        else {
            finishDateTV.setText("Работы завершены: " + DateFormatter.getFormatedStringDate(buildObject.getFinishDate()) + " г.");
        }

        toolbar.setNavigationOnClickListener(view -> finish());

        Button showPhotosBtn = findViewById(R.id.showObjectPhotosBtn);

        showPhotosBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, ObjectPhotosActivity.class);
            intent.putExtra("OBJECT_ID", id);
            startActivity(intent);
        });

        BigDecimal finalPrice = price;

        updateObjectBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, BuildObjectFormActivity.class);

            intent.putExtra("ACTION", "UPDATE");
            intent.putExtra("OBJECT_ID", id);
            intent.putExtra("OBJECT_POSITION", position);
            intent.putExtra("OBJECT_START_DATE", DateFormatter.getFormatedStringDate(buildObject.getStartDate()));
            intent.putExtra("OBJECT_END_DATE", DateFormatter.getFormatedStringDate(buildObject.getEndDate()));
            intent.putExtra("OBJECT_STATUS", buildObject.getStatus());
            intent.putExtra("OBJECT_NAME", buildObject.getObjectName());
            intent.putExtra("OBJECT_CATEGORY", buildObject.getObjectCategory());
            intent.putExtra("OBJECT_PRICE", finalPrice.toString());
            intent.putExtra("OBJECT_PHONE", buildObject.getPhone());
            intent.putExtra("OBJECT_REGION", buildObject.getRegion());
            intent.putExtra("OBJECT_INDEX", buildObject.getIndexRegion());
            intent.putExtra("OBJECT_CITY", buildObject.getCity());
            intent.putExtra("OBJECT_STREET", buildObject.getStreet());
            intent.putExtra("OBJECT_HOME", buildObject.getHome());
            intent.putExtra("OBJECT_CLIENT_ID", buildObject.getClientId());
            intent.putExtra("OBJECT_FOREMAN_ID", buildObject.getForemanId());

            if(buildObject.getFlat() != null) {
                intent.putExtra("OBJECT_FLAT", buildObject.getFlat());
            }

            if(buildObject.getFinishDate() != null) {
                intent.putExtra("OBJECT_FINISH_DATE", DateFormatter.getFormatedStringDate(buildObject.getFinishDate()));
            }

            startActivity(intent);
        });

        finishButton.setOnClickListener(view -> {
            buildObjectService.finishWork(id);

            updateObjectBtn.setVisibility(View.GONE);
            finishButton.setVisibility(View.GONE);
            employeesObjectBtn.setVisibility(View.GONE);


            finishDateTV.setText("Дата завершения работ: " + DateFormatter.getFormatedStringDate(buildObject.getFinishDate()) + " г.");
            statusTV.setText("Статус: " + buildObject.getStatus());

            ObjectsFragment.objects.set(position, buildObject);

            if(ObjectsFragment.objects.size() != 0) {
                ObjectsFragment.adapter.notifyItemChanged(ObjectsFragment.objects.size() - 1);
            }
            else {
                ObjectsFragment.adapter.notifyItemChanged(ObjectsFragment.objects.size());
            }
        });

        employeesObjectBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, ObjectEmployeesActivity.class);

            intent.putExtra("OBJECT_ID", id);
            startActivity(intent);
        });
    }

}