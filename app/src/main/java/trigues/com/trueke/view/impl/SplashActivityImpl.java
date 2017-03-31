package trigues.com.trueke.view.impl;

/**
 * Created by Alba on 21/03/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashActivityImpl extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, LoginActivityImpl.class);
        startActivity(intent);
        finish();

    }


}
