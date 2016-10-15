package com.e_buyad.marvin.e_buyad.model;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * User-defined model for handling sessions in the application
 *
 * @version 1.0
 */
public class Session implements SessionInterface {
    private static final String PREFERENCE_TAG = "UserPreference";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    /**
     * Initializing Session class with context
     * @param context
     */
    public Session(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE_TAG, Context.MODE_PRIVATE);
    }


    /**
     * Store session on user's device
     *
     * @param maps
     */
    public void set(HashMap<String, String> maps) {
        editor = sharedPreferences.edit();

        for(Map.Entry<String, String> entry : maps.entrySet()) {
            editor.putString(entry.getKey(), entry.getValue());
        }

        editor.commit();
    }


    /**
     * Get stored session on user's device
     *
     * @param keyName
     * @return String
     */
    public String get(String keyName) {
        return sharedPreferences.getString(keyName, "");
    }


    /**
     * Clear all stored sessions on user's device
     */
    public void clearAll() {
        editor = sharedPreferences.edit();

        editor.clear();
        editor.commit();
    }
}
