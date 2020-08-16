package com.test.ecommerce.ui.back.refactor;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.test.ecommerce.data.db.DBConnect;
import com.test.ecommerce.data.lists.ProductObject;

import io.realm.Realm;

public class RefactorProductViewModel extends ViewModel {
    private MutableLiveData<ProductObject> dataProduct;
    private DBConnect mDBConnect;
    private Realm realm;
    public RefactorProductViewModel(int data){
        mDBConnect = new DBConnect();
        dataProduct = new MutableLiveData<>();
        realm = Realm.getDefaultInstance();

        dataProduct.setValue(mDBConnect.getOneProduct(realm, data));
    }

    public MutableLiveData<ProductObject> getDataProduct() {
        return dataProduct;
    }

    /**
     *  Изменение данных о товаре
     * @param id уникальный id
     * @param name название
     * @param price стоимость
     * @param quantity количество
     */
    public void refactorDataDB(int id, String name, Double price, int quantity){
        mDBConnect.refactorDataDB(id, name, price, quantity);
    }

    /**
     * Удаление продукта
     * @param idProduct id в БД
     */
    public void deleteData(int idProduct){
        mDBConnect.deleteProduct(idProduct);
    }

    public void closeModel(){
        realm.close();
    }

}