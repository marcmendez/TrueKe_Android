package trigues.com.data.datasource;

import com.trigues.callback.DefaultCallback;
import com.trigues.entity.Payment;
import com.trigues.entity.Product;
import com.trigues.entity.Shipment;
import com.trigues.entity.User;

import java.util.List;

import trigues.com.data.entity.ApiDTO;
import trigues.com.data.entity.LoginDTO;
import trigues.com.data.entity.UserProductsDTO;


/**
 * Created by mbaque on 15/03/2017.
 */

public interface ApiInterface {

    //Functions:

    void getUserProductDetails(int productId, ProductDataCallback dataCallback);

    void showProducts(int userID, ProductListDataCallback dataCallback);

    void register(User user, BooleanDataCallback dataCallback);

    void login(User user, LoginDataCallback dataCallback);

    void addProduct(Product product, BooleanDataCallback dataCallback);

    void showProfile(int userID, UserDataCallback dataCallback);

    void showPayments(int id, PaymentsCallback paymentDataCallback);

    void showShipments(Integer id, ShipmentsCallback shipmentsCallback);

    void changeProfile(User user, BooleanDataCallback booleanDataCallback);

    void deleteUser(int user_id, BooleanDataCallback booleanDataCallback);


    //Callbacks:

    interface UserDataCallback extends DefaultCallback<User>{}

    interface ProductDataCallback extends DefaultCallback<Product> {}

    interface ProductListDataCallback extends DefaultCallback<ApiDTO<UserProductsDTO>> {}

    interface BooleanDataCallback extends DefaultCallback<Boolean> {}

    interface VoidDataCallback extends DefaultCallback<Void> {}

    interface LoginDataCallback extends DefaultCallback<ApiDTO<LoginDTO>> {}

    interface PaymentsCallback extends DefaultCallback<List<Payment>>{}

    interface ShipmentsCallback extends DefaultCallback<List<Shipment>>{}
}
