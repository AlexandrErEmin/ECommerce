package com.test.ecommerce.ui.back.create;

import androidx.lifecycle.ViewModel;

import com.test.ecommerce.data.db.DBConnect;

public class CreateProductViewModel extends ViewModel {

    public void insertNewProduct(String name, double price, int quantity) {
        new DBConnect().insertProduct(name, price, quantity);
    }
}