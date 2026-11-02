package com.example.plantcatalog;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView name = findViewById(R.id.detailName);
        TextView desc = findViewById(R.id.detailDesc);
        TextView conditions = findViewById(R.id.detailConditions);

        name.setText(getIntent().getStringExtra("name"));
        desc.setText(getIntent().getStringExtra("desc"));

        String cond = "Освещение: " + getIntent().getStringExtra("light") + "\n" +
                "Полив: " + getIntent().getStringExtra("watering") + "\n" +
                "Влажность: " + getIntent().getStringExtra("humidity");
        conditions.setText(cond);
    }
}