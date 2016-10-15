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

import java.util.List;

/**
 * User-defined adapter for shop
 */
public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopAdapterViewHolder> {
    private Context context;
    private List<Product> productList;


    public ShopAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public ShopAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(
                parent.getContext()
        ).inflate(
                R.layout.cardview_shop,
                parent,
                false
        );

        return new ShopAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ShopAdapterViewHolder holder, final int position) {
        Product product = productList.get(position);

        holder.genericText.setText(product.getGeneric());
        holder.brandText.setText(product.getBrand());
        holder.medNameText.setText(product.getMedName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailActivity.class);

                intent.putExtra("PRODUCT_ID", productList.get(position).getProductCode());
                intent.putExtra("GENERIC", productList.get(position).getGeneric());
                intent.putExtra("BRAND", productList.get(position).getBrand());
                intent.putExtra("DOSAGE", productList.get(position).getMedSize());
                intent.putExtra("PRICE", productList.get(position).getPrice());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ShopAdapterViewHolder extends RecyclerView.ViewHolder {
        protected TextView genericText;
        protected TextView brandText;
        protected TextView medNameText;

        public ShopAdapterViewHolder(View itemView) {
            super(itemView);

            genericText = (TextView) itemView.findViewById(R.id.generic);
            brandText = (TextView) itemView.findViewById(R.id.brand);
            medNameText = (TextView) itemView.findViewById(R.id.medical_name);
        }
    }
}
