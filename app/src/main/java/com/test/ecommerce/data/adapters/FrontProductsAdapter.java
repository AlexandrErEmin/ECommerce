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
    public FrontProductsAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MaterialCardView cardView = (MaterialCardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card_front, parent, false);
        return new Holder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        ProductObject productObject = productsList.get(position);
        holder.product_name.setText(productObject.getProduct_name());
        holder.product_price.setText(productObject.getProduct_price()+"  руб.");
        holder.product_quantity.setText(productObject.getProduct_quantity()+" шт.");
    }

    @Override
    public int getItemCount() {
        if(productsList.size() != 0)
            return productsList.size();
        else
            return 0;
    }

    class Holder extends RecyclerView.ViewHolder{
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
            buy.setOnClickListener(view -> clickCard.clickRecycler(productsList.get(getAdapterPosition()).getProduct_id(), getAdapterPosition()));
        }
    }

}
