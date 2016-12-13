package com.danielacedo.a100snacks.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.danielacedo.a100snacks.R;
import com.danielacedo.a100snacks.model.Beverage;
import com.danielacedo.a100snacks.model.Product;
import com.danielacedo.a100snacks.model.Snack;
import com.danielacedo.a100snacks.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by usuario on 12/12/16.
 */

public class SnackAdapter extends ArrayAdapter<Product> {

    private List<Product> originalList;

    public SnackAdapter(Context context){
        super(context, R.layout.snack_layout, new ArrayList<Product>(ProductRepository.getInstance().getProducts()));
        originalList = new ArrayList<>(ProductRepository.getInstance().getProducts());
    }

    public List<Product> getSelectedProducts(){
        List<Product> selectedProducts = new ArrayList<>();

        for (int i = 0; i<getCount(); i++){
            Product p = getItem(i);

            if(p.getQuantity() > 0){
                selectedProducts.add(p);
            }
        }

        return selectedProducts;
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
        holder.txv_price.setText(String.format("%.2f", getItem(position).getPrice())+" x");
        holder.edt_quantity.setText(String.valueOf(getItem(position).getQuantity()));
        holder.btn_plus.setFocusable(false);
        holder.btn_plus.setFocusableInTouchMode(false);
        holder.btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getItem(position).increaseQuantity();
                notifyDataSetChanged();
            }
        });

        holder.btn_minus.setFocusable(false);
        holder.btn_minus.setFocusableInTouchMode(false);
        holder.btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getItem(position).decreaseQuantity();
                notifyDataSetChanged();
            }
        });

        if(getItem(position) instanceof Snack){
            v.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
        }
        else if(getItem(position) instanceof Beverage){
            v.setBackgroundColor(getContext().getResources().getColor(R.color.colorAccent));
        }

        return v;
    }

    public void resetProductCount(){
        for (int i = 0; i < getCount(); i++){
            getItem(i).setQuantity(0);
        }

        notifyDataSetChanged();
    }

    public void sortSnackFirst(){
        List<Snack> snacks = new ArrayList<>();
        List<Beverage> beverages = new ArrayList<>();

        for (int i = 0; i<originalList.size(); i++){
            if(originalList.get(i) instanceof Snack)
                snacks.add((Snack)originalList.get(i));
            else if (originalList.get(i) instanceof Beverage)
                beverages.add((Beverage) originalList.get(i));
        }

        clear();
        addAll(snacks);
        addAll(beverages);
    }

    public void sortBeverageFirst(){
        List<Snack> snacks = new ArrayList<>();
        List<Beverage> beverages = new ArrayList<>();

        for (int i = 0; i<originalList.size(); i++){
            if(originalList.get(i) instanceof Snack)
                snacks.add((Snack)originalList.get(i));
            else if (originalList.get(i) instanceof Beverage)
                beverages.add((Beverage) originalList.get(i));
        }

        clear();
        addAll(beverages);
        addAll(snacks);
    }

    public void filterSnackOnly(){
        List<Product> temp = new ArrayList<>();

        for (int i = 0; i<originalList.size(); i++){
            if(originalList.get(i) instanceof Snack)
                temp.add(originalList.get(i));
        }

        clear();
        addAll(temp);
    }

    public void filterBeverageOnly(){
        List<Product> temp = new ArrayList<>();

        for (int i = 0; i<originalList.size(); i++){
            if(originalList.get(i) instanceof Beverage)
                temp.add(originalList.get(i));
        }

        clear();
        addAll(temp);
    }

    public static class ProductHolder{
        TextView txv_title, txv_price;
        EditText edt_quantity;
        Button btn_plus, btn_minus;
    }
}
