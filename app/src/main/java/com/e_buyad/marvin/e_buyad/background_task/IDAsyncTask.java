package com.e_buyad.marvin.e_buyad.background_task;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.e_buyad.marvin.e_buyad.Configuration;
import com.e_buyad.marvin.e_buyad.PasscodeActivity;
import com.e_buyad.marvin.e_buyad.QRLoginActivity;
import com.e_buyad.marvin.e_buyad.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class IDAsyncTask extends AsyncTask<String, Void, String> {
    private Context context;

    public IDAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        StringBuilder stringBuilder = new StringBuilder();
        String username = params[0];

        try {
            URL url = new URL(Configuration.URL + "api/v1/accounts/" + username);
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

        try {
            JSONObject jsonObject = new JSONObject(result);

            if(!jsonObject.isNull("data")) {
                Intent intent = new Intent(context, PasscodeActivity.class);

                intent.putExtra(
                        "USERNAME",
                        jsonObject.getJSONObject("data").getString("strMemAcctCode")
                );

                intent.putExtra(
                        "NAME",
                        jsonObject.getJSONObject("data").getString("MemName")
                );

                intent.putExtra(
                        "PIN_CODE",
                        jsonObject.getJSONObject("data").getString("strMemAcctPinCode")
                );

                intent.putExtra(
                        "EMAIL",
                        jsonObject.getJSONObject("data").getString("strMemEmail")
                );

                intent.putExtra(
                        "USERNAME",
                        jsonObject.getJSONObject("data").getString("strMemAcctCode")
                );

                intent.putExtra(
                        "IMG_URL",
                        jsonObject.getJSONObject("data").getString("imgMemPhoto")
                );
                context.startActivity(intent);
            } else {
                Toast.makeText(
                        context,
                        context.getResources().getString(R.string.username_not_found_text),
                        Toast.LENGTH_LONG
                ).show();

                ((QRLoginActivity) context).tryAgainButton.setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
