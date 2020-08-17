package com.test.ecommerce.ui.front;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.test.ecommerce.data.db.DBConnect;
import com.test.ecommerce.data.lists.ProductObject;
import com.test.ecommerce.interfaces.IForRecycler;

import io.realm.Realm;
import io.realm.RealmResults;

public class FrontViewModel extends ViewModel implements IForRecycler.IRecycler{

    private MutableLiveData<RealmResults<ProductObject>> mProductList; // Полный список
    private MutableLiveData<Integer> mRefactorProduct; // ID Конкретного продукта

    private DBConnect mDBConnect;
    private Realm realm;

    public FrontViewModel() {
        mDBConnect = new DBConnect();
        mProductList = new MutableLiveData<>();
        mRefactorProduct = new MutableLiveData<>();
        realm = Realm.getDefaultInstance();

        setmProductList(mDBConnect.getAllProductCountNonZero());
    }

    public void setmProductList(RealmResults<ProductObject> mProductList) {
        // Храним список тут
        this.mProductList.setValue(mProductList);
    }
    public LiveData<RealmResults<ProductObject>> getmProductList() {
        return mProductList;
    }
    public MutableLiveData<Integer> getmRefactorProduct() {
        return mRefactorProduct;
    }

    /**
     * Клик по карточке списка
     * @param idCard НЕ ПОРЯДКОВЫЙ НОМЕР КАРТОЧКИ, А ID ТОВАРА!!!!
     */
    @Override
    public void clickRecycler(int idCard, int positionCard) {
        mDBConnect.buyProduct(idCard);
        setmProductList(mDBConnect.getAllProductCountNonZero());
    }

    /**
     * Уничтожение
     */
    public void closeModel(){
        realm.close();
    }
}