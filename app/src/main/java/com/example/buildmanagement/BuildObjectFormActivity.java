package com.example.buildmanagement;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BuildObjectFormActivity extends AppCompatActivity {
    EditText nameETV, categoryETV, startDateETV, endDateETV, phoneETV, priceETV, indexETV, regionETV, cityETV, streetETV, homeETV, flatETV;
    Button submitFormBTN;
    Spinner clientsSpinner;
    Spinner employeeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_object_form);

        EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
        BuildObjectServiceImpl buildObjectService = new BuildObjectServiceImpl();
        ClientServiceImpl clientService = new ClientServiceImpl();

        nameETV= findViewById(R.id.objectNameEditTextView);
        categoryETV = findViewById(R.id.objectCategoryEditTextView);
        startDateETV = findViewById(R.id.objectStartDateEditTextView);
        endDateETV = findViewById(R.id.objectEndDateEditTextView);
        phoneETV = findViewById(R.id.objectPhoneEditTextView);
        priceETV = findViewById(R.id.objectPriceEditTextView);
        indexETV = findViewById(R.id.objectIndexEditTextView);
        regionETV = findViewById(R.id.objectRegionEditTextView);
        cityETV = findViewById(R.id.objectCityEditTextView);
        streetETV = findViewById(R.id.objectStreetEditTextView);
        homeETV = findViewById(R.id.objectHomeEditTextView);
        flatETV = findViewById(R.id.objectFlatEditTextView);
        submitFormBTN = findViewById(R.id.submitObjectFormButton);

        int objectId = getIntent().getIntExtra("OBJECT_ID", 0);
        int objectPosition = getIntent().getIntExtra("OBJECT_POSITION", 0);
        String objectStatus = getIntent().getStringExtra("OBJECT_STATUS");
        String objectFinishDate = getIntent().getStringExtra("OBJECT_FINISH_DATE");
        int clientId = getIntent().getIntExtra("OBJECT_CLIENT_ID", 0);
        int foremanId = getIntent().getIntExtra("OBJECT_FOREMAN_ID", 0);

        Toolbar toolbar = findViewById(R.id.toolBarFormObjects);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(view -> finish());
        String action = getIntent().getStringExtra("ACTION");

        employeeSpinner = findViewById(R.id.employeeSpinner);

        List<Employee> employeesForemanList = employeeService.getForemanList();
        ArrayList<String> employeesStr = new ArrayList<>();

        for (Employee employee: employeesForemanList) {
            String middleName = employee.getMiddleName();
            if(middleName == null) {
                middleName = "";
            }
            employeesStr.add(String.format("%d | %s %s %s", employee.getId(), employee.getLastName(), employee.getFirstName(), middleName));
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, employeesStr);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        employeeSpinner.setAdapter(spinnerAdapter);

        clientsSpinner = findViewById(R.id.clientsSpinner);

        List<Client> clientList = clientService.findAll();
        ArrayList<String> clientStr = new ArrayList<>();

        for (Client client: clientList) {
            String middleName = client.getMiddleName();
            if(middleName == null) {
                middleName = "";
            }
            clientStr.add(String.format("%d | %s %s %s", client.getId(), client.getLastName(), client.getFirstName(), middleName));
        }

        ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, clientStr);
        spinnerAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clientsSpinner.setAdapter(spinnerAdapter2);

        startDateETV.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view1, year, month, dayOfMonth) -> {
                String dayString = String.valueOf(dayOfMonth);
                String monthString = String.valueOf(month);
                if(month < 10) {
                    monthString = "0" + month;
                }
                if(dayOfMonth < 10) {
                    dayString = "0" + dayOfMonth;
                }
                startDateETV.setText(String.format("%s-%s-%d", dayString, monthString, year));
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

            datePickerDialog.show();
        });

        endDateETV.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    String dayString = String.valueOf(dayOfMonth);
                    String monthString = String.valueOf(month);
                    if(month < 10) {
                        monthString = "0" + month;
                    }
                    if(dayOfMonth < 10) {
                        dayString = "0" + dayOfMonth;
                    }
                    endDateETV.setText(String.format("%s-%s-%d", dayString, monthString, year));
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

            datePickerDialog.show();
        });

        if(action.equals("ADD")) {
            setTitle("Добавление объекта");
            submitFormBTN.setText("Добавить объект");
        }

        else if(action.equals("UPDATE")) {
            clientsSpinner.setVisibility(View.GONE);
            employeeSpinner.setVisibility(View.GONE);
            findViewById(R.id.objectFormClientTextView).setVisibility(View.GONE);
            findViewById(R.id.objectFormEmployeeTextView).setVisibility(View.GONE);

            nameETV.setText(getIntent().getStringExtra("OBJECT_NAME"));
            categoryETV.setText(getIntent().getStringExtra("OBJECT_CATEGORY"));
            startDateETV.setText(getIntent().getStringExtra("OBJECT_START_DATE"));
            endDateETV.setText(getIntent().getStringExtra("OBJECT_END_DATE"));
            phoneETV.setText(getIntent().getStringExtra("OBJECT_PHONE"));
            priceETV.setText(getIntent().getStringExtra("OBJECT_PRICE"));
            indexETV.setText(String.valueOf(getIntent().getIntExtra("OBJECT_INDEX", 0)));
            regionETV.setText(getIntent().getStringExtra("OBJECT_REGION"));
            cityETV.setText(getIntent().getStringExtra("OBJECT_CITY"));
            streetETV.setText(getIntent().getStringExtra("OBJECT_STREET"));
            homeETV.setText(getIntent().getStringExtra("OBJECT_HOME"));
            if (getIntent().getIntExtra("OBJECT_FLAT", 0) != 0) {
                flatETV.setText(String.valueOf(getIntent().getIntExtra("OBJECT_FLAT", 0)));
            }

            setTitle("Редактирование объекта");
            submitFormBTN.setText("Изменить объект");
        }

        submitFormBTN.setOnClickListener(view -> {
            String name = nameETV.getText().toString();
            String category = categoryETV.getText().toString();
            String startDate = startDateETV.getText().toString();
            String endDate = endDateETV.getText().toString();
            String phone = phoneETV.getText().toString();
            String price = priceETV.getText().toString();
            String index = indexETV.getText().toString();
            String region = regionETV.getText().toString();
            String city = cityETV.getText().toString();
            String street = streetETV.getText().toString();
            String home = homeETV.getText().toString();
            String flat = flatETV.getText().toString();

            String[] clientArr = new String[0];
            String[] employeeArr = new String[0];

            if(action.equals("ADD")) {
                clientArr = clientsSpinner.getSelectedItem().toString().split("");
                employeeArr = employeeSpinner.getSelectedItem().toString().split("");
            }

            if(validateData(action, name, category, startDate, endDate, phone, price, index, region, city, street, home)) {
                BuildObject buildObject = new BuildObject(DateFormatter.stringToDateFromPicker(startDate),
                        DateFormatter.stringToDateFromPicker(endDate), "В работе", name, category,
                        BigDecimal.valueOf(Double.parseDouble(price)), phone, Integer.parseInt(index), region,
                        city, home, street, null,
                        null, 0, 0);

                if(!flat.equals("")) {
                    buildObject.setFlat(Integer.parseInt(flat));
                }

                if(action.equals("ADD")) {
                    buildObject.setForemanId(Integer.parseInt(employeeArr[0]));
                    buildObject.setClientId(Integer.parseInt(clientArr[0]));
                    int newObjectId = buildObjectService.create(buildObject);
                    buildObjectService.addEmployeeOnObject(newObjectId, Integer.parseInt(employeeArr[0]));
                    buildObject.setId(newObjectId);
                    ObjectsFragment.objects.add(buildObject);
                    if(ObjectsFragment.objects.size() != 0) {
                        ObjectsFragment.adapter.notifyItemInserted(ObjectsFragment.objects.size() - 1);
                    }
                    else {
                        ObjectsFragment.adapter.notifyItemInserted(ObjectsFragment.objects.size());
                    }
                }

                else if(action.equals("UPDATE")) {

                    if(objectFinishDate != null) {
                        buildObject.setFinishDate(DateFormatter.stringToDateFromPicker(objectFinishDate));
                    }

                    buildObject.setId(objectId);
                    buildObject.setStatus(objectStatus);
                    buildObject.setClientId(clientId);
                    buildObject.setForemanId(foremanId);
                    buildObjectService.update(buildObject);

                    ObjectsFragment.objects.set(objectPosition, buildObject);
                    ObjectsDetailsActivity.nameTV.setText("Название: " + name);
                    ObjectsDetailsActivity.categoryTV.setText("Категория: " + category);
                    ObjectsDetailsActivity.startDateTV.setText("Дата начала работ: " + startDate + " г.");
                    ObjectsDetailsActivity.endDateTV.setText("Дата окончания работа: " + endDate + "г.");
                    ObjectsDetailsActivity.phoneTV.setText("Телефон: " + phone);
                    ObjectsDetailsActivity.priceTV.setText("Стоимость работ: " + price);
                    ObjectsDetailsActivity.indexTV.setText("Индекс: " + index);
                    ObjectsDetailsActivity.regionTV.setText("Регион: " + region);
                    ObjectsDetailsActivity.cityTV.setText("Город: " + city);
                    ObjectsDetailsActivity.streetTV.setText("Улица: " + street);
                    ObjectsDetailsActivity.homeTV.setText("Дом: " + home);

                    ObjectsDetailsActivity.buildObject = buildObjectService.findOneById(objectId);

                    if(!flat.equals("")) {
                        ObjectsDetailsActivity.flatTV.setText("Квартира: " + flat);
                    }

                    else {
                        ObjectsDetailsActivity.flatTV.setText("Квартира: не указана");
                    }

                    ObjectsFragment.adapter.notifyItemChanged(objectPosition);
                }

                finish();
            }
        });
    }

    private boolean validateData(String action, String name, String category, String startDate, String endDate, String phone, String price, String index, String region, String city, String street, String home) {
        if(name.length() == 0) {
            nameETV.requestFocus();
            nameETV.setError("Поле обязательно к заполнению");
            return false;
        }
        else if(clientsSpinner.getSelectedItem() == null && action.equals("ADD")) {
            clientsSpinner.requestFocus();
            return false;
        }
        else if(category.length() == 0) {
            categoryETV.requestFocus();
            categoryETV.setError("Поле обязательно к заполнению");
            return false;
        }
        else if(employeeSpinner.getSelectedItem() == null && action.equals("ADD")) {
            employeeSpinner.requestFocus();
            return false;
        }
        else if(startDate.length() == 0) {
            startDateETV.requestFocus();
            startDateETV.setError("Поле обязательно к заполнению");
            return false;
        }
        else if(!startDate.matches("^\\d{2}-\\d{2}-\\d{4}$")) {
            startDateETV.requestFocus();
            startDateETV.setError("Неверный формат даты");
        }
        else if(endDate.length() == 0) {
            endDateETV.requestFocus();
            endDateETV.setError("Поле обязательно к заполнению");
            return false;
        }
        else if(!endDate.matches("^\\d{2}-\\d{2}-\\d{4}$")) {
            endDateETV.requestFocus();
            endDateETV.setError("Неверный формат даты");
        }
        else if(phone.length() == 0) {
            phoneETV.requestFocus();
            phoneETV.setError("Поле обязательно к заполнению");
            return false;
        }
        else if(!phone.matches("^\\+?[0-9\\-\\s]*$")){
            phoneETV.requestFocus();
            phoneETV.setError("Неверный формат телефона");
            return false;
        }
        else if(price.length() == 0) {
            priceETV.requestFocus();
            priceETV.setError("Поле обязательно к заполнению");
            return false;
        }
        else if(index.length() == 0) {
            indexETV.requestFocus();
            indexETV.setError("Поле обязательно к заполнению");
            return false;
        }
        else if(!index.matches("\\d{6}")) {
            indexETV.requestFocus();
            indexETV.setError("Неверный формат индекса");
            return false;
        }
        else if(region.length() == 0) {
            regionETV.requestFocus();
            regionETV.setError("Поле обязательно к заполнению");
            return false;
        }
        else if(city.length() == 0) {
            cityETV.requestFocus();
            cityETV.setError("Поле обязательно к заполнению");
            return false;
        }
        else if(street.length() == 0) {
            streetETV.requestFocus();
            streetETV.setError("Поле обязательно к заполнению");
            return false;
        }
        else if(home.length() == 0) {
            homeETV.requestFocus();
            homeETV.setError("Поле обязательно к заполнению");
            return false;
        }

        return true;
    }
}