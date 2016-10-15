package com.e_buyad.marvin.e_buyad;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.e_buyad.marvin.e_buyad.background_task.AddToCartAsyncTask;
import com.e_buyad.marvin.e_buyad.model.Session;

public class ProductDetailActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView genericText;
    private TextView brandText;
    private TextView dosageText;
    private TextView priceText;
    private EditText quantity;
    private String productCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        widgetsInit();
        displayBackArrow();
        setToolbarOnClick();
        getDataFromIntent();
    }

    private void widgetsInit() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        quantity = (EditText) findViewById(R.id.quantity);
        genericText = (TextView) findViewById(R.id.generic_name_value);
        brandText = (TextView) findViewById(R.id.brand_name_value);
        dosageText = (TextView) findViewById(R.id.dosage_name_value);
        priceText = (TextView) findViewById(R.id.price_name_value);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    private void getDataFromIntent() {
        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            genericText.setText(extras.getString("GENERIC"));
            brandText.setText(extras.getString("BRAND"));
            dosageText.setText(extras.getString("DOSAGE"));
            priceText.setText(extras.getString("PRICE"));

            toolbar.setTitle(extras.getString("GENERIC"));

            productCode = extras.getString("PRODUCT_ID");
        }
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

    public void addToShoppingCartOnClick(View view) {
        Session session = new Session(this);

        new AddToCartAsyncTask(this).execute(
                productCode,
                session.get("username")
        );
    }
}
