package com.e_buyad.marvin.e_buyad;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.e_buyad.marvin.e_buyad.background_task.ImageLoadAsyncTask;
import com.e_buyad.marvin.e_buyad.background_task.LoadAsyncTask;
import com.e_buyad.marvin.e_buyad.background_task.PointAsyncTask;
import com.e_buyad.marvin.e_buyad.model.Session;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public TextView pointsText, loadText;
    private Session session;
    public ImageView imageView;

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();

        loadPointsSetup();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        session = new Session(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View hView =  navigationView.getHeaderView(0);
        imageView = (ImageView) hView.findViewById(R.id.user_image);
        TextView nameText = (TextView) hView.findViewById(R.id.nav_header_name);
        TextView emailText = (TextView) hView.findViewById(R.id.email_text);

        new ImageLoadAsyncTask(this).execute(Configuration.URL + session.get("imageURL"));

        nameText.setText(session.get("name"));
        emailText.setText(TextUtils.isEmpty(session.get("email"))
                ? "No Email" : session.get("email"));

        navigationView.setNavigationItemSelectedListener(this);

        widgetsInit();

    }

    private void widgetsInit() {
        pointsText = (TextView) findViewById(R.id.points);
        loadText = (TextView) findViewById(R.id.load);
    }

    private void loadPointsSetup() {
        Session session = new Session(this);

        new LoadAsyncTask(this).execute(session.get("username"));
        new PointAsyncTask(this).execute(session.get("username"));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        Intent intent = new Intent(Intent.ACTION_MAIN);

        intent.addCategory(Intent.CATEGORY_HOME);

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Session session = new Session(this);

        if(TextUtils.isEmpty(session.get("username"))) {
            Intent intent = new Intent(this, MainActivity.class);

            startActivity(intent);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;

        if (id == R.id.home) {

        } else if (id == R.id.profile) {
            intent = new Intent(this, ProfileActivity.class);

            startActivity(intent);
        } else if (id == R.id.shop) {
            intent = new Intent(this, ShopActivity.class);

            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if(id == R.id.history) {
            intent = new Intent(this, HistoryActivity.class);

            startActivity(intent);
        } else if(id == R.id.logout) {
            Session session = new Session(this);

            session.clearAll();

            intent = new Intent(this, MainActivity.class);

            startActivity(intent);
        } else if(id == R.id.cardview_shopping_list) {
            intent = new Intent(this, ShoppingCartListActivity.class);

            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
