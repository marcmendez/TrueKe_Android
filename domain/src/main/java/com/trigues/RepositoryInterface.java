package com.trigues;

import com.trigues.callback.DefaultCallback;
import com.trigues.entity.ChatInfo;
import com.trigues.entity.ChatMessage;
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

    void showProducts(ProductListCallback dataCallback);

    void login(User user, BooleanCallback dataCallback);

    void showProfile(UserCallback dataCallback);

    void deleteDesiredCategory(Product product, VoidCallback dataCallback);

    void register(User user, BooleanCallback dataCallback);

    void addProduct(Product product, BooleanCallback dataCallback);

    void logout(VoidCallback dataCallback);

    void showPayments(Integer id, PaymentCallback dataCallback);

    void showShipments(Integer id, ShipmentCallback dataCallback);

    void changeProfile(String type, String value, BooleanCallback dataCallback);

    void deleteUser(int user_id, BooleanCallback dataCallback);

    void changePayment(Payment payment, BooleanCallback dataCallback);

    void newPayment(Payment payment, BooleanCallback dataCallback);

    void deletePayment(int payment_id, BooleanCallback dataCallback);

    void deleteShipment(int shipment_id, BooleanCallback dataCallback);

    void newShipment(Shipment shipment, BooleanCallback dataCallback);

    void changeShipment(Shipment shipment, BooleanCallback dataCallback);

    void acceptMatch(Integer[] productID, VoidCallback dataCallback);

    void rejectMatch(Integer[] productsID, VoidCallback dataCallback);

    void getMatchmakingProducts(int prodID, ProductListCallback dataCallback);

    void deleteProduct(int product_id, BooleanCallback dataCallback);

    void addProductCategory(List<String> product_id, BooleanCallback dataCallback);

    void deleteProductCategory(List<String> product_id, BooleanCallback dataCallback);

    void getDesiredCategories(int productID, StringListCallback dataCallback);

    void getUserChats(int userID, ChatListCallback dataCallback);

    void getChatMessages(String chatId, ChatMessagesCallback dataCallback);

    void setMessageAsRead(String chatId, String key);

    void sendChatMessage(ChatMessage message, VoidCallback dataCallback);

    //Callbacks:

    interface VoidCallback extends DefaultCallback<Void> {}
    interface ProductCallback extends DefaultCallback<Product>{}
    interface ProductListCallback extends DefaultCallback<List<Product>>{}
    interface BooleanCallback extends DefaultCallback<Boolean>{}
    interface UserCallback extends DefaultCallback<User>{}
    interface PaymentCallback extends DefaultCallback<List<Payment>>{}
    interface ShipmentCallback extends DefaultCallback<List<Shipment>>{}
    interface StringListCallback extends DefaultCallback<List<String>>{}
    interface ChatListCallback extends DefaultCallback<List<ChatInfo>>{}
    interface ChatMessagesCallback extends DefaultCallback<ChatMessage> {}
}
