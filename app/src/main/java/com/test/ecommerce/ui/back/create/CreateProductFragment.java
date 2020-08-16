package com.test.ecommerce.ui.back.create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;
import com.test.ecommerce.R;
import com.test.ecommerce.data.ConstantManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateProductFragment extends Fragment {

    private CreateProductViewModel mViewModel;
    private View view;

    @BindView(R.id.name) TextInputEditText mName;
    @BindView(R.id.price) TextInputEditText mPrice;
    @BindView(R.id.quantity) TextInputEditText mQuantity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_product_fragment, container, false);
        ButterKnife.bind(this, view);
        mViewModel = new ViewModelProvider(this).get(CreateProductViewModel.class);

        return view;
    }

    @OnClick(R.id.create)
    public void OnClick (View view){
        if(!mName.getText().toString().isEmpty() && !mPrice.getText().toString().isEmpty() && !mQuantity.getText().toString().isEmpty()){
            mViewModel.insertNewProduct(mName.getText().toString(),
                                        Double.parseDouble(mPrice.getText().toString()),
                                        Integer.parseInt(mQuantity.getText().toString()));
            Navigation.findNavController(view).navigate(R.id.action_createProductFragment_to_navigation_back);
        }else {
            Toast.makeText(getContext(), ConstantManager.NULL_FIELD, Toast.LENGTH_SHORT).show();
        }
    }
}