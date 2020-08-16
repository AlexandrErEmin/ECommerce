package com.test.ecommerce.ui.back;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.test.ecommerce.data.db.DBConnect;
import com.test.ecommerce.data.lists.ProductObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmResults;

public class BackViewModel extends ViewModel{

    private MutableLiveData<RealmResults<ProductObject>> mProductList; // Полный список
    private MutableLiveData<Integer> mRefactorProduct; // ID Конкретного продукта

    private DBConnect mDBConnect;
    private Realm realm;

    public BackViewModel() {
        mDBConnect = new DBConnect();
        mProductList = new MutableLiveData<>();
        mRefactorProduct = new MutableLiveData<>();

        realm = Realm.getDefaultInstance();

        setmListProduct(mDBConnect.getAllProduct());
    }

    public void setmListProduct(RealmResults<ProductObject> mListProduct) {
        this.mProductList.setValue(mListProduct);
    }
    public MutableLiveData<RealmResults<ProductObject>> getmListProduct() {
        return mProductList;
    }
    public MutableLiveData<Integer> getmRefactorProduct() {
        return mRefactorProduct;
    }

    /**
     * Изменили данные продукта
     * @param product_id ID ПРодукта
     */
    private void refactorProduct(int product_id) {
        mProductList.setValue(mDBConnect.getAllProduct());
        mRefactorProduct.setValue(product_id);
    }

    /**
     * Уничтожение
     */
    public void closeModel(){
        realm.close();
    }
}