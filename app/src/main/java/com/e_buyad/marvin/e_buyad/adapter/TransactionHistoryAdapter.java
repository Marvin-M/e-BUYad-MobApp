package com.e_buyad.marvin.e_buyad.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.e_buyad.marvin.e_buyad.R;
import com.e_buyad.marvin.e_buyad.model.TransactionHistory;
import com.google.android.gms.vision.text.Text;
import com.google.firebase.database.Transaction;

import java.util.List;

/**
 * User-defined adapter for transaction history
 */
public class TransactionHistoryAdapter
        extends RecyclerView.Adapter<TransactionHistoryAdapter.TransactionHistoryViewHolder> {
    private List<TransactionHistory> transactionHistoryList;

    public TransactionHistoryAdapter(List<TransactionHistory> transactionHistoryList) {
        this.transactionHistoryList = transactionHistoryList;
    }

    @Override
    public TransactionHistoryAdapter.TransactionHistoryViewHolder
        onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(
                parent.getContext()
        ).inflate(
                R.layout.cardview_history,
                parent,
                false
        );

        return new TransactionHistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(
            TransactionHistoryAdapter.TransactionHistoryViewHolder holder,
            int position
    ) {
        TransactionHistory transactionHistory = transactionHistoryList.get(position);

        holder.transactionDate.setText(transactionHistory.getTransactionDate());
        holder.totalAmount.setText(String.format(
                "Total Amount: %.2fPhp",
                transactionHistory.getTotalAmount()
        ));
    }

    @Override
    public int getItemCount() {
        return transactionHistoryList.size();
    }

    public static class TransactionHistoryViewHolder extends RecyclerView.ViewHolder {
        protected TextView transactionDate;
        protected TextView totalAmount;

        public TransactionHistoryViewHolder(View itemView) {
            super(itemView);

            this.transactionDate = (TextView) itemView.findViewById(R.id.date_of_transaction);
            this.totalAmount = (TextView) itemView.findViewById(R.id.total_amount);
        }
    }
}
