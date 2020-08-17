package com.test.ecommerce.data.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.test.ecommerce.R;
import com.test.ecommerce.data.lists.ProductObject;
import com.test.ecommerce.interfaces.IForRecycler;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class FrontProductsAdapter extends RealmRecyclerViewAdapter<ProductObject, FrontProductsAdapter.Holder> {

    private OrderedRealmCollection<ProductObject> productsList;
    private IForRecycler.IRecycler clickCard;

    public FrontProductsAdapter(IForRecycler.IRecycler clickCard, @Nullable OrderedRealmCollection<ProductObject> data) {
        super(data, false, true);
        productsList = data;
        this.clickCard = clickCard;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        MaterialCardView cardView = (MaterialCardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card_front, parent, false);
        return new Holder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        ProductObject productObject = productsList.get(position);
        if(productObject != null){
            String dataSTR = productObject.getProduct_price() + "  руб.";
            holder.product_name.setText(productObject.getProduct_name());
            holder.product_price.setText(dataSTR);
            dataSTR = productObject.getProduct_quantity()+" шт.";
            holder.product_quantity.setText(dataSTR);
        }
        int idCard = productsList.get(holder.getAdapterPosition()).getProduct_id();
        holder.buy.setOnClickListener(view -> clickCard.clickRecycler(idCard, holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    static class Holder extends RecyclerView.ViewHolder{
        private TextView product_name;
        private TextView product_price;
        private TextView product_quantity;
        private MaterialButton buy;
        public Holder(@NonNull View itemView) {
            super(itemView);
            this.product_name = itemView.findViewById(R.id.product_name);
            this.product_price = itemView.findViewById(R.id.product_price);
            this.product_quantity = itemView.findViewById(R.id.product_quantity);
            this.buy = itemView.findViewById(R.id.buy);
        }
    }

}
