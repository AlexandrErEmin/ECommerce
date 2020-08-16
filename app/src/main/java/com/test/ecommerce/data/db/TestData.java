package com.test.ecommerce.data.db;

import com.test.ecommerce.data.lists.ProductObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Установка тестовых данных
 */

public class TestData {
    private List<ProductObject> productLists = new ArrayList<>();

    public TestData(){
        initTestData(); // Инициализация тестовых данных
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> realm1.insert(productLists));
        realm.close();
    }

    /**
     * Задаем тестовые данные если приложение запущено в первый раз
     * Используем ProductList для ввода и вывода информации
     */
    private void initTestData(){
        productLists.add(new ProductObject(1, "Apple iPod touch 5 32Gb", 8888, 5));
        productLists.add(new ProductObject(2, "Samsung Galaxy S Duos S7562", 7230, 2));
        productLists.add(new ProductObject(3, "Canon EOS 600D Kit", 15659, 4));
        productLists.add(new ProductObject(4, "Samsung Galaxy Tab 2 10.1 P5100 16Gb", 13290, 9));
        productLists.add(new ProductObject(5, "PocketBook Touch", 5197, 2));
        productLists.add(new ProductObject(6, "Samsung Galaxy Note II 16Gb", 17049.5, 2));
        productLists.add(new ProductObject(7, "Nikon D3100 Kit", 12190, 4));
        productLists.add(new ProductObject(8, "Canon EOS 1100D Kit", 10985, 2));
        productLists.add(new ProductObject(9, "Sony Xperia acro S", 1180.99, 1));
        productLists.add(new ProductObject(10, "Lenovo G580", 8922, 1));
    }
}
