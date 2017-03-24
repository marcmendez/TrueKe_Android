package trigues.com.trueke.view.impl;

/**
 * Created by Alba on 21/03/2017.
 */

import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import trigues.com.trueke.R;

public class SplashActivityImpl extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, ShowProductsActivityImpl.class);
        startActivity(intent);
        finish();

    }


}
