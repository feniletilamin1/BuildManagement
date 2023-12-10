package com.example.buildmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.buildmanagement.DataBase.DbHelper;
import com.example.buildmanagement.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new DbHelper(this);
        DbHelper.database = dbHelper.getWritableDatabase();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        binding.appBarMain.fab.setOnClickListener(view -> {
            Intent intent = new Intent();
            switch (getIntent().getStringExtra("ACTIVE_FRAGMENT")) {
                case "ORDERS":
                    intent = new Intent(MainActivity.this, OrderFormActivity.class);
                    intent.putExtra("ACTION", "ADD");
                    break;
                case "CLIENTS":
                    intent = new Intent(MainActivity.this, ClientFormActivity.class);
                    intent.putExtra("ACTION", "ADD");
                    break;
                case "EMPLOYEES":
                    intent = new Intent(MainActivity.this, EmployeeFormActivity.class);
                    intent.putExtra("ACTION", "ADD");
                    break;
                case "OBJECTS":
                    intent = new Intent(MainActivity.this, BuildObjectFormActivity.class);
                    intent.putExtra("ACTION", "ADD");
                    break;
            }
            startActivity(intent);
        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_workers,
               R.id.nav_clients, R.id.nav_objects, R.id.nav_orders)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        getSupportFragmentManager().popBackStackImmediate();

        makeNewDir(getFilesDir() + File.separator + "objectImages");
        makeNewDir(getFilesDir() + File.separator + "clientPhotos");
        makeNewDir(getFilesDir() + File.separator + "employeePhotos");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void makeNewDir(String path) {
        File newFolder = new File(path);
        newFolder.mkdirs();
    }
}