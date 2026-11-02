package com.example.plantcatalog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "plants.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "plants_table";

    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_DESC = "description";
    private static final String COL_LIGHT = "light";
    private static final String COL_WATERING = "watering";
    private static final String COL_HUMIDITY = "humidity";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_DESC + " TEXT, " +
                COL_LIGHT + " TEXT, " +
                COL_WATERING + " TEXT, " +
                COL_HUMIDITY + " TEXT)";
        db.execSQL(createTable);

        addInitialData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    private void addInitialData(SQLiteDatabase db) {

        insertPlant(db, "Сансевиерия", "Неприхотливое растение. Идеально для новичков.", "Тень", "Редкий", "Низкая");
        insertPlant(db, "Монстера", "Тропическое растение, любит свет и влажность.", "Полутень", "Умеренный", "Высокая");
        insertPlant(db, "Кактус", "Любит солнце и очень редкий полив.", "Солнце", "Редкий", "Низкая");
        insertPlant(db, "Фикус", "Требует яркого рассеянного света.", "Полутень", "Умеренный", "Средняя");
    }

    private void insertPlant(SQLiteDatabase db, String name, String desc, String light, String watering, String humidity) {
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, name);
        cv.put(COL_DESC, desc);
        cv.put(COL_LIGHT, light);
        cv.put(COL_WATERING, watering);
        cv.put(COL_HUMIDITY, humidity);
        db.insert(TABLE_NAME, null, cv);
    }


    public List<Plant> getPlants(String searchQuery, String lightFilter, String wateringFilter) {
        List<Plant> plantList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        StringBuilder selection = new StringBuilder();
        List<String> selectionArgs = new ArrayList<>();


        if (searchQuery != null && !searchQuery.isEmpty()) {
            selection.append(COL_NAME).append(" LIKE ?");
            selectionArgs.add("%" + searchQuery + "%");
        }
        if (lightFilter != null && !lightFilter.isEmpty()) {
            if (selection.length() > 0) selection.append(" AND ");
            selection.append(COL_LIGHT).append(" = ?");
            selectionArgs.add(lightFilter);
        }
        if (wateringFilter != null && !wateringFilter.isEmpty()) {
            if (selection.length() > 0) selection.append(" AND ");
            selection.append(COL_WATERING).append(" = ?");
            selectionArgs.add(wateringFilter);
        }

        Cursor cursor = db.query(TABLE_NAME, null,
                selection.length() > 0 ? selection.toString() : null,
                selectionArgs.size() > 0 ? selectionArgs.toArray(new String[0]) : null,
                null, null, COL_NAME + " ASC");

        if (cursor.moveToFirst()) {
            do {
                Plant plant = new Plant(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_DESC)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_LIGHT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_WATERING)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_HUMIDITY))
                );
                plantList.add(plant);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return plantList;
    }
}