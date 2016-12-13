package com.danielacedo.a100snacks.repository;

import android.app.ProgressDialog;

import com.danielacedo.a100snacks.model.Beverage;
import com.danielacedo.a100snacks.model.Product;
import com.danielacedo.a100snacks.model.Snack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel on 12/12/16.
 */

public class ProductRepository {
    private static ProductRepository productRepository;
    private List<Product> products;

    public static ProductRepository getInstance(){
        if (productRepository == null) {
            productRepository = new ProductRepository();
        }

        return productRepository;
    }

    private ProductRepository(){
        initializeProducts();
    }

    public List<Product> getProducts(){
        return new ArrayList<>(products);
    }

    private void initializeProducts(){
        products = new ArrayList<>();
        products.add(new Snack(0, "Lomo al ajillo", 1.00, 0));
        products.add(new Snack(1, "Pollo con alioli", 1.00, 0));
        products.add(new Beverage(2, "Cocacola", 1.20, 0));
        products.add(new Snack(3, "Serranito", 2.00, 0));
    }
}
