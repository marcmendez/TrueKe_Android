package trigues.com.data.repository;

import com.trigues.RepositoryInterface;
import com.trigues.entity.Product;
import com.trigues.entity.User;
import com.trigues.exception.ErrorBundle;

import java.util.List;

import javax.inject.Inject;

import trigues.com.data.datasource.ApiInterface;
import trigues.com.data.datasource.InternalStorageInterface;
import trigues.com.data.entity.ApiDTO;
import trigues.com.data.entity.LoginDTO;

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
    public void login(User user, final BooleanCallback dataCallback) {
        apiDataSource.login(user, new ApiInterface.LoginDataCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {
                dataCallback.onError(errorBundle);
            }

            @Override
            public void onSuccess(ApiDTO<LoginDTO> returnParam) {
                internalStorage.saveToken(returnParam.getContent().getToken());
                dataCallback.onSuccess(!returnParam.getError());

            }
        });
    }

    @Override
    public void addProduct(Product product, final BooleanCallback dataCallback) {
        apiDataSource.addProduct(product, new ApiInterface.BooleanDataCallback() {
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
