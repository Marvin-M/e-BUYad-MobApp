package com.e_buyad.marvin.e_buyad.model;

import java.util.HashMap;

/**
 * Session interface
 *
 * @version 1.0
 */
public interface SessionInterface {
    void set(HashMap<String, String> maps);

    String get(String keyName);

    void clearAll();
}
