package trigues.com.data.datasource.impl;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

import trigues.com.data.datasource.InternalStorageInterface;
import trigues.com.data.dependencyinjection.qualifier.ForApp;

/**
 * Created by mbaque on 05/04/2017.
 */

public class InternalStorageDataSource implements InternalStorageInterface {

    SharedPreferences internalStorage;

    @Inject
    public InternalStorageDataSource(@ForApp Context context) {
        internalStorage = context.getSharedPreferences("TrueKe", Context.MODE_PRIVATE);
    }


}
