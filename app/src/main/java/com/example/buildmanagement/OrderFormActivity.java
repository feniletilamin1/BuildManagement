package com.example.buildmanagement;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.buildmanagement.Models.Order;
import com.example.buildmanagement.Services.OrderServiceImpl;
import com.example.buildmanagement.ui.OrdersFragment;

import java.math.BigDecimal;
import java.util.Calendar;

public class OrderFormActivity extends AppCompatActivity {
    EditText nameETV, priceETV;
    Button submitFormBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_form);

        OrderServiceImpl orderService = new OrderServiceImpl();
        int orderId = getIntent().getIntExtra("ORDER_ID", 0);
        int orderPosition = getIntent().getIntExtra("ORDER_POSITION", 0);

        submitFormBTN = findViewById(R.id.submitOrderFormButton);
        nameETV = findViewById(R.id.orderNameEditText);
        priceETV = findViewById(R.id.orderPriceEditText);

        Toolbar toolbar = findViewById(R.id.toolBarOrderForm);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String action = getIntent().getStringExtra("ACTION");
        if(action.equals("ADD")) {
            setTitle("Добавление заказа");
            submitFormBTN.setText("Добавить заказа");
        }
        else if(action.equals("UPDATE")) {
            nameETV.setText(getIntent().getStringExtra("ORDER_NAME"));
            priceETV.setText(getIntent().getStringExtra("ORDER_PRICE"));
            setTitle("Редактирование заказа");
            submitFormBTN.setText("Изменить заказа");
        }

        toolbar.setNavigationOnClickListener(view -> finish());

        submitFormBTN.setOnClickListener(view -> {
            String name = nameETV.getText().toString();
            String price = priceETV.getText().toString();

            if(validateData(name, price)) {
                Order order = new Order(Calendar.getInstance(), name, BigDecimal.valueOf(Double.parseDouble(price)));
                if(action.equals("ADD")) {
                    int newOrderId = orderService.create(order);
                    order.setId(newOrderId);
                    OrdersFragment.orders.add(order);
                    if(OrdersFragment.orders.size() != 0) {
                        OrdersFragment.adapter.notifyItemInserted(OrdersFragment.orders.size() - 1);
                    }
                    else {
                        OrdersFragment.adapter.notifyItemInserted(OrdersFragment.orders.size());
                    }
                }
                else if(action.equals("UPDATE")) {
                    order.setId(orderId);
                    orderService.update(order);
                    OrdersFragment.orders.set(orderPosition, order);

                    OrdersDetailsActivity.nameTV.setText(name);
                    OrdersDetailsActivity.priceTV.setText(price + " руб.");
                    OrdersDetailsActivity.order = orderService.findOneById(orderId);

                    OrdersFragment.adapter.notifyItemChanged(orderPosition);

                }
                finish();
            }
        });
    }

    private boolean validateData(String name, String price) {
        if(name.length() == 0) {
            nameETV.requestFocus();
            nameETV.setError("Поле обязательно к заполнению");
            return false;
        }
        else if(price.length() == 0) {
            priceETV.requestFocus();
            priceETV.setError("Поле обязательно к заполнению");
            return false;
        }
        return true;
    }
}