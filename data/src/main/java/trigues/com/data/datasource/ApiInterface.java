package trigues.com.data.datasource;

import com.trigues.callback.DefaultCallback;
import com.trigues.entity.Payment;
import com.trigues.entity.Product;
import com.trigues.entity.Shipment;
import com.trigues.entity.User;

import java.util.List;


/**
 * Created by mbaque on 15/03/2017.
 */

public interface ApiInterface {

    //Functions:

    void getUserProductDetails(int productId, ProductDataCallback dataCallback);

    void showProducts(int userID, ProductListDataCallback dataCallback);

    void register(User user, BooleanDataCallback dataCallback);

    void login(User user, BooleanDataCallback dataCallback);

    void showProfile(int userID, UserDataCallback dataCallback);

    void showPaymentInfo(int id, PaymentDataCallback paymentDataCallback);

    void showShipmentInfo(Integer id, ShipmentDataCallback shipmentDataCallback);

    void changeProfile(User user, BooleanDataCallback booleanDataCallback);


    //Callbacks:

    interface UserDataCallback extends DefaultCallback<User>{}

    interface ProductDataCallback extends DefaultCallback<Product> {}

    interface ProductListDataCallback extends DefaultCallback<List<Product>> {}

    interface BooleanDataCallback extends DefaultCallback<Boolean> {}

    interface VoidDataCallback extends DefaultCallback<Void> {}

    interface PaymentDataCallback extends DefaultCallback<Payment>{}

    interface ShipmentDataCallback extends DefaultCallback<Shipment>{}
}
