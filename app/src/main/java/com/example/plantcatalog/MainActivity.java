package com.example.plantcatalog;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private PlantAdapter adapter;
    private EditText searchEditText;
    private String currentLightFilter = "";
    private String currentWaterFilter = "";

    private final ActivityResultLauncher<Intent> filterLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {

                    String light = result.getData().getStringExtra("light");
                    String water = result.getData().getStringExtra("watering");

                    currentLightFilter = (light != null) ? light : "";
                    currentWaterFilter = (water != null) ? water : "";

                    loadData();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        searchEditText = findViewById(R.id.searchEditText);
        Button filterButton = findViewById(R.id.filterButton);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Plant> initialPlants = dbHelper.getPlants("", "", "");
        adapter = new PlantAdapter(this, initialPlants);
        recyclerView.setAdapter(adapter);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loadData();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        filterButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FilterActivity.class);
            filterLauncher.launch(intent);
        });
    }

    private void loadData() {
        String query = searchEditText.getText().toString().trim();
        List<Plant> filteredPlants = dbHelper.getPlants(query, currentLightFilter, currentWaterFilter);
        adapter.updateList(filteredPlants);
    }
}