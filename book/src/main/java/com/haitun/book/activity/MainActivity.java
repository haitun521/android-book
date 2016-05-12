package com.haitun.book.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haitun.book.R;
import com.haitun.book.event.MyEvent;
import com.haitun.book.fragment.BaseFragment;
import com.haitun.book.fragment.BookListFragment;
import com.haitun.book.fragment.ReviewListFragment;
import com.haitun.book.utils.Constant;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private TextView mNameTextView;
    private LinearLayout mPersonLinearLayout;
    private NavigationView navigationView;
    private DrawerLayout drawer;

    private SharedPreferences sp = null;
    private String name;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.setVisibility(View.GONE);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        mNameTextView = (TextView) headerView.findViewById(R.id.nav_name);
        mPersonLinearLayout = (LinearLayout) headerView.findViewById(R.id.nav_person_layout);
        initView();
    }


    public void accessActivity(Class clazz) {
        Intent intent = new Intent(MainActivity.this, clazz);
        startActivity(intent);
    }

    private void initView() {
        sp = getSharedPreferences("user", MODE_PRIVATE);
        name = sp.getString("name", "");
        userId=sp.getString("id","");


        if (!name.equals("")) {
            mNameTextView.setText(name);
        }

        mPersonLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                if (!name.equals("")) {
                    accessActivity(PersonActivity.class);
                } else {
                    accessActivity(LoginActivity.class);
                }
            }
        });
        replaceFragment(BookListFragment.newInstance(userId));
    }

    private void replaceFragment(BaseFragment baseFragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, baseFragment).commit();
    }


    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
        } else if (id == R.id.action_search) {
            accessActivity(SearchActivity.class);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent(this, ScanActivity.class);
            intent.putExtra(Constant.REQUEST_SCAN_MODE, Constant.REQUEST_SCAN_MODE_ALL_MODE);
            startActivity(intent);

        } else if (id == R.id.nav_index) {
            replaceFragment(BookListFragment.newInstance(userId));
            getSupportActionBar().setTitle(R.string.app_name);
        } else if (id == R.id.nav_review) {

            if(userId.equals("")){
                accessActivity(LoginActivity.class);
            }else {
                replaceFragment(ReviewListFragment.newInstance(userId));
                getSupportActionBar().setTitle(R.string.review);
            }
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void handleEvent(MyEvent event) {
        if(event.getId()==null) {
            finish();
        }else{
            userId=event.getId();
            name=event.getName();
            mNameTextView.setText(name);
        }

    }
}
