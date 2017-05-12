package trigues.com.data.datasource.impl;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.trigues.entity.Product;
import com.trigues.entity.User;

import javax.inject.Inject;

import trigues.com.data.datasource.InternalStorageInterface;
import trigues.com.data.dependencyinjection.qualifier.ForApp;

/**
 * Created by mbaque on 05/04/2017.
 */

public class InternalStorageDataSource implements InternalStorageInterface {

    private static final String USER_TOKEN = "user_token";
    private static final String USER = "user";
    private static final String PRODUCT_ID = "product_id";

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
    public void saveUser(User user) {
        Gson gson = new Gson();
        String userJson = gson.toJson(user);
        internalStorage.edit().putString(USER, userJson).commit();
    }

    @Override
    public User getUser() {
        Gson gson = new Gson();
        String userJson = internalStorage.getString(USER, null);
        if(userJson!=null) {
            User user = gson.fromJson(userJson, User.class);
            return user;
        }
        return null;
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

    @Override
    public void saveProductId(Integer id) {
        internalStorage.edit().putString(PRODUCT_ID, String.valueOf(id)).commit();
    }

    @Override
    public int getProductId() {
        String productId = internalStorage.getString(PRODUCT_ID, null);
        return Integer.valueOf(productId);
    }


}
