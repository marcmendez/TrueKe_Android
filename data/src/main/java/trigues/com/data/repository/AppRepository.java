package trigues.com.data.repository;

import com.trigues.RepositoryInterface;
import com.trigues.entity.Payment;
import com.trigues.entity.Product;
import com.trigues.entity.Shipment;
import com.trigues.entity.User;
import com.trigues.exception.ErrorBundle;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import trigues.com.data.datasource.ApiInterface;
import trigues.com.data.datasource.InternalStorageInterface;
import trigues.com.data.entity.ApiDTO;
import trigues.com.data.entity.CategoryDTO;
import trigues.com.data.entity.LoginDTO;
import trigues.com.data.entity.ProductDTO;

/**
 * Created by mbaque on 15/03/2017.
 */

public class AppRepository implements RepositoryInterface {

    private ApiInterface apiDataSource;
    private InternalStorageInterface internalStorage;

    @Inject
    public AppRepository(ApiInterface apiDataSource, InternalStorageInterface internalStorage) {
        this.apiDataSource = apiDataSource;
        this.internalStorage = internalStorage;
    }

    @Override
    public void getUserProductDetails(int productId, final ProductCallback dataCallback) {
        apiDataSource.getUserProductDetails(internalStorage.getToken(), internalStorage.getUser().getId(), new ApiInterface.ProductDataCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(Product returnParam) {
                dataCallback.onSuccess(returnParam);
            }
        });
    }

    @Override
    public void showProducts(int userID, final ProductListCallback dataCallback) {
        apiDataSource.showProducts(internalStorage.getToken(), internalStorage.getUser().getId(), new ApiInterface.ProductListDataCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(ApiDTO<List<Product>> returnParam) {
                List<Product> p = new ArrayList<>();
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



    public void acceptMatch(Integer[] productsID, final VoidCallback dataCallback) {
        //Aun no se sabe nombre de la query, inventarme algo;
        // POST /matches al header tinc un token, y al body tinc el product_id1 i product_id2 i un wants (0 o 1 si accepta)
        //
        //(productsID[0], productsID[1], 1) //este bool 0 rechaza, 1 acepta

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
    public void rejectMatch(Integer[] productsID, VoidCallback dataCallback) {

    }

    @Override
    public void getMatchmakingProducts(int prodID, final ProductListCallback dataCallback) {

        apiDataSource.getMatchmakingProducts(internalStorage.getToken(), internalStorage.getUser().getId(), new ApiInterface.ProductListDataCallback() {
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
        apiDataSource.addProduct(internalStorage.getToken(), p2, new ApiInterface.BooleanDataCallback() {
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
}
