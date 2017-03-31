package trigues.com.data.datasource;

import com.trigues.callback.DefaultCallback;
import com.trigues.entity.Product;
import com.trigues.entity.User;

/**
 * Created by mbaque on 15/03/2017.
 */

public interface ApiInterface {

    void login(User user, BooleanDataCallback dataCallback);

    void register(User param, BooleanDataCallback datacallback );

    void getUserProductDetails(int productId, ProductDataCallback dataCallback);

    interface BooleanDataCallback extends DefaultCallback<Boolean> {}

    interface ProductDataCallback extends DefaultCallback<Product> {}

}

