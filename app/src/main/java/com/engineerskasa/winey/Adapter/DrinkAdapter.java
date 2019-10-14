package com.engineerskasa.winey.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.engineerskasa.winey.Interface.ItemClickListener;
import com.engineerskasa.winey.Model.Drink;
import com.engineerskasa.winey.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DrinkAdapter extends RecyclerView.Adapter {
    Context context;
    List<Drink>drinkList;

    public DrinkAdapter(Context context, List<Drink> drinkList) {
        this.context = context;
        this.drinkList = drinkList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.drink_item_layout, null);
        return new DrinkViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        DrinkViewHolder drinkViewHolder = (DrinkViewHolder) holder;

        drinkViewHolder.txt_price
                .setText(new StringBuilder("Ghc ")
                        .append(drinkList.get(position).Price).toString());
        drinkViewHolder.txt_drink_name
                .setText(drinkList.get(position).Name);
        Picasso.with(context)
                .load(drinkList.get(position).Link)
                .into(drinkViewHolder.img_drink);

        drinkViewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Clicked "+ position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return drinkList.size();
    }

    private class DrinkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView img_drink;
        TextView txt_drink_name, txt_price;

        ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public DrinkViewHolder(View itemView) {
            super(itemView);
            img_drink = (ImageView) itemView.findViewById(R.id.image_drink);
            txt_drink_name = (TextView) itemView.findViewById(R.id.txt_drink_name);
            txt_price = (TextView) itemView.findViewById(R.id.txt_price);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v);
        }
    }
}
