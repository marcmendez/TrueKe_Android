package trigues.com.data.repository;

import com.trigues.RepositoryInterface;
import com.trigues.entity.Product;
import com.trigues.exception.ErrorBundle;
import com.trigues.usecase.ShowProductsUseCase;

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

    @Override
    public void showProducts(int userID, final showProductsCallback dataCallback) {
        apiDataSource.showProducts(userID, new ApiInterface.showProducts() {
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
}
