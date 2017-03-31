package com.trigues;

import com.trigues.callback.DefaultCallback;
import com.trigues.entity.Product;
import com.trigues.entity.User;

/**
 * Created by mbaque on 15/03/2017.
 */

public interface RepositoryInterface {
    //TODO: Definir aqui les funcions del repositori

    void register(User param, BooleanCallback dataCallback);

    void deleteDesiredCategory(Product product, VoidCallback callback);

    void getUserProductDetails(int productId, GetUserProductDetailsCallback callback);

    void login(User user, BooleanCallback dataCallback);

    //TODO: Definir aqui els callbacks (interficies) que s'han de passar com a parametre a cada funció del repositori
    interface BooleanCallback extends DefaultCallback<Boolean> {}

    interface GetUserProductDetailsCallback extends DefaultCallback<Product> {}

    interface VoidCallback extends DefaultCallback<Void> {}
}
