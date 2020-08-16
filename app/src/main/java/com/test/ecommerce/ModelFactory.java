package com.test.ecommerce;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.test.ecommerce.ui.back.BackViewModel;
import com.test.ecommerce.ui.back.refactor.RefactorProductViewModel;
import com.test.ecommerce.ui.front.FrontFragment;
import com.test.ecommerce.ui.front.FrontViewModel;

public class ModelFactory extends ViewModelProvider.NewInstanceFactory {
    private int mDataInt;
    public ModelFactory(int dataInt){
        mDataInt = dataInt;
    }
    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass){
        if (BackViewModel.class.equals(modelClass)) {
            return (T) new BackViewModel();
        } else if (FrontFragment.class.equals(modelClass)) {
            return (T) new FrontViewModel();
        } else if (RefactorProductViewModel.class.equals(modelClass)){
            return (T) new RefactorProductViewModel(mDataInt);
        }else
            return null;
    }
}
