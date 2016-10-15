package com.e_buyad.marvin.e_buyad;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.e_buyad.marvin.e_buyad.background_task.ImageLoadAsyncTask;
import com.e_buyad.marvin.e_buyad.background_task.ProfileAsyncTask;
import com.e_buyad.marvin.e_buyad.model.Session;

public class ProfileActivity extends AppCompatActivity {

    public Toolbar toolbar;
    public TextView nameText, usernameText, addressText, birthdayText, oscaIdText, genderText;
    public ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        widgetsInit();
        displayBackArrow();
        setToolbarOnClick();
        setUserDetails();
    }

    private void setUserDetails() {
        Session session = new Session(this);

        new ImageLoadAsyncTask(this).execute(Configuration.URL + session.get("imageURL"));
        new ProfileAsyncTask(this).execute(session.get("username"));
    }

    private void setToolbarOnClick() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void widgetsInit() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageView = (ImageView) findViewById(R.id.image);
        nameText = (TextView) findViewById(R.id.name);
        usernameText = (TextView) findViewById(R.id.username);
        addressText = (TextView) findViewById(R.id.address_details);
        birthdayText = (TextView) findViewById(R.id.birthday_details);
        oscaIdText = (TextView) findViewById(R.id.osca_id_details);
        genderText = (TextView) findViewById(R.id.email_details);
    }

    private void displayBackArrow() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
