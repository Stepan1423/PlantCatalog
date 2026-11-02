package com.example.plantcatalog;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.ViewHolder> {

    private List<Plant> plants;
    private Context context;

    public PlantAdapter(Context context, List<Plant> plants) {
        this.context = context;
        this.plants = plants;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Plant plant = plants.get(position);
        holder.name.setText(plant.getName());
        String info = "Свет: " + plant.getLight() + " | Полив: " + plant.getWatering();
        holder.info.setText(info);


        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);

            intent.putExtra("name", plant.getName());
            intent.putExtra("desc", plant.getDescription());
            intent.putExtra("light", plant.getLight());
            intent.putExtra("watering", plant.getWatering());
            intent.putExtra("humidity", plant.getHumidity());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return plants.size();
    }

    public void updateList(List<Plant> newPlants) {
        this.plants = newPlants;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, info;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.plantName);
            info = itemView.findViewById(R.id.plantInfo);
        }
    }
}