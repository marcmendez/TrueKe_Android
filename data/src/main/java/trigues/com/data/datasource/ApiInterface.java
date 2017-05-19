package trigues.com.data.datasource;

import com.trigues.RepositoryInterface;
import com.trigues.callback.DefaultCallback;
import com.trigues.entity.Payment;
import com.trigues.entity.Product;
import com.trigues.entity.Shipment;
import com.trigues.entity.User;

import java.util.List;

import trigues.com.data.entity.ApiDTO;
import trigues.com.data.entity.CategoryDTO;
import trigues.com.data.entity.ImagePath;
import trigues.com.data.entity.ChatDTO;
import trigues.com.data.entity.LoginDTO;
import trigues.com.data.entity.ProductDTO;
import trigues.com.data.entity.ProductId;


/**
 * Created by mbaque on 15/03/2017.
 */

public interface ApiInterface {

    //Functions:

    void getUserProductDetails(String token, int productId, ProductDataCallback dataCallback);

    void showProducts(String token, int userID, ProductListDataCallback dataCallback);

    void register(User user, BooleanDataCallback dataCallback);

    void login(User user, LoginDataCallback dataCallback);

    void addProduct(String token, ProductDTO product, AddProductDataCallback dataCallback);

    void showProfile(String token, int id,UserDataCallback dataCallback);

    void showPayments(String token, int id, PaymentsCallback paymentDataCallback);

    void showShipments(String token, int id, ShipmentsCallback shipmentsCallback);

    void changeProfile(String token, String id, String type, String value, BooleanDataCallback booleanDataCallback);

    void deleteUser(int user_id, BooleanDataCallback booleanDataCallback);

    void changePayment(Payment payment, BooleanDataCallback booleanDataCallback);

    void newPayment(String token, Payment payment, BooleanDataCallback booleanDataCallback);

    void deletePayment(String token, int payment_id, BooleanDataCallback booleanDataCallback);

    void deleteShipment(String token, int shipment_id, BooleanDataCallback booleanDataCallback);

    void newShipment(String token, Shipment shipment, BooleanDataCallback booleanDataCallback);

    void changeShipment(Shipment shipment, BooleanDataCallback booleanDataCallback);

    void getMatchmakingProducts(String token, int prodID, ProductListDataCallback dataCallback);

    void deleteProduct(String token, int product_id, BooleanDataCallback booleanDataCallback);

    void addProductCategory(String token, List<String> product_id, BooleanDataCallback booleanDataCallback);

    void deleteProductCategory(String token, List<String> product_id, BooleanDataCallback booleanDataCallback);

    void getDesiredCategories(String token, int product_id, StringListDataCallback stringListDataCallback);

    void acceptMatch(String token, Integer[] productsID, VoidDataCallback voidDataCallback);

    void rejectMatch(String token, Integer[] productsID, VoidDataCallback voidDataCallback);

    void addImagesProduct(String token, int product_id, String image_md5, BooleanDataCallback booleanDataCallback);
    void getUserChats(String token, int userID, ChatListDataCallback voidDataCallback);

    void createTrueke(String chatID, String tokenadmin, VoidDataCallback voidDataCallback);

    void addImages(String image_base64, ImagesDataCallback ImageDataCallback);

    void getImagesProduct(int product_id, GetImagesProductDataCallback dataCallback);

    void getImages(String md5, ImagesDataCallback ImageDataCallback);

    void changeUserImageProfile(String token, String id, String image_path, BooleanDataCallback booleanDataCallback);

    //Callbacks:

    interface UserDataCallback extends DefaultCallback<ApiDTO<List<User>>>{}

    interface ProductDataCallback extends DefaultCallback<Product> {}

    interface ProductListDataCallback extends DefaultCallback<ApiDTO<List<Product>>> {}

    interface BooleanDataCallback extends DefaultCallback<Boolean> {}

    interface VoidDataCallback extends DefaultCallback<Void> {}

    interface LoginDataCallback extends DefaultCallback<ApiDTO<LoginDTO>> {}

    interface PaymentsCallback extends DefaultCallback<ApiDTO<List<Payment>>>{}

    interface ShipmentsCallback extends DefaultCallback<ApiDTO<List<Shipment>>>{}

    interface StringListDataCallback extends DefaultCallback<ApiDTO<List<CategoryDTO>>>{}

    interface ChatListDataCallback extends DefaultCallback<ApiDTO<List<ChatDTO>>>{}

    interface AddProductDataCallback extends DefaultCallback<ApiDTO<ProductId>> {}

    interface ImagesDataCallback extends DefaultCallback<ApiDTO<String>> {}

    interface GetImagesProductDataCallback extends DefaultCallback<ApiDTO<List<String>>> {}

}
