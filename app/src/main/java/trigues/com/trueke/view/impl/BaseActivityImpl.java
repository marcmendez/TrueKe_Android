package trigues.com.trueke.view.impl;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.Toast;

import javax.inject.Inject;

import trigues.com.trueke.R;
import trigues.com.trueke.dependencyinjection.App;
import trigues.com.trueke.dependencyinjection.activity.ActivityModule;
import trigues.com.trueke.dependencyinjection.view.ViewModule;
import trigues.com.trueke.presenter.BasePresenter;
import trigues.com.trueke.view.BaseActivity;

/**
 * Created by mbaque on 15/03/2017.
 */

public class BaseActivityImpl extends AppCompatActivity implements BaseActivity {

    @Inject
    BasePresenter presenter;

    @LayoutRes
    int layoutRes;

    protected ProgressDialog progressDialog;
    private FrameLayout baseContainer;

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

        this.layoutRes = layoutResID;

        this.baseContainer = (FrameLayout) findViewById(R.id.base_content_layout);
        LayoutInflater.from(this).inflate(layoutResID, baseContainer);
    }

    public void setUpBackActionBar(){
        FrameLayout frameToolbar = (FrameLayout) findViewById(R.id.base_toolbar_layout);
        LayoutInflater.from(this).inflate(R.layout.toolbar_core, frameToolbar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setUpBackActionBar(toolbar);
    }

    public void setUpBackActionBar(Toolbar toolbar){
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void addFullScreenFragment(Fragment fragment){

        getSupportFragmentManager().beginTransaction().replace(R.id.base_container, fragment).commit();

    }

    public void addFullScreenFragmentWithTransition(Fragment fragment, @AnimRes int enterTrans, @AnimRes  int exitTrans){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(enterTrans, exitTrans);
        transaction.replace(R.id.base_container, fragment);
        transaction.commit();

    }

    public void removeFullScreenFragment(){
        getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.base_container)).commit();
    }

    @Override
    public void showProgress(String message) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(message);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

    }

    @Override
    public void hideProgress() {

        if(progressDialog!=null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }

    }

    @Override
    public void onError(String error) {

        hideProgress();
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();

    }
}
