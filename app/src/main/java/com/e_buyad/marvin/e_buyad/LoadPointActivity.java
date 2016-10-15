package com.e_buyad.marvin.e_buyad;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.e_buyad.marvin.e_buyad.background_task.LoadAsyncTask;
import com.e_buyad.marvin.e_buyad.background_task.PointAsyncTask;
import com.e_buyad.marvin.e_buyad.model.Session;

public class LoadPointActivity extends AppCompatActivity {

    public TextView pointsText, loadText;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_point);

        widgetsInit();
        displayBackArrow();
        setLoadPointDetails();
        setToolbarOnClick();
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

    private void setLoadPointDetails() {
        Session session = new Session(this);

        new LoadAsyncTask(this).execute(session.get("username"));
        new PointAsyncTask(this).execute(session.get("username"));
    }

    private void widgetsInit() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pointsText = (TextView) findViewById(R.id.points);
        loadText = (TextView) findViewById(R.id.load);
    }

}
