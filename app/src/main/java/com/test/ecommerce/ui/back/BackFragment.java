package com.test.ecommerce.ui.back;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.ecommerce.R;
import com.test.ecommerce.data.ConstantManager;
import com.test.ecommerce.data.adapters.BackProductsAdapter;
import com.test.ecommerce.data.db.DBConnect;
import com.test.ecommerce.data.lists.ProductObject;
import com.test.ecommerce.interfaces.IForRecycler;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmResults;

public class BackFragment extends Fragment implements IForRecycler.IRecycler {

    private BackViewModel backViewModel;
    private Context mContext;
    private View view;
    private BackProductsAdapter mAdapter;
    private RealmResults<ProductObject> objectRealmObject;
    private Realm mRealm;

    @BindView(R.id.list) RecyclerView productList;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        backViewModel = new ViewModelProvider(this).get(BackViewModel.class);
        view = inflater.inflate(R.layout.fragment_back, container, false);
        ButterKnife.bind(this, view);
        mRealm = Realm.getDefaultInstance();

        objectRealmObject = new DBConnect().getAllProduct();
        objectRealmObject.addChangeListener(createListener());
        productList.setLayoutManager(new LinearLayoutManager(mContext));
        productList.setHasFixedSize(true);
        mAdapter = new BackProductsAdapter(this, objectRealmObject);
        productList.setAdapter(mAdapter);

        backViewModel.getmListProduct().observe(getViewLifecycleOwner(), data -> {
            objectRealmObject = data;
        });
        backViewModel.getmRefactorProduct().observe(getViewLifecycleOwner(), data ->{
            mAdapter.notifyItemChanged(data -1);
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @OnClick(R.id.create)
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.create:
                Navigation.findNavController(view).navigate(R.id.action_navigation_back_to_createProductFragment);
                break;
            default:
                System.out.println("ERROR");
        }
    }

    /**
     * Кликнули по карточке списка
     * @param idCard НЕ ПОРЯДКОВЫЙ НОМЕР КАРТОЧКИ, А ID ТОВАРА!!!!
     */
    @Override
    public void clickRecycler(int idCard, int positionCard) {
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantManager.GET_ID_CARD_RECYCLER, idCard);
        bundle.putInt(ConstantManager.GET_POSITION_CARD_RECYCLER, idCard);
        Navigation.findNavController(view).navigate(R.id.action_navigation_back_to_refactorProductFragment, bundle);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRealm.close();
        backViewModel.closeModel();
    }

    private OrderedRealmCollectionChangeListener createListener() {
        return (collection, changeSet) -> {
            if (changeSet.getState() == OrderedCollectionChangeSet.State.INITIAL) {
                mAdapter.notifyDataSetChanged();
                return;
            }
            OrderedCollectionChangeSet.Range[] deletions = changeSet.getDeletionRanges();
            for (int i = deletions.length - 1; i >= 0; i--) {
                OrderedCollectionChangeSet.Range range = deletions[i];
                mAdapter.notifyItemRangeRemoved(range.startIndex, range.length);
            }
            OrderedCollectionChangeSet.Range[] insertions = changeSet.getInsertionRanges();
            for (OrderedCollectionChangeSet.Range range : insertions) {
                mAdapter.notifyItemRangeInserted(range.startIndex, range.length);
            }
            OrderedCollectionChangeSet.Range[] modifications = changeSet.getChangeRanges();
            for (OrderedCollectionChangeSet.Range range : modifications) {
                mAdapter.notifyItemRangeChanged(range.startIndex, range.length);
            }
        };
    }
}