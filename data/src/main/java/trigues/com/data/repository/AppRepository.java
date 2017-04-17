package trigues.com.data.repository;

import android.util.Log;

import com.trigues.RepositoryInterface;
import com.trigues.entity.Payment;
import com.trigues.entity.Product;
import com.trigues.entity.Shipment;
import com.trigues.entity.User;
import com.trigues.exception.ErrorBundle;

import java.util.List;

import javax.inject.Inject;

import trigues.com.data.datasource.ApiInterface;

/**
 * Created by mbaque on 15/03/2017.
 */

public class AppRepository implements RepositoryInterface {

    private ApiInterface apiDataSource;

    @Inject
    public AppRepository(ApiInterface apiDataSource) {
        this.apiDataSource = apiDataSource;
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
                dataCallback.onSuccess(returnParam);
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
    public void showPaymentInfo(Integer id, final PaymentCallback dataCallback) {
        apiDataSource.showPaymentInfo(id,new ApiInterface.PaymentDataCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(Payment returnParam) {
                dataCallback.onSuccess(returnParam);
            }
        });
    }

    @Override
    public void showShipmentInfo(Integer id, final ShipmentCallback dataCallback) {
        apiDataSource.showShipmentInfo(id,new ApiInterface.ShipmentDataCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(Shipment returnParam) {
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
    public void login(User user, final BooleanCallback dataCallback) {
        apiDataSource.login(user, new ApiInterface.BooleanDataCallback() {
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
    public void showProfile(int userID, final UserCallback dataCallback) {
        apiDataSource.showProfile(userID,new ApiInterface.UserDataCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(User returnParam) {
                dataCallback.onSuccess(returnParam);
            }
        });
    }
}
