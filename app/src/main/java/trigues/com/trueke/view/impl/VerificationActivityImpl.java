package trigues.com.trueke.view.impl;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.ButterKnife;
import trigues.com.trueke.R;
import trigues.com.trueke.dependencyinjection.App;
import trigues.com.trueke.dependencyinjection.activity.ActivityModule;
import trigues.com.trueke.dependencyinjection.view.ViewModule;
import trigues.com.trueke.presenter.LoginPresenter;
import trigues.com.trueke.sendmail.GMailSender;
import trigues.com.trueke.view.VerificationActivity;
import trigues.com.trueke.view.fragment.LoginFragImpl;
import trigues.com.trueke.view.fragment.RegisterFragImpl;

/**
 * Created by marc on 18/05/17.
 */

public class VerificationActivityImpl extends BaseActivityImpl implements VerificationActivity {

    int code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((App) getApplication())
                .getComponent()
                .plus(new ActivityModule(this),
                        new ViewModule(this))
                .inject(this);

        ButterKnife.bind(this);

        code = getIntent().getIntExtra("product_id", 0);
    }

    public void goToShowProductList() {

        startActivity(new Intent(this, VerificationActivityImpl.class));
    }


}
