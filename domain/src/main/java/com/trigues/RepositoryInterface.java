package com.trigues;

import com.trigues.callback.DefaultCallback;
import com.trigues.entity.Payment;
import com.trigues.entity.Product;
import com.trigues.entity.Shipment;
import com.trigues.entity.User;

import java.util.List;

/**
 * Created by mbaque on 15/03/2017.
 */

public interface RepositoryInterface {

    //Functions:

    void getUserProductDetails(int productId, ProductCallback dataCallback);

    void showProducts(int userID, ProductListCallback dataCallback);

    void login(User user, BooleanCallback dataCallback);

    void showProfile(UserCallback dataCallback);

    void deleteDesiredCategory(Product product, VoidCallback dataCallback);

    void register(User user, BooleanCallback dataCallback);

    void addProduct(Product product, BooleanCallback dataCallback);

    void logout(VoidCallback dataCallback);

    void showPayments(Integer id, PaymentCallback dataCallback);

    void showShipments(Integer id, ShipmentCallback dataCallback);

    void changeProfile(User user, BooleanCallback dataCallback);

    void deleteUser(int user_id, BooleanCallback dataCallback);

    void changePayment(Payment payment, BooleanCallback dataCallback);

    void newPayment(Payment payment, BooleanCallback dataCallback);

    void deletePayment(int payment_id, BooleanCallback dataCallback);

    void deleteShipment(int shipment_id, BooleanCallback dataCallback);

    void newShipment(Shipment shipment, BooleanCallback dataCallback);

    void changeShipment(Shipment shipment, BooleanCallback dataCallback);

    void deleteProduct(int product_id, BooleanCallback dataCallback);


    //Callbacks:

    interface VoidCallback extends DefaultCallback<Void> {}
    interface ProductCallback extends DefaultCallback<Product>{}
    interface ProductListCallback extends DefaultCallback<List<Product>>{}
    interface BooleanCallback extends DefaultCallback<Boolean>{}
    interface UserCallback extends DefaultCallback<User>{}
    interface PaymentCallback extends DefaultCallback<List<Payment>>{}
    interface ShipmentCallback extends DefaultCallback<List<Shipment>>{}
}
