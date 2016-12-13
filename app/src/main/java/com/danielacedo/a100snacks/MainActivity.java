package com.danielacedo.a100snacks;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
                double total = 0.0;
                for (Product p : selectedProducts){
                    total += p.getPrice() * (double) p.getQuantity();
                    builder.append(p.getName()+getString(R.string.tab)+String.format("%.2f", p.getPrice())+"x "+ p.getQuantity() + " -> " + String.format("%.2f", p.getPrice()* (double) p.getQuantity())+"\n");
                }

                builder.append("\nTOTAL: "+ String.format("%.2f", total));

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
                alertBuilder.setTitle("Pedido").setMessage(builder.toString()).setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        adapter.resetProductCount();
                    }
                });

                alertBuilder.show();
            }
        });

        lv_snacks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, final int i, long l) {
                PopupMenu menu = new PopupMenu(MainActivity.this, view, Gravity.RIGHT);
                menu.getMenuInflater().inflate(R.menu.ctx_list_menu, menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        adapter.getItem(i).setQuantity(0);
                        adapter.notifyDataSetChanged();
                        return false;
                    }
                });

                menu.show();
            }
        });

        registerForContextMenu(lv_snacks);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.listmenu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_snack_sortfirst:
                adapter.sortSnackFirst();
                break;
            case R.id.menu_beverage_sortfirst:
                adapter.sortBeverageFirst();
                break;
            case R.id.menu_snackOnly:
                adapter.filterSnackOnly();
                break;
            case R.id.menu_beverageOnly:
                adapter.filterBeverageOnly();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //PopupMenu popupMenu = new PopupMenu(MainActivity.this, v);
        //popupMenu.inflate(R.menu.ctx_list_menu);
        getMenuInflater().inflate(R.menu.ctx_list_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()){
            case R.id.ctx_resetQuantity:
                adapter.getItem(info.position).setQuantity(0);
                adapter.notifyDataSetChanged();
                break;

        }

        return super.onContextItemSelected(item);
    }
}
