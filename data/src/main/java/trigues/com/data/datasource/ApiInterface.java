package trigues.com.data.datasource;

import com.trigues.callback.DefaultCallback;
import com.trigues.entity.Product;


/**
 * Created by mbaque on 15/03/2017.
 */

public interface ApiInterface {

    //Functions:

    void getUserProductDetails(int productId, GetUserProductDataDetails dataCallback);

    void showProducts(int userID, showProducts dataCallback);


    //Callbacks:

    interface GetUserProductDataDetails extends DefaultCallback<Product> {}
    interface showProducts extends DefaultCallback<Product> {}
}
