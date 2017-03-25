package com.trigues;

import com.trigues.callback.DefaultCallback;
import com.trigues.entity.Product;

/**
 * Created by mbaque on 15/03/2017.
 */

public interface RepositoryInterface {

    //Functions:

    void getUserProductDetails(int productId, GetUserProductDetailsCallback dataCallback);


    //Callbacks:

    interface GetUserProductDetailsCallback extends DefaultCallback<Product>{}
}
