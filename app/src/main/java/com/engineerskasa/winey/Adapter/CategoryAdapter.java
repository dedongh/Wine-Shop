package com.engineerskasa.winey.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.engineerskasa.winey.Activity.DrinkActivity;
import com.engineerskasa.winey.Interface.ItemClickListener;
import com.engineerskasa.winey.Model.Category;
import com.engineerskasa.winey.R;
import com.engineerskasa.winey.Util.Common;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter {
    Context context;
    List<Category> categories;

    public CategoryAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.menu_item_layout, null);
        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        CategoryViewHolder viewHolder = (CategoryViewHolder)holder;
        Picasso.with(context)
                .load(categories.get(position)
                .Link)
                .into(viewHolder.img_product);

        viewHolder.txt_menu_name.setText(categories.get(position).Name);

        // implement event on holder
        viewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view) {
                Common.currentCategory = categories.get(position);
                // start new activity
                context.startActivity(new Intent(context, DrinkActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    private class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView img_product;
        TextView txt_menu_name;

        ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public CategoryViewHolder(View itemView) {
            super(itemView);
            img_product = (ImageView)itemView.findViewById(R.id.image_product);
            txt_menu_name = (TextView) itemView.findViewById(R.id.txt_menu_name);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v);
        }
    }
}
