package trigues.com.trueke.view.impl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import trigues.com.trueke.R;
import trigues.com.trueke.view.LoginActivity;

public class LoginActivityImpl extends AppCompatActivity implements LoginActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
