package trigues.com.data.repository;

import android.util.Log;

import com.trigues.RepositoryInterface;
import com.trigues.callback.FirebaseChatListener;
import com.trigues.entity.ChatInfo;
import com.trigues.entity.ChatMessage;
import com.trigues.entity.Payment;
import com.trigues.entity.Product;
import com.trigues.entity.Shipment;
import com.trigues.entity.TruekeData;
import com.trigues.entity.TruekePaymentData;
import com.trigues.entity.User;
import com.trigues.entity.VoteData;
import com.trigues.exception.ErrorBundle;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import trigues.com.data.datasource.ApiInterface;
import trigues.com.data.datasource.FirebaseInterface;
import trigues.com.data.datasource.InternalStorageInterface;
import trigues.com.data.entity.ApiDTO;
import trigues.com.data.entity.CategoryDTO;
import trigues.com.data.entity.ChatDTO;
import trigues.com.data.entity.LoginDTO;
import trigues.com.data.entity.ProductDTO;
import trigues.com.data.entity.ProductId;

/**
 * Created by mbaque on 15/03/2017.
 */

public class AppRepository implements RepositoryInterface {

    private ApiInterface apiDataSource;
    private InternalStorageInterface internalStorage;
    private FirebaseInterface firebaseDataSource;

    @Inject
    public AppRepository(ApiInterface apiDataSource, InternalStorageInterface internalStorage, FirebaseInterface firebaseDataSource) {
        this.apiDataSource = apiDataSource;
        this.internalStorage = internalStorage;
        this.firebaseDataSource = firebaseDataSource;
    }



