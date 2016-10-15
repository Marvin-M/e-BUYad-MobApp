package com.e_buyad.marvin.e_buyad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.e_buyad.marvin.e_buyad.background_task.LoginAsyncTask;
import com.e_buyad.marvin.e_buyad.business.Login;
import com.e_buyad.marvin.e_buyad.model.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    EditText username, password;

    @Override
    protected void onResume() {
        super.onResume();

        checkLoggedUser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextInit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        
        Intent intent = new Intent(Intent.ACTION_MAIN);

        intent.addCategory(Intent.CATEGORY_HOME);

        startActivity(intent);
    }

    private void checkLoggedUser() {
        Session session = new Session(this);

        if(!TextUtils.isEmpty(session.get("username"))) {
            Intent intent = new Intent(this, HomeActivity.class);

            startActivity(intent);
        }
    }

    private void editTextInit() {
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
    }

    public void loginOnClick(View view) throws ExecutionException, InterruptedException {
        Map<String, String> userMap = new HashMap<>();

        userMap.put("username", username.getText().toString());
        userMap.put("password", password.getText().toString());

        if (Login.isValid(userMap).get("status").equals("S")) {
            new LoginAsyncTask(this).execute(
                username.getText().toString(),
                password.getText().toString()
            );
        } else {
            Toast.makeText(this, Login.isValid(userMap).get("message"), Toast.LENGTH_LONG)
                    .show();
        }
    }

    public void loginQrOnClick(View view) {
        Intent intent = new Intent(this, QRLoginActivity.class);

        startActivity(intent);
    }
}
