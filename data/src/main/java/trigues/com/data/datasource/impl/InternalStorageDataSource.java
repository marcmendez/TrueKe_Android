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

    private static final String USER_TOKEN = "user_token";

    SharedPreferences internalStorage;

    @Inject
    public InternalStorageDataSource(@ForApp Context context) {
        internalStorage = context.getSharedPreferences("TrueKe", Context.MODE_PRIVATE);
    }


    @Override
    public boolean isUserLogged() {
        String token = internalStorage.getString(USER_TOKEN, null);
        return token != null;
    }

    @Override
    public void saveToken(String token) {
        internalStorage.edit().putString(USER_TOKEN, token).apply();
    }

    @Override
    public String getToken() {
        String token = internalStorage.getString(USER_TOKEN, null);
        return token;
    }

    @Override
    public void onLogOut() {
        internalStorage.edit().remove(USER_TOKEN).apply();
    }
}
