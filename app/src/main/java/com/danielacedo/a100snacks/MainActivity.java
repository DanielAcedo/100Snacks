package com.danielacedo.a100snacks;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.danielacedo.a100snacks.adapter.SnackAdapter;
import com.danielacedo.a100snacks.model.Product;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lv_snacks;
    private SnackAdapter adapter;
    private Button btn_order;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv_snacks = (ListView)findViewById(R.id.lv_snacks);
        adapter = new SnackAdapter(this);
        lv_snacks.setAdapter(adapter);

        btn_order = (Button)findViewById(R.id.btn_order);
        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Product> selectedProducts = adapter.getSelectedProducts();

                StringBuilder builder = new StringBuilder();

                for (Product p : selectedProducts){
                    builder.append(p.getName()+getString(R.string.tab)+p.getPrice()+"x "+String.format("%.2f", p.getPrice()) + " -> " + p.getPrice()* (double) p.getQuantity()+"\n");
                }

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
                alertBuilder.setTitle("Pedido").setMessage(builder.toString());
                alertBuilder.show();
            }
        });

    }
}
