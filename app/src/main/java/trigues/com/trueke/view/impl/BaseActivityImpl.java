package trigues.com.trueke.view.impl;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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

    protected ProgressDialog progressDialog;
    private FrameLayout contentContainer;

    private boolean isShowingFragment = false;

    @LayoutRes
    private int activityResID;

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
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(R.layout.activity_base);

        this.activityResID = layoutResID;

        this.contentContainer = (FrameLayout) findViewById(getContentLayoutId());
        LayoutInflater.from(this).inflate(layoutResID, contentContainer);
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

    protected void addContentFragment(Fragment fragment){
        contentContainer.removeAllViews();
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        getSupportFragmentManager().beginTransaction().add(getContentLayoutId(), fragment).commit();

        this.isShowingFragment = true;
    }

    protected void restoreActivityContent(){
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        LayoutInflater.from(this).inflate(activityResID, contentContainer);

        this.isShowingFragment = false;
    }

    @Override
    public void onBackPressed() {
        if(isShowingFragment){
            restoreActivityContent();
        }
        else{
            super.onBackPressed();
        }
    }

    private int getContentLayoutId(){
        return R.id.contentLayout;
    }
}
