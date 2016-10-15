package com.e_buyad.marvin.e_buyad.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.e_buyad.marvin.e_buyad.ProductDetailActivity;
import com.e_buyad.marvin.e_buyad.R;
import com.e_buyad.marvin.e_buyad.model.Product;
import com.e_buyad.marvin.e_buyad.model.ShoppingCart;

import java.util.List;

/**
 * User-defined adapter class for shopping list
 */
public class ShoppingListAdapter
        extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListAdapterViewHolder> {
    private Context context;
    private List<ShoppingCart> shoppingCartList;


    public ShoppingListAdapter(Context context, List<ShoppingCart> shoppingCartList) {
        this.context = context;
        this.shoppingCartList = shoppingCartList;
    }

    @Override
    public ShoppingListAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(
                parent.getContext()
        ).inflate(
                R.layout.cardview_shopping_list,
                parent,
                false
        );

        return new ShoppingListAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ShoppingListAdapterViewHolder holder, final int position) {
        ShoppingCart shoppingCart = shoppingCartList.get(position);

        holder.transactionNumber.setText(shoppingCart.getTransactionId());
        holder.transactionDate.setText(shoppingCart.getTransactionDate());
        holder.genericName.setText(shoppingCart.getGenericName());
        holder.quantity.setText(shoppingCart.getQuantity());
    }

    @Override
    public int getItemCount() {
        return shoppingCartList.size();
    }

    public static class ShoppingListAdapterViewHolder extends RecyclerView.ViewHolder {
        protected TextView transactionNumber;
        protected TextView transactionDate;
        protected TextView genericName;
        protected TextView quantity;

        public ShoppingListAdapterViewHolder(View itemView) {
            super(itemView);

            transactionNumber = (TextView) itemView.findViewById(R.id.transaction_number);
            transactionDate = (TextView) itemView.findViewById(R.id.transaction_date);
            genericName = (TextView) itemView.findViewById(R.id.generic_name);
            quantity = (TextView) itemView.findViewById(R.id.quantity);
        }
    }
}
