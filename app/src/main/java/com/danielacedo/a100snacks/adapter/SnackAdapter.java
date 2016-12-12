package com.danielacedo.a100snacks.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.danielacedo.a100snacks.R;
import com.danielacedo.a100snacks.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by usuario on 12/12/16.
 */

public class SnackAdapter extends ArrayAdapter<Product> {

    private List<Product> productList;

    public SnackAdapter(Context context, List<Product> products){
        super(context, R.layout.snack_layout);
        productList = new ArrayList<>(products);
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ProductHolder holder = null;

        if (v == null) {
            v = LayoutInflater.from(getContext()).inflate(R.layout.snack_layout, null);
            holder = new ProductHolder();

            holder.txv_title = (TextView)v.findViewById(R.id.txv_snackTitle);
            holder.txv_price = (TextView)v.findViewById(R.id.txv_snackPrice);
            holder.edt_quantity = (EditText)v.findViewById(R.id.edt_snackQuantity);
            holder.btn_plus = (Button)v.findViewById(R.id.snackPlus);
            holder.btn_minus = (Button)v.findViewById(R.id.snackMinus);

            v.setTag(holder);
        }else{
            holder = (ProductHolder) v.getTag();
        }

        holder.txv_title.setText(getItem(position).getName());
        holder.txv_price.setText(String.format("%.2f", getItem(position).getPrice()));
        holder.edt_quantity.setText(String.valueOf(getItem(position).getQuantity()));
        holder.btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getItem(position).increaseQuantity();
                notifyDataSetChanged();
            }
        });

        holder.btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getItem(position).decreaseQuantity();
                notifyDataSetChanged();
            }
        });
    }

    public static class ProductHolder{
        TextView txv_title, txv_price;
        EditText edt_quantity;
        Button btn_plus, btn_minus;
    }
}
