package com.e_buyad.marvin.e_buyad.background_task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.e_buyad.marvin.e_buyad.Configuration;
import com.e_buyad.marvin.e_buyad.HistoryActivity;
import com.e_buyad.marvin.e_buyad.R;
import com.e_buyad.marvin.e_buyad.adapter.TransactionHistoryAdapter;
import com.e_buyad.marvin.e_buyad.model.TransactionHistory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * User-defined background task class for transaction history
 */
public class TransactionHistoryAsyncTask extends AsyncTask<String, Void, String> {
    private Context context;
    private ProgressDialog progressDialog;

    public TransactionHistoryAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = new ProgressDialog(context);

        progressDialog.setMessage(context.getResources().getString(R.string.loading_text));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        StringBuilder stringBuilder = new StringBuilder();
        String username = params[0];

        try {
            URL url = new URL(Configuration.URL + "api/v1/transaction-histories/"
                    + username);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            try {
                BufferedReader bufferedReader =
                        new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }

                bufferedReader.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        progressDialog.dismiss();

        TransactionHistory transactionHistory;

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            List<TransactionHistory> transactionHistoryList
                    = new ArrayList<>();

            if(jsonArray.length() > 0) {
                for(int i = 0; i < jsonArray.length(); i++) {
                    transactionHistory = new TransactionHistory();

                    transactionHistory.setTotalAmount(
                            jsonArray.getJSONObject(i).getDouble("totalAmount")
                    );
                    transactionHistory.setTransactionDate(
                            jsonArray.getJSONObject(i).getString("transactionDate")
                    );

                    transactionHistoryList.add(transactionHistory);
                }

                ((HistoryActivity) context).recyclerView
                        .setAdapter(new TransactionHistoryAdapter(transactionHistoryList));
            } else {
                Toast.makeText(
                        context,
                        context.getResources().getString(R.string.empty_trans_history_text),
                        Toast.LENGTH_LONG
                ).show();
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
}
