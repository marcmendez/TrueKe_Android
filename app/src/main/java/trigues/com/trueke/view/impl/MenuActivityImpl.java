package trigues.com.trueke.view.impl;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import javax.inject.Inject;

import trigues.com.trueke.R;
import trigues.com.trueke.dependencyinjection.App;
import trigues.com.trueke.dependencyinjection.activity.ActivityModule;
import trigues.com.trueke.dependencyinjection.view.ViewModule;
import trigues.com.trueke.presenter.MenuPresenter;
import trigues.com.trueke.view.MenuActivity;

/**
 * Created by mbaque on 18/03/2017.
 */

public class MenuActivityImpl extends BaseActivityImpl implements MenuActivity {

    @Inject
    MenuPresenter presenter;

    protected DrawerLayout mDrawerLayout;
    protected ActionBarDrawerToggle mDrawerToggle;
    protected NavigationView mNavigationView;
    private FrameLayout contentContainer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((App) getApplication())
                .getComponent()
                .plus(new ActivityModule(this),
                        new ViewModule(this))
                .inject(this);

    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_base);

        this.contentContainer = (FrameLayout) findViewById(R.id.contentLayout);
        LayoutInflater.from(this).inflate(layoutResID, contentContainer);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        if (getToolbarLayout() != null) {
            FrameLayout frameToolbar = (FrameLayout) findViewById(R.id.toolbar_layout);
            LayoutInflater.from(this).inflate(getToolbarLayout(), frameToolbar);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        generateMenu(toolbar);
    }

    private void generateMenu(Toolbar toolbar) {
        setSupportActionBar(toolbar);

        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                   //host Activity
                mDrawerLayout,          //DrawerLayout object
                toolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        if (getNavigationViewLayout() != null) {
            FrameLayout navigationContent = (FrameLayout) findViewById(R.id.leftContent);
            LayoutInflater.from(this).inflate(getNavigationViewLayout(), navigationContent);
            mNavigationView = (NavigationView) findViewById(R.id.app_navigation_view);
        }
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return false;
            }
        });

        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                InputMethodManager inputMethodManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_user_products_list:
                        startActivity(new Intent(MenuActivityImpl.this, UserProductsListActivityImpl.class));
                        return true;
                    case R.id.menu_logout:
                        presenter.logout();
                }

                return false;
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public Integer getToolbarLayout() {
        return R.layout.toolbar_core;
    }

    protected Integer getNavigationViewLayout() {
        return R.layout.app_navigation_view;
    }

    @Override
    public void onLogout() {
        startActivity(new Intent(this, LoginActivityImpl.class));
        finish();
    }
}
