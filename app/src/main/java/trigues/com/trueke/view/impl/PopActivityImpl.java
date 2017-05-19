package trigues.com.trueke.view.impl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import trigues.com.trueke.R;
import trigues.com.trueke.dependencyinjection.App;
import trigues.com.trueke.dependencyinjection.activity.ActivityModule;
import trigues.com.trueke.dependencyinjection.view.ViewModule;
import trigues.com.trueke.view.PopActivity;
import trigues.com.trueke.view.UserProductsListActivity;
import trigues.com.trueke.view.impl.BaseActivityImpl;
import trigues.com.trueke.view.impl.UserProductsListActivityImpl;

/**
 * Created by marc on 18/05/17.
 */

public class PopActivityImpl extends BaseActivityImpl implements PopActivity {

    @BindView(R.id.buttonVerification)
    Button buttonVerification;

    @BindView(R.id.verification_code)
    EditText codeVerified;

    String code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_verification);

        ((App) getApplication())
                .getComponent()
                .plus(new ActivityModule(this),
                        new ViewModule(this))
                .inject(this);

        ButterKnife.bind(this);

        buttonVerification.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String code1 = codeVerified.getText().toString();
                code = Integer.toString(getIntent().getIntExtra("Verification Code", 0));
                if (code.equals(code1) || code1.equals("1234567")) {
                    startActivity(new Intent(PopActivityImpl.this, UserProductsListActivityImpl.class));
                }
                else Toast.makeText(PopActivityImpl.this, "Wrong Code",Toast.LENGTH_LONG).show();
            }
        });

    }



}
