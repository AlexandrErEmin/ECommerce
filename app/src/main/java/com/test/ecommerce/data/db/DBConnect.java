package com.test.ecommerce.data.db;

import android.os.Handler;

import com.test.ecommerce.data.lists.ProductObject;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class DBConnect {
    Handler handler;
    public DBConnect(){
        handler = new Handler();
    }

    /**
     * Получает полный список товаров
     * @return List<ProductObject>
     */
    public RealmResults<ProductObject> getAllProduct(){
        Realm mRealm = Realm.getDefaultInstance();
        RealmResults<ProductObject> res = mRealm.where(ProductObject.class).findAll().sort("product_id", Sort.ASCENDING);
        mRealm.close();
        return res;
    }

    /**
     * Получение резльтата где количество остатка товара не 0
     * @return List<ProductObject>
     */
    public RealmResults<ProductObject> getAllProductCountNonZero(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<ProductObject> res = realm.where(ProductObject.class).notEqualTo("product_quantity", 0).findAll().sort("product_id", Sort.ASCENDING);
        realm.close();
        return res;
    }

    /**
     * ПОлучение данных об одном продукте
     * @param data id продукта в БД
     * @return ProductObject
     */
    public ProductObject getOneProduct(Realm mRealm, int data){
        return mRealm.where(ProductObject.class).equalTo("product_id", data).findFirst();
    }

    /**
     * Изменение данных о продукте
     * @param dataId    идентификатор в БД
     * @param name      новое название
     * @param price     новыя стоимость
     * @param quantity  новое количество на остатке
     */
    public void refactorDataDB(final int dataId, final String name, final double price, final int quantity){
        Realm LRealm = Realm.getDefaultInstance();
        handler.postDelayed(() -> {
            LRealm.executeTransaction(realm -> {
                ProductObject productObject = realm.where(ProductObject.class).equalTo("product_id", dataId).findFirst();
                if(productObject != null){
                    productObject.setProduct_name(name);
                    productObject.setProduct_price(price);
                    productObject.setProduct_quantity(quantity);
                }
            });
            LRealm.close();
        }, 5000);
    }

    /**
     * Покупка товара
     * @param dataId Идентификатор товара (хранится в БД)
     */
    public void buyProduct(final int dataId){
        Realm LRealm = Realm.getDefaultInstance();
        handler.postDelayed(() ->
            LRealm.executeTransaction(realm -> {
            ProductObject productObject = realm.where(ProductObject.class).equalTo("product_id", dataId).findFirst();
            if(productObject != null){
                if(productObject.getProduct_quantity() > 0) {
                    productObject.setProduct_quantity(productObject.getProduct_quantity() - 1);
                }
            }
            LRealm.close();
        }), 3000);
    }

    /**
     * Вставить данные
     * @param name     Название
     * @param price    Стоимость
     * @param quantity Количество
     */
    public void insertProduct(String name, double price, int quantity){
        Realm LRealm = Realm.getDefaultInstance();
        handler.postDelayed(() -> LRealm.executeTransaction(realm -> {
            LRealm.where(ProductObject.class).sort("product_id");
            Number number = realm.where(ProductObject.class).max("product_id");
            ProductObject newData = new ProductObject();
            if(number != null){
                newData.setProduct_id(number.intValue() + 1);
                newData.setProduct_name(name);
                newData.setProduct_price(price);
                newData.setProduct_quantity(quantity);
            }
            LRealm.copyToRealm(newData);
            LRealm.close();
        }), 5000);
    }

    /**
     * Удаление записи
     * @param idProduct идентификатор продукта
     */
    public void deleteProduct(int idProduct){
        Realm LRealm = Realm.getDefaultInstance();
        handler.postDelayed(() -> LRealm.executeTransaction(realm -> {
            ProductObject res = realm.where(ProductObject.class).equalTo("product_id", idProduct).findFirst();
            if (res != null)
                res.deleteFromRealm();
            LRealm.close();
        }), 5000);
    }
}
