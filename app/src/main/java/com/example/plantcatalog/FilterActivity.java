package com.example.plantcatalog;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;

public class FilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        RadioGroup lightGroup = findViewById(R.id.lightRadioGroup);
        RadioGroup waterGroup = findViewById(R.id.wateringRadioGroup);
        Button applyBtn = findViewById(R.id.applyFilterButton);

        applyBtn.setOnClickListener(v -> {
            String light = "";
            String watering = "";

            int selectedLightId = lightGroup.getCheckedRadioButtonId();
            if (selectedLightId == R.id.lightSun) light = "Солнце";
            else if (selectedLightId == R.id.lightPartial) light = "Полутень";
            else if (selectedLightId == R.id.lightShade) light = "Тень";

            int selectedWaterId = waterGroup.getCheckedRadioButtonId();
            if (selectedWaterId == R.id.waterRare) watering = "Редкий";
            else if (selectedWaterId == R.id.waterModerate) watering = "Умеренный";

            Intent returnIntent = new Intent();
            returnIntent.putExtra("light", light);
            returnIntent.putExtra("watering", watering);
            setResult(RESULT_OK, returnIntent);
            finish();
        });
    }
}