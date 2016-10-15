package com.e_buyad.marvin.e_buyad.background_task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.e_buyad.marvin.e_buyad.Configuration;
import com.e_buyad.marvin.e_buyad.HomeActivity;
import com.e_buyad.marvin.e_buyad.LoadPointActivity;
import com.e_buyad.marvin.e_buyad.R;
import com.e_buyad.marvin.e_buyad.model.Session;

import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.HashMap;

public class LoginAsyncTask extends AsyncTask<String, Void, String> {
    private Context context;
    private ProgressDialog progressDialog;
    private HashMap<String, String> values = new HashMap<>();

    public LoginAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = new ProgressDialog(context);

        progressDialog.setMessage(context.getResources().getString(R.string.logging_in_text));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String username = params[0];
        String password = params[1];
        StringBuilder stringBuilder = new StringBuilder();

        try {
            String URL_ROUTE = Configuration.URL + "api/v1/accounts";
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
                    URLEncoder.encode("mem_acct_code", "UTF-8"),
                    URLEncoder.encode(username, "UTF-8"),
                    URLEncoder.encode("mem_acct_pin_code", "UTF-8"),
                    URLEncoder.encode(password, "UTF-8")
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
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        progressDialog.dismiss();

        try {
            JSONObject jsonObject = new JSONObject(result);

            if(!jsonObject.isNull("data")) {
                setSession(
                        jsonObject.getJSONObject("data").getString("strMemAcctCode"),
                        jsonObject.getJSONObject("data").getString("MemName"),
                        jsonObject.getJSONObject("data").getString("strMemEmail"),
                        jsonObject.getJSONObject("data").getString("imgMemPhoto")
                );

                Intent intent = new Intent(context, HomeActivity.class);

                context.startActivity(intent);
            } else {
                Toast.makeText(
                        context,
                        context.getResources().getString(R.string.invalid_user_pass_text),
                        Toast.LENGTH_LONG
                ).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setSession(String username, String name, String email, String imageUrl) {
        Session session = new Session(this.context);

        values.put("username", username);
        values.put("name", name);
        values.put("email", email);
        values.put("imageURL", imageUrl);

        session.set(values);
    }
}
