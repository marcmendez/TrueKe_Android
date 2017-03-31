package com.trigues;

import com.trigues.callback.DefaultCallback;
import com.trigues.entity.Product;
import com.trigues.entity.User;

import java.util.List;

/**
 * Created by mbaque on 15/03/2017.
 */

public interface RepositoryInterface {

    //Functions:

    void getUserProductDetails(int productId, ProductCallback dataCallback);


    void showProducts(int userID, showProductsCallback dataCallback);

    void login(User user, BooleanCallback dataCallback);


    //Callbacks:

    interface ProductCallback extends DefaultCallback<Product>{}
    interface showProductsCallback extends DefaultCallback<List<Product>>{}
    interface BooleanCallback extends DefaultCallback<Boolean>{}
}
