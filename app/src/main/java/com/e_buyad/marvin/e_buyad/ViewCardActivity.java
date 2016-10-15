package com.e_buyad.marvin.e_buyad;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.e_buyad.marvin.e_buyad.background_task.ImageLoadAsyncTask;
import com.e_buyad.marvin.e_buyad.model.Session;

public class ViewCardActivity extends AppCompatActivity {

    public ImageView frontCardImage, backCardImage;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_card);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        widgetsInit();
        displayBackArrow();
        setToolbarOnClick();
        setupCard();
    }

    private void setupCard() {
        Session session = new Session(this);

        new ImageLoadAsyncTask(this).execute(Configuration.URL
                + "storage/member_card/" + session.get("username") + "/front.png",
                "front_card");
        new ImageLoadAsyncTask(this).execute(Configuration.URL
                + "storage/member_card/" + session.get("username") + "/back.png",
                "back_card");
    }

    private void widgetsInit() {
        frontCardImage = (ImageView) findViewById(R.id.front_card);
        backCardImage = (ImageView) findViewById(R.id.back_card);
    }

    private void displayBackArrow() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setToolbarOnClick() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
