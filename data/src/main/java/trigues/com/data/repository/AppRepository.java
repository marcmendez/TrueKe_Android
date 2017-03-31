package trigues.com.data.repository;

import com.trigues.RepositoryInterface;
import com.trigues.entity.Product;
import com.trigues.entity.User;
import com.trigues.exception.ErrorBundle;

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
    public void register(User param, final BooleanCallback dataCallback) {
        apiDataSource.register(param, new ApiInterface.BooleanDataCallback(){

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
    public void deleteDesiredCategory(Product product, VoidCallback callback) {

    }

    @Override
    public void getUserProductDetails(int productId, final GetUserProductDetailsCallback callback) {
        apiDataSource.getUserProductDetails(productId, new ApiInterface.ProductDataCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                callback.onError(errorBundle);
            }

            @Override
            public void onSuccess(Product returnParam) {
                callback.onSuccess(returnParam);
            }
        });
    }
}

