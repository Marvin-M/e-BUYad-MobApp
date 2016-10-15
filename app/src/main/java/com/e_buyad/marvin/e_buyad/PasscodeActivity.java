package com.e_buyad.marvin.e_buyad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.e_buyad.marvin.e_buyad.model.Session;

import java.util.HashMap;

public class PasscodeActivity extends AppCompatActivity {

    private String password,username, email, imageUrl, name;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);

        getExtras();
        widgetsInit();
    }

    private void widgetsInit() {
        passwordEditText = (EditText) findViewById(R.id.password);
    }

    private void getExtras() {
        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            password    = extras.getString("PIN_CODE");
            username    = extras.getString("USERNAME");
            email       = extras.getString("EMAIL");
            imageUrl    = extras.getString("IMG_URL");
            name        = extras.getString("NAME");
        }
    }

    public void loginOnClick(View view) {
        if(passwordEditText.getText().toString().equals(password)) {
            HashMap<String, String> hashMap = new HashMap<>();
            Session session = new Session(this);

            hashMap.put("username", username);
            hashMap.put("name", name);
            hashMap.put("email", email);
            hashMap.put("imageURL", imageUrl);

            session.set(hashMap);

            Intent intent = new Intent(this, HomeActivity.class);

            startActivity(intent);
        } else {
            Toast.makeText(
                    this,
                    getResources().getString(R.string.password_mismatch_text),
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}
