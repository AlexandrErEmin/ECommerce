package com.test.ecommerce.ui.front;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.test.ecommerce.R;
import com.test.ecommerce.data.adapters.FrontProductsAdapter;
import com.test.ecommerce.data.db.DBConnect;
import com.test.ecommerce.data.lists.ProductObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmResults;

public class FrontFragment extends Fragment {

    private FrontViewModel frontViewModel;
    private RealmResults<ProductObject> productObjectList;
    @BindView(R.id.view_list) RecyclerView mViewList;
    private FrontProductsAdapter mFrontProductAdapter;
    Realm realm;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        frontViewModel = new ViewModelProvider(this).get(FrontViewModel.class);
        View view = inflater.inflate(R.layout.fragment_front, container, false);
        ButterKnife.bind(this, view);
        realm = Realm.getDefaultInstance();

        productObjectList = new DBConnect().getAllProductCountNonZero();
        mViewList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mViewList.setHasFixedSize(true);
        mFrontProductAdapter = new FrontProductsAdapter(frontViewModel, productObjectList);
        productObjectList.addChangeListener(createListener());
        mViewList.setAdapter(mFrontProductAdapter);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mViewList);

        frontViewModel.getmProductList().observe(getViewLifecycleOwner(), data -> {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!");
            productObjectList = data;
        });

        frontViewModel.getmRefactorProduct().observe(getViewLifecycleOwner(), data -> {
            //mFrontProductAdapter.notifyItemChanged(data - 1);
        });


        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
        frontViewModel.closeModel();
    }

    private OrderedRealmCollectionChangeListener createListener() {
        return (collection, changeSet) -> {
            if (changeSet.getState() == OrderedCollectionChangeSet.State.INITIAL) {
                mFrontProductAdapter.notifyDataSetChanged();
                return;
            }
            OrderedCollectionChangeSet.Range[] deletions = changeSet.getDeletionRanges();
            for (int i = deletions.length - 1; i >= 0; i--) {
                OrderedCollectionChangeSet.Range range = deletions[i];
                mFrontProductAdapter.notifyItemRangeRemoved(range.startIndex, range.length);
            }
            OrderedCollectionChangeSet.Range[] insertions = changeSet.getInsertionRanges();
            for (OrderedCollectionChangeSet.Range range : insertions) {
                mFrontProductAdapter.notifyItemRangeInserted(range.startIndex, range.length);
            }
            OrderedCollectionChangeSet.Range[] modifications = changeSet.getChangeRanges();
            for (OrderedCollectionChangeSet.Range range : modifications) {
                mFrontProductAdapter.notifyItemRangeChanged(range.startIndex, range.length);
            }
        };
    }
}