package com.test.ecommerce.ui.back.refactor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButton;
import com.test.ecommerce.ModelFactory;
import com.test.ecommerce.R;
import com.test.ecommerce.data.ConstantManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RefactorProductFragment extends Fragment {

    private View view;
    private int mId;
    private RefactorProductViewModel mRefactorProductViewModel;
    @BindView(R.id.name) EditText mName;
    @BindView(R.id.price) EditText mPrice;
    @BindView(R.id.quantity) EditText mQuantity;
    @BindView(R.id.refactor) MaterialButton mRefactor;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.refactor_product_fragment, container, false);
        ButterKnife.bind(this, view);
        // Определяем по какому продукту из списка кликнули и сообщаем об этом ViewModel
        int mClickProduct = getArguments() != null ? getArguments().getInt(ConstantManager.GET_ID_CARD_RECYCLER) : 0;
        if(mClickProduct > 0) {
            mRefactorProductViewModel = new ViewModelProvider(this, new ModelFactory(mClickProduct)).get(RefactorProductViewModel.class);
            mRefactorProductViewModel.getDataProduct().observe(getViewLifecycleOwner(), data -> {
                mId = data.getProduct_id();
                mName.setText(data.getProduct_name());
                mPrice.setText(String.valueOf(data.getProduct_price()));
                mQuantity.setText(String.valueOf(data.getProduct_quantity()));
            });
        }
        // TODO Данные по редактируемому продукту не пришли
        //-----------------------------------------------END-------------------------------------------
        return view;
    }
    @OnClick({R.id.refactor, R.id.delete})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.refactor: // Сохранение изменений
                if(!mName.getEditableText().toString().isEmpty() && !mPrice.getText().toString().isEmpty() && !mQuantity.getText().toString().isEmpty()){
                    mRefactorProductViewModel.refactorDataDB(mId,
                            mName.getEditableText().toString(),
                            Double.parseDouble(mPrice.getText().toString()),
                            Integer.parseInt(mQuantity.getText().toString()));
                    Navigation.findNavController(this.view).navigate(R.id.action_refactorProductFragment_to_navigation_back);
                }else{
                    Toast.makeText(getContext(), ConstantManager.NULL_FIELD, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.delete: // удаление товара
                mRefactorProductViewModel.deleteData(mId);
                Navigation.findNavController(this.view).navigate(R.id.action_refactorProductFragment_to_navigation_back);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRefactorProductViewModel.closeModel();
    }
}