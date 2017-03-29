package trigues.com.data.datasource;

import com.trigues.callback.DefaultCallback;
import com.trigues.entity.Product;
import com.trigues.entity.User;

/**
 * Created by mbaque on 15/03/2017.
 */

public interface ApiInterface {

    //TODO: Definir aqui les funcions del datasource

    void register(User param, BooleanDataCallback datacallback );

    void getUserProductDetails(int productId, ProductDataCallback dataCallback);

    //TODO: Definir aqui els callbacks (interficies) que s'han de passar com a parametre a cada funció del datasource

    interface BooleanDataCallback extends DefaultCallback<Boolean> {}

    interface ProductDataCallback extends DefaultCallback<Product> {}

}

