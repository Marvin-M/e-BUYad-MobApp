package com.e_buyad.marvin.e_buyad;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.e_buyad.marvin.e_buyad.background_task.TransactionHistoryAsyncTask;
import com.e_buyad.marvin.e_buyad.model.Session;

public class HistoryActivity extends AppCompatActivity {
    private Toolbar toolbar;
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        widgetsInit();
        displayBackArrow();
        setToolbarOnClick();
        recyclerViewSetup();
        populateRecyclerViewData();
    }

    private void widgetsInit() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.history_recyclerview);

        setSupportActionBar(toolbar);
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

    private void recyclerViewSetup() {
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
    }

    private void populateRecyclerViewData() {
        Session session = new Session(this);

        new TransactionHistoryAsyncTask(this).execute(session.get("username"));
    }
}
