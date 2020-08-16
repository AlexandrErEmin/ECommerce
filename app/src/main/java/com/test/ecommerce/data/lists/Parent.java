package com.test.ecommerce.data.lists;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Parent extends RealmObject {
    @SuppressWarnings("unused")
    private RealmList<ProductObject> productObjects;

    public RealmList<ProductObject> getProductObjects() {
        return productObjects;
    }
}
