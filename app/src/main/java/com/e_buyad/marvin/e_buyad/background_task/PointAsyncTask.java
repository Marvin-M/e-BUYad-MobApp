package com.e_buyad.marvin.e_buyad.background_task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.e_buyad.marvin.e_buyad.Configuration;
import com.e_buyad.marvin.e_buyad.HomeActivity;
import com.e_buyad.marvin.e_buyad.LoadPointActivity;
import com.e_buyad.marvin.e_buyad.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PointAsyncTask extends AsyncTask<String, Void, String> {

    private Context context;
    private ProgressDialog progressDialog;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = new ProgressDialog(context);

        progressDialog.setMessage(context.getResources().getString(R.string.loading_text));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public PointAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        StringBuilder stringBuilder = new StringBuilder();
        String username = params[0];

        try {
            URL url = new URL(Configuration.URL + "api/v1/points/" + username);
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

        try {
            JSONObject jsonObject = new JSONObject(result);

            if(!jsonObject.isNull("data")) {
               if(context.getClass().getSimpleName().equals("LoadPointActivity")) {
                   ((LoadPointActivity) context).pointsText.setText(
                           jsonObject.getJSONObject("data").getString("Point")
                   );
               } else {
                   ((HomeActivity) context).pointsText.setText(
                           jsonObject.getJSONObject("data").getString("Point")
                   );
               }
            } else {
                if(context.getClass().getSimpleName().equals("LoadPointActivity")) {
                    ((LoadPointActivity) context).pointsText.setText("0");
                } else {
                    ((HomeActivity) context).pointsText.setText("0");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
