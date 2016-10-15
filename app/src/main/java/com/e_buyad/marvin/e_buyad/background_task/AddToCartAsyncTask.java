package com.e_buyad.marvin.e_buyad.background_task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.e_buyad.marvin.e_buyad.Configuration;
import com.e_buyad.marvin.e_buyad.ProductDetailActivity;
import com.e_buyad.marvin.e_buyad.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * User-defined background task for add to cart
 */
public class AddToCartAsyncTask extends AsyncTask<String, Void, String> {
    private Context context;
    private ProgressDialog progressDialog;

    public AddToCartAsyncTask(Context context) {
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
        String productCode = params[0];
        String username = params[1];
        StringBuilder stringBuilder = new StringBuilder();

        try {
            String URL_ROUTE = Configuration.URL + "api/v1/shopping-carts";
            URL url = new URL(URL_ROUTE);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(outputStream, "UTF-8")
            );
            String data = String.format("%s=%s&%s=%s",
                    URLEncoder.encode("username", "UTF-8"),
                    URLEncoder.encode(username, "UTF-8"),
                    URLEncoder.encode("product_code", "UTF-8"),
                    URLEncoder.encode(productCode, "UTF-8")
            );

            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        progressDialog.dismiss();

        Toast.makeText(
            this.context,
                "Successfully added to cart",
                Toast.LENGTH_LONG
        ).show();

        ((ProductDetailActivity) context).finish();
    }
}
