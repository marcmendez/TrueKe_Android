package trigues.com.data.repository;

import com.trigues.RepositoryInterface;
import com.trigues.entity.Product;
import com.trigues.exception.ErrorBundle;

import javax.inject.Inject;

import trigues.com.data.datasource.ApiInterface;

/**
 * Created by mbaque on 15/03/2017.
 */

public class AppRepository implements RepositoryInterface {

    @Inject
    private ApiInterface apiDataSource;

    public AppRepository() {
    }

    @Override
    public void getUserProductDetails(int productId, final GetUserProductDetailsCallback dataCallback) {
        apiDataSource.getUserProductDetails(productId, new ApiInterface.GetUserProductDataDetails() {
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
}
