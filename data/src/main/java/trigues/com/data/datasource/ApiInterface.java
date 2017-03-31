package trigues.com.data.datasource;

import com.trigues.callback.DefaultCallback;
import com.trigues.entity.Product;
import com.trigues.entity.User;

import java.util.List;


/**
 * Created by mbaque on 15/03/2017.
 */

public interface ApiInterface {

    //Functions:

    void getUserProductDetails(int productId, GetUserProductDataDetails dataCallback);

    void showProducts(int userID, showProducts dataCallback);

    void register(User user, BooleanDataCallback dataCallback);

    void login(User user, BooleanDataCallback dataCallback);


    //Callbacks:

    interface GetUserProductDataDetails extends DefaultCallback<Product> {}
    interface showProducts extends DefaultCallback<List<Product>> {}
    interface BooleanDataCallback extends DefaultCallback<Boolean> {}
}
