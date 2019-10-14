package com.engineerskasa.winey.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
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

        drinkViewHolder.btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddToCartDialog(position);
            }
        });

        drinkViewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Clicked "+ position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAddToCartDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.add_to_cart_layout, null);

        // views
        ImageView img_product_dialog = (ImageView) itemView.findViewById(R.id.img_cart_product);
        ElegantNumberButton txt_Count = (ElegantNumberButton) itemView.findViewById(R.id.txt_count);
        TextView txt_product_dialog = (TextView) itemView.findViewById(R.id.txt_cart_product_name);

        EditText edt_comment = (EditText) itemView.findViewById(R.id.edt_comments);

        RadioButton rdi_sizeM = (RadioButton) itemView.findViewById(R.id.rdi_sizeM);
        RadioButton rdi_sizeL = (RadioButton) itemView.findViewById(R.id.rdi_sizeL);
        RadioButton rdi_alcoholic = (RadioButton) itemView.findViewById(R.id.rdi_flavAlco);
        RadioButton rdi_non_alcohol = (RadioButton) itemView.findViewById(R.id.rdi_flavFrut);

        RecyclerView recyclerView = (RecyclerView) itemView.findViewById(R.id.recycler_toppings);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);

        // set Data
        Picasso.with(context)
                .load(drinkList.get(position).Link)
                .into(img_product_dialog);

        txt_product_dialog.setText(drinkList.get(position).Name);

        builder.setView(itemView);

        builder.setNegativeButton("Add to Cart", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public int getItemCount() {
        return drinkList.size();
    }

    private class DrinkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView img_drink;
        TextView txt_drink_name, txt_price;
        Button  btn_add_to_cart;

        ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public DrinkViewHolder(View itemView) {
            super(itemView);
            img_drink = (ImageView) itemView.findViewById(R.id.image_drink);
            txt_drink_name = (TextView) itemView.findViewById(R.id.txt_drink_name);
            txt_price = (TextView) itemView.findViewById(R.id.txt_price);

            btn_add_to_cart = (Button) itemView.findViewById(R.id.btn_add_cart);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v);
        }
    }
}
