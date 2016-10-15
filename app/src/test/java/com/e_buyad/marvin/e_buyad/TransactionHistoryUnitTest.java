package com.e_buyad.marvin.e_buyad;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class TransactionHistoryUnitTest {
    @Test
    public void retrieveDataIsCorrect() throws Exception {
        JSONObject jsonObject = new JSONObject(getResult());
        JSONArray jsonArray = jsonObject.getJSONArray("data");

        assertNotEquals(0, jsonArray.length());
        assertEquals(
                "2016-07-23 09:22:45",
                jsonArray.getJSONObject(0).getString("transactionDate")
        );

        System.out.println(getResult());
    }

    private String getResult() {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            URL url = new URL(Configuration.URL + "api/v1/transaction-histories/"
                    + "MEM00003");
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
}