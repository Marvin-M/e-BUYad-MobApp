package com.e_buyad.marvin.e_buyad.background_task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.e_buyad.marvin.e_buyad.Configuration;
import com.e_buyad.marvin.e_buyad.ProfileActivity;
import com.e_buyad.marvin.e_buyad.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProfileAsyncTask extends AsyncTask<String, Void, String> {

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

    public ProfileAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        StringBuilder stringBuilder = new StringBuilder();
        String username = params[0];

        Log.e("USERNAME", username);

        try {
            URL url = new URL(Configuration.URL + "api/v1/members/" + username);
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
                ((ProfileActivity) context).toolbar.setTitle(
                        jsonObject.getJSONObject("data").getString("strMemFullName")
                );
                ((ProfileActivity) context).nameText.setText(
                        jsonObject.getJSONObject("data").getString("strMemFullName")
                );
                ((ProfileActivity) context).usernameText.setText(
                        jsonObject.getJSONObject("data").getString("strMemCode")
                );
                ((ProfileActivity) context).addressText.setText(
                        jsonObject.getJSONObject("data").getString("strMemAddress")
                );
                ((ProfileActivity) context).birthdayText.setText(
                        jsonObject.getJSONObject("data").getString("datMemBirthday")
                );
                ((ProfileActivity) context).oscaIdText.setText(
                        jsonObject.getJSONObject("data").isNull("strMemOSCAID")
                        ? "---"
                                : jsonObject.getJSONObject("data").getString("strMemOSCAID")
                );
                ((ProfileActivity) context).genderText.setText(
                        TextUtils.isEmpty(
                                jsonObject.getJSONObject("data").getString("strMemEmail")
                        ) ? "---"
                                : jsonObject.getJSONObject("data").getString("strMemEmail")
                );
            } else {
                Toast.makeText(
                        context,
                        context.getResources().getString(R.string.user_not_found_text),
                        Toast.LENGTH_LONG
                ).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