    @Override
    public void showProducts(final ProductListCallback dataCallback) {
        apiDataSource.showProducts(internalStorage.getToken(), internalStorage.getUser().getId(), new ApiInterface.ProductListDataCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(ApiDTO<List<Product>> returnParam) {
                dataCallback.onSuccess(returnParam.getContent());
            }
        });
    }

    @Override
    public void deleteDesiredCategory(Product product, VoidCallback dataCallback) {

    }

    @Override
    public void register(User user, final BooleanCallback dataCallback) {
        apiDataSource.register(user, new ApiInterface.BooleanDataCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(Boolean returnParam) {
                dataCallback.onSuccess(returnParam);
            }
        });
    }

    @Override
    public void logout(VoidCallback dataCallback) {
        internalStorage.onLogOut();
        dataCallback.onSuccess(null);
    }

    @Override
    public void showPayments(Integer id, final PaymentCallback dataCallback) {
        apiDataSource.showPayments(internalStorage.getToken(), internalStorage.getUser().getId(),new ApiInterface.PaymentsCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(ApiDTO<List<Payment>> returnParam) {
                dataCallback.onSuccess(returnParam.getContent());
            }
        });
    }

    @Override
    public void showShipments(Integer id, final ShipmentCallback dataCallback) {
        apiDataSource.showShipments(internalStorage.getToken(),internalStorage.getUser().getId(),new ApiInterface.ShipmentsCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(ApiDTO<List<Shipment>> returnParam) {
                dataCallback.onSuccess(returnParam.getContent());
            }
        });
    }

    @Override
    public void changeProfile(final String type, final String value, final BooleanCallback dataCallback) {
        apiDataSource.changeProfile(internalStorage.getToken(),String.valueOf(internalStorage.getUser().getId()),type,value,new ApiInterface.BooleanDataCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(Boolean returnParam) {
                dataCallback.onSuccess(returnParam);
            }
        });
    }

    @Override
    public void deleteUser(int user_id, final BooleanCallback dataCallback) {
        apiDataSource.deleteUser(user_id, new ApiInterface.BooleanDataCallback(){
            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(Boolean returnParam) {
                dataCallback.onSuccess(returnParam);
            }
        });
    }

    @Override
    public void changePayment(Payment payment, final BooleanCallback dataCallback) {
        apiDataSource.changePayment(payment,new ApiInterface.BooleanDataCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(Boolean returnParam) {
                dataCallback.onSuccess(returnParam);
            }
        });
    }

    @Override
    public void newPayment(Payment payment, final BooleanCallback dataCallback) {
        payment.setUser_id(internalStorage.getUser().getId());
        apiDataSource.newPayment(internalStorage.getToken(),payment,new ApiInterface.BooleanDataCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(Boolean returnParam) {
                dataCallback.onSuccess(returnParam);
            }
        });
    }

    @Override
    public void deletePayment(int payment_id, final BooleanCallback dataCallback) {
        apiDataSource.deletePayment(internalStorage.getToken(), payment_id, new ApiInterface.BooleanDataCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(Boolean returnParam) {
                dataCallback.onSuccess(returnParam);
            }
        });
    }

    @Override
    public void deleteShipment(int shipment_id, final BooleanCallback dataCallback) {
        apiDataSource.deleteShipment(internalStorage.getToken(),shipment_id, new ApiInterface.BooleanDataCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(Boolean returnParam) {
                dataCallback.onSuccess(returnParam);
            }
        });
    }

    @Override
    public void newShipment(Shipment shipment, final BooleanCallback dataCallback) {
        shipment.setUser_id(internalStorage.getUser().getId());
        apiDataSource.newShipment(internalStorage.getToken(),shipment,new ApiInterface.BooleanDataCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(Boolean returnParam) {
                dataCallback.onSuccess(returnParam);
            }
        });
    }

    @Override
    public void changeShipment(Shipment shipment, final BooleanCallback dataCallback) {
        apiDataSource.changeShipment(shipment, new ApiInterface.BooleanDataCallback() {

            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(Boolean returnParam) {
                dataCallback.onSuccess(returnParam);
            }
        });
    }

    @Override
    public void acceptMatch(Integer[] productsID, final VoidCallback dataCallback) {

        apiDataSource.acceptMatch(internalStorage.getToken(), productsID, new ApiInterface.VoidDataCallback() {

            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(Void returnParam) {
                dataCallback.onSuccess(returnParam);
            }
        });

    }

    @Override
    public void rejectMatch(Integer[] productsID, final VoidCallback dataCallback) {
        apiDataSource.rejectMatch(internalStorage.getToken(), productsID, new ApiInterface.VoidDataCallback() {

            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(Void returnParam) {
                dataCallback.onSuccess(returnParam);
            }
        });
    }

    @Override
    public void reportProduct(Integer[] userProdID, final VoidCallback dataCallback) {
        userProdID[0] = internalStorage.getUser().getId();
        apiDataSource.reportProduct(internalStorage.getToken(), userProdID, new ApiInterface.VoidDataCallback() {

            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(Void returnParam) {
                dataCallback.onSuccess(returnParam);
            }
        });
    }

    @Override
    public void getMatchmakingProducts(int prodID, final ProductListCallback dataCallback) {

        apiDataSource.getMatchmakingProducts(internalStorage.getToken(), prodID, new ApiInterface.ProductListDataCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(ApiDTO<List<Product>> returnParam) {
                dataCallback.onSuccess(returnParam.getContent());
            }
        });

    }


    @Override
    public void login(User user, final BooleanCallback dataCallback) {
        apiDataSource.login(user, new ApiInterface.LoginDataCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(ApiDTO<LoginDTO> returnParam) {
                if(!returnParam.getError()) {
                    internalStorage.saveToken(returnParam.getContent().getToken());
                    internalStorage.saveUser(returnParam.getContent().getUser());
                }
                    dataCallback.onSuccess(!returnParam.getError());


            }
        });
    }

    @Override
    public void addProduct(Product product, final BooleanCallback dataCallback) {
        ProductDTO p2 = new ProductDTO(product);
        /*Log.i("addProduct", "token: "+internalStorage.getToken());
        Log.i("addProduct", "userId: "+internalStorage.getUser().getId()+" userId_prod: "+p2.getUserId());
        Log.i("addProduct", "title: "+p2.getTitle());
        Log.i("addProduct", "description: "+p2.getDescription());
        Log.i("addProduct", "desiredCategories: "+p2.getDesiredCategories());
        Log.i("addProduct", "category: "+p2.getProductCategory());
        Log.i("addProduct", "priceMin: "+p2.getMinPrice());
        Log.i("addProduct", "priceMax: "+p2.getMaxPrice());*/
        apiDataSource.addProduct(internalStorage.getToken(), p2, new ApiInterface.AddProductDataCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(ApiDTO<ProductId> returnParam) {
                if(!returnParam.getError()) {
                    internalStorage.saveProductId(returnParam.getContent().getProductId());
                    Log.i("ID PRODUCT: ", "productId: "+internalStorage.getProductId());
                }
                dataCallback.onSuccess(!returnParam.getError());
            }
        });
    }

    @Override
    public void showProfile(final UserCallback dataCallback) {
        apiDataSource.showProfile(internalStorage.getToken(), internalStorage.getUser().getId(),
                new ApiInterface.UserDataCallback()
                {

                    @Override
                    public void onError(ErrorBundle errorBundle) {
                        dataCallback.onError(errorBundle);
                    }

                    @Override
                    public void onSuccess(ApiDTO<List<User>> returnParam) {
                        dataCallback.onSuccess(returnParam.getContent().get(0));
                    }
                });
    }

    @Override
    public void deleteProduct(int product_id, final BooleanCallback dataCallback) {
        apiDataSource.deleteProduct(internalStorage.getToken(),product_id, new ApiInterface.BooleanDataCallback(){
            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(Boolean returnParam) {
                dataCallback.onSuccess(returnParam);
            }
        });
    }

    @Override
    public void addProductCategory(List<String> category, final BooleanCallback dataCallback) {
        if(category.size() == 1) category.add(String.valueOf(internalStorage.getProductId()));
        apiDataSource.addProductCategory(internalStorage.getToken(),category, new ApiInterface.BooleanDataCallback(){
            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(Boolean returnParam) {
                dataCallback.onSuccess(returnParam);
            }
        });
    }

    @Override
    public void deleteProductCategory(List<String> category, final BooleanCallback dataCallback) {
        apiDataSource.deleteProductCategory(internalStorage.getToken(),category, new ApiInterface.BooleanDataCallback(){
            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(Boolean returnParam) {
                dataCallback.onSuccess(returnParam);
            }
        });
    }

    @Override
    public void getDesiredCategories(int productID, final StringListCallback dataCallback) {
        apiDataSource.getDesiredCategories(internalStorage.getToken(),productID, new ApiInterface.StringListDataCallback(){
            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(ApiDTO<List<CategoryDTO>> returnParam/*funcio per cambiar de category dto a list strings*/) {
                List<String> categories = new ArrayList<>();
                for (CategoryDTO element : returnParam.getContent()) {
                    categories.add(element.getCategory());
                }
                dataCallback.onSuccess(categories);
            }
        });
    }

    @Override
    public void addImagesProduct(String image_md5, final BooleanCallback dataCallback) {
        Log.i("image_md5",  "returnParam appRepository: "+image_md5 );
         apiDataSource.addImagesProduct(internalStorage.getToken(),internalStorage.getProductId(), image_md5, new ApiInterface.BooleanDataCallback(){
            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(Boolean returnParam) {
                dataCallback.onSuccess(returnParam);
            }
        });
    }

    @Override
    public void addImages(String image_base64, final ImagesCallback dataCallback) {
        apiDataSource.addImages(image_base64, new ApiInterface.ImagesDataCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(ApiDTO<String> returnParam) {
                if(!returnParam.getError()) dataCallback.onSuccess(returnParam.getContent());
            }
        });
    }

    @Override
    public void getImagesProduct(int product_id, final GetImagesProductCallback dataCallback) {
        apiDataSource.getImagesProduct(product_id, new ApiInterface.GetImagesProductDataCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(ApiDTO<List<String>> returnParam) {
                if(!returnParam.getError()) dataCallback.onSuccess(returnParam.getContent());
            }
        });
    }

    @Override
    public void getImages(String image_md5, final ImagesCallback dataCallback) {
        apiDataSource.getImages(image_md5, new ApiInterface.ImagesDataCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(ApiDTO<String> returnParam) {
                if(!returnParam.getError()) dataCallback.onSuccess(returnParam.getContent());
            }
        });
    }

    @Override
    public void changeProfileUserImage(String image_path, final BooleanCallback dataCallback) {
        apiDataSource.changeUserImageProfile(internalStorage.getToken(),String.valueOf(internalStorage.getUser().getId()),image_path,new ApiInterface.BooleanDataCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(Boolean returnParam) {
                dataCallback.onSuccess(returnParam);
            }
        });
    }

    @Override
    public void voteTrueke(final VoteData voteData, final VoidCallback dataCallback) {
        apiDataSource.voteTrueke(voteData.getRating(),voteData.getOtherProduct(),internalStorage.getToken(),new ApiInterface.VoidDataCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(Void returnParam) {
                dataCallback.onSuccess(null);
            }
        });

        firebaseDataSource.voteTrueke(voteData.getChatId(), voteData.isUser1(), new FirebaseInterface.FirebaseBooleanCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {

            }

            @Override
            public void onSuccess(Boolean returnParam) {
                if(returnParam){
                    //Delete chat
                    apiDataSource.deleteProduct("f4493ed183abba6b096f3903a5fc3b64", voteData.getProduct_id(), new ApiInterface.BooleanDataCallback() {
                        @Override
                        public void onError(ErrorBundle errorBundle) {

                        }

                        @Override
                        public void onSuccess(Boolean returnParam) {

                        }
                    });

                    apiDataSource.deleteProduct("f4493ed183abba6b096f3903a5fc3b64", voteData.getOtherProduct(), new ApiInterface.BooleanDataCallback() {
                        @Override
                        public void onError(ErrorBundle errorBundle) {

                        }

                        @Override
                        public void onSuccess(Boolean returnParam) {

                        }
                    });
                }
            }
        });
    }

    @Override
    public void deleteChat(String chatId, final VoidCallback dataCallback) {
        apiDataSource.deleteChat(chatId, internalStorage.getToken(), new ApiInterface.VoidDataCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(Void returnParam) {
                dataCallback.onSuccess(returnParam);
            }
        });
    }

    @Override
    public void showProfileOtherUser(int id, final UserCallback dataCallback) {
        if(id !=0) {
            apiDataSource.showProfile("f4493ed183abba6b096f3903a5fc3b64", id,
                    new ApiInterface.UserDataCallback() {

                        @Override
                        public void onError(ErrorBundle errorBundle) {
                            dataCallback.onError(errorBundle);
                        }

                        @Override
                        public void onSuccess(ApiDTO<List<User>> returnParam) {
                            dataCallback.onSuccess(returnParam.getContent().get(0));
                        }
                    });
        }
    }

    @Override
    public void getImagesOtherUser(int id, String image, final ImagesCallback dataCallback) {
        apiDataSource.getImages(image, new ApiInterface.ImagesDataCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(ApiDTO<String> returnParam) {
                if(!returnParam.getError()) dataCallback.onSuccess(returnParam.getContent());
            }
        });
    }


    @Override
    public void getUserChats(final ChatListCallback dataCallback) {
        apiDataSource.getUserChats(internalStorage.getToken(), internalStorage.getUser().getId(), new ApiInterface.ChatListDataCallback(){
            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(ApiDTO<List<ChatDTO>> returnParam/*funcio per cambiar de category dto a list strings*/) {
                List<ChatInfo> chats = new ArrayList<>();
                    for (ChatDTO element : returnParam.getContent()) {
                        ChatInfo chati = new ChatInfo();
                        chati.setId(Integer.toString(element.getProduct_id1()) + "_" + Integer.toString(element.getProduct_id2()) + "_" + Integer.toString(element.getId()));
                        chati.setMy_product(element.getMy_product());
                        chati.setTitle(element.getTitle());
                        if (element.getMy_product() == element.getProduct_id1())  {
                            chati.setProduct_id1(element.getProduct_id1());
                            chati.setProduct_id2(element.getProduct_id2());
                        }
                        else {

                            chati.setProduct_id1(element.getProduct_id2());
                            chati.setProduct_id2(element.getProduct_id1());
                        }
                        chats.add(chati);
                    }

                    dataCallback.onSuccess(chats);

            }
        });
    }

    @Override
    public void getChatMessages(String chatId, final ChatMessagesCallback dataCallback) {
        firebaseDataSource.getChatMessages(chatId, new FirebaseChatListener() {
            @Override
            public void onNewMessage(ChatMessage messages) {
                dataCallback.onSuccess(messages);
            }

            @Override
            public void onError(ErrorBundle bundle) {
                dataCallback.onError(bundle);
            }
        });
    }

    @Override
    public void setMessageAsRead(String chatId, String key) {
        firebaseDataSource.setMessageAsRead(chatId, key);
    }

    @Override
    public void sendChatMessage(ChatMessage message, final VoidCallback dataCallback) {
        firebaseDataSource.newMessage(message.getChatId(), message, new FirebaseInterface.FirebaseVoidCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(Void returnParam) {
                dataCallback.onSuccess(null);
            }
        });
    }

    @Override
    public void setTruekeStatus(TruekeData td, final VoidCallback dataCallback){
        firebaseDataSource.setTruekeStatus(td.getStatus(),td.getChatID(),td.getTruekeID(), new FirebaseInterface.FirebaseVoidCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(Void returnParam) {
                dataCallback.onSuccess(null);
            }
        });
    }

    @Override
    public void createTrueke(String chatID, final VoidCallback dataCallback) {
        apiDataSource.createTrueke(chatID,"f4493ed183abba6b096f3903a5fc3b64", new ApiInterface.VoidDataCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                    dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(Void returnParam) {
                    dataCallback.onSuccess(null);
            }
        });
    }

    @Override
    public void payTrueke(TruekePaymentData truekedata, final VoidCallback dataCallback) {
        apiDataSource.payTrueke(truekedata.getProduct_id(),truekedata.getChat_id(),truekedata.getPayment_id(),internalStorage.getToken(), new ApiInterface.VoidDataCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(Void returnParam) {
                dataCallback.onSuccess(null);
            }
        });
    }

    @Override
    public void getProductInfo(int prodID, final ProductCallback dataCallback) {
        apiDataSource.getProductInfo(prodID, new ApiInterface.ProductDataCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(ApiDTO<ProductDTO> returnParam) {
                Product p = ProductDTO.changeType(returnParam.getContent());
                dataCallback.onSuccess(p);
            }
        });
    }



}
