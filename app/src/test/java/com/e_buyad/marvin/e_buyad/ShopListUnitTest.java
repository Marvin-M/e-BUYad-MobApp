package com.e_buyad.marvin.e_buyad;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static junit.framework.Assert.assertEquals;

/**
 * Unit test for Shop
 */
public class ShopListUnitTest {
    @Test
    public void isRetrievedDataCorrect() throws JSONException {
        JSONObject jsonObject = new JSONObject(getResult());
        JSONArray jsonArray = jsonObject.getJSONArray("data");

        assertEquals(17, jsonArray.length());
    }

    private String getResult() {
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
}
