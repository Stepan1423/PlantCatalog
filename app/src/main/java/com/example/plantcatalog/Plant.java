package com.example.plantcatalog;

public class Plant {
    private int id;
    private String name;
    private String description;
    private String light;     //Свет
    private String watering;  //Полив
    private String humidity;  //Влажность

    public Plant(int id, String name, String description, String light, String watering, String humidity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.light = light;
        this.watering = watering;
        this.humidity = humidity;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getLight() { return light; }
    public String getWatering() { return watering; }
    public String getHumidity() { return humidity; }
}