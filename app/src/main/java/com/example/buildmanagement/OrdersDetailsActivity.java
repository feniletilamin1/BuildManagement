    package com.example.buildmanagement;

    import android.content.Intent;
    import android.os.Bundle;
    import android.widget.Button;
    import android.widget.TextView;

    import androidx.appcompat.app.AppCompatActivity;
    import androidx.appcompat.widget.Toolbar;

    import com.example.buildmanagement.Helpers.DateFormatter;
    import com.example.buildmanagement.Models.Order;
    import com.example.buildmanagement.Services.OrderServiceImpl;

    import java.math.BigDecimal;

    public class OrdersDetailsActivity extends AppCompatActivity {
        public static  TextView nameTV, priceTV;
        public static Order order;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_orders_details);

            Toolbar toolbar = findViewById(R.id.toolbarOrders);
            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            int id = getIntent().getIntExtra("ID", 0);
            int orderPosition = getIntent().getIntExtra("POSITION", 0);

            OrderServiceImpl orderService = new OrderServiceImpl();
            order = orderService.findOneById(id);


            setTitle("Заказ № " + id);

            nameTV = findViewById(R.id.orderNameTextView);
            priceTV = findViewById(R.id.orderPriceTextView);
            TextView addDateTV = findViewById(R.id.orderDateTextView);

            nameTV.setText("Название: " + order.getName());
            addDateTV.setText("Добавлен: " + DateFormatter.getFormatedStringDate(order.getAddDate()) + " г.");
            BigDecimal price = order.getPrice();
            price = price.setScale(2, BigDecimal.ROUND_HALF_UP);
            priceTV.setText(price + " руб.");

            Button updateBtn = findViewById(R.id.updateOrderBtn);

            BigDecimal finalPrice = price;
            updateBtn.setOnClickListener(view -> {
                Intent intent = new Intent(this, OrderFormActivity.class);
                intent.putExtra("ACTION", "UPDATE");

                intent.putExtra("ORDER_POSITION", orderPosition);
                intent.putExtra("ORDER_ID", id);
                intent.putExtra("ORDER_NAME", order.getName());

                intent.putExtra("ORDER_PRICE", finalPrice.toString());
                startActivity(intent);
            });

            toolbar.setNavigationOnClickListener(view -> finish());
        }

    }