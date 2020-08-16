package com.test.ecommerce.data.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.test.ecommerce.R;
import com.test.ecommerce.data.lists.ProductObject;
import com.test.ecommerce.interfaces.IForRecycler;

import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class BackProductsAdapter extends RealmRecyclerViewAdapter<ProductObject, BackProductsAdapter.Holder> {

    private OrderedRealmCollection<ProductObject> productsList;
    private IForRecycler.IRecycler clickCard;

    public BackProductsAdapter(IForRecycler.IRecycler clickCard, @Nullable OrderedRealmCollection<ProductObject> data) {
        super(data, false, true);
        productsList = data;
        this.clickCard = clickCard;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        MaterialCardView cardView = (MaterialCardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card_back, parent, false);
        return new Holder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final ProductObject obj = productsList.get(position);
        holder.product_name.setText(obj.getProduct_name()+"-"+obj.getProduct_id());
        holder.product_quantity.setText(obj.getProduct_quantity()+" шт.");
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        public ProductObject data;
        private TextView product_name;
        private TextView product_quantity;
        public Holder(@NonNull View itemView) {
            super(itemView);
            this.product_name = itemView.findViewById(R.id.product_name);
            this.product_quantity = itemView.findViewById(R.id.product_quantity);
            itemView.setOnClickListener(view -> clickCard.clickRecycler(productsList.get(getAdapterPosition()).getProduct_id(), getAdapterPosition()));
        }
    }
}
