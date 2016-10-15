package com.e_buyad.marvin.e_buyad.background_task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.e_buyad.marvin.e_buyad.Configuration;
import com.e_buyad.marvin.e_buyad.R;
import com.e_buyad.marvin.e_buyad.ShopActivity;
import com.e_buyad.marvin.e_buyad.adapter.ShopAdapter;
import com.e_buyad.marvin.e_buyad.adapter.ShoppingListAdapter;
import com.e_buyad.marvin.e_buyad.model.Product;

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
 * User-defined background task for shop
 */
public class ShopAsyncTask extends AsyncTask<String, Void, String> {
    private Context context;
    private ProgressDialog progressDialog;

    public ShopAsyncTask(Context context) {
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

        try {
            URL url = new URL(Configuration.URL + "api/v1/products");
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

        Product product;

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            List<Product> productList
                    = new ArrayList<>();

            if(jsonArray.length() > 0) {
                for(int i = 0; i < jsonArray.length(); i++) {
                    product = new Product();

                    product.setProductCode(
                            jsonArray.getJSONObject(i).getString("code")
                    );

                    product.setGeneric(
                            jsonArray.getJSONObject(i).isNull("generic")
                            ? "---" : jsonArray.getJSONObject(i).getString("generic")
                    );
                    product.setBrand(
                            jsonArray.getJSONObject(i).isNull("brand")
                            ? "---" : jsonArray.getJSONObject(i).getString("brand")
                    );

                    product.setMedName(
                            jsonArray.getJSONObject(i).isNull("NMedName")
                            ? "---" : jsonArray.getJSONObject(i).getString("NMedName")
                    );

                    product.setMedSize(
                            jsonArray.getJSONObject(i).getString("medSize")
                    );

                    product.setPrice(
                            jsonArray.getJSONObject(i).getString("pricePerPiece")
                    );

                    productList.add(product);
                }

                ((ShopActivity) context).recyclerView
                        .setAdapter(new ShopAdapter(context, productList));
            } else {
                Toast.makeText(
                        context,
                        context.getResources().getString(R.string.empty_shop_item_text),
                        Toast.LENGTH_LONG
                ).show();
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
}
