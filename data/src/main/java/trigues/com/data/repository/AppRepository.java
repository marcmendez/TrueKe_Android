package trigues.com.data.repository;

import android.util.Log;

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
        apiDataSource.getUserProductDetails(productId, new ApiInterface.ProductDataCallback() {
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
        apiDataSource.showProducts(userID, new ApiInterface.ProductListDataCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(List<Product> returnParam) {
                List<Product> p = new ArrayList<>();
                dataCallback.onSuccess(p);
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
        apiDataSource.showPayments(id,new ApiInterface.PaymentsCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(List<Payment> returnParam) {
                dataCallback.onSuccess(returnParam);
            }
        });
    }

    @Override
    public void showShipments(Integer id, final ShipmentCallback dataCallback) {
        apiDataSource.showShipments(id,new ApiInterface.ShipmentsCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(List<Shipment> returnParam) {
                dataCallback.onSuccess(returnParam);
            }
        });
    }

    @Override
    public void changeProfile(User user, final BooleanCallback dataCallback) {
        apiDataSource.changeProfile(user,new ApiInterface.BooleanDataCallback(){

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
        apiDataSource.newPayment(payment,new ApiInterface.BooleanDataCallback(){

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
        apiDataSource.newShipment(shipment,new ApiInterface.BooleanDataCallback(){

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
        apiDataSource.changeShipment(shipment,new ApiInterface.BooleanDataCallback(){

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
               internalStorage.saveUser(returnParam.getContent().getUser());
               internalStorage.saveToken(returnParam.getContent().getToken());
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
        apiDataSource.showProfile(internalStorage.getToken(), String.valueOf(internalStorage.getUser().getId()),
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
}
