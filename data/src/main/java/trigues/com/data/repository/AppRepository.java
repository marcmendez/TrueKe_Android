package trigues.com.data.repository;

import com.trigues.RepositoryInterface;
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
    public void register(User param, final LoginUseCaseCallback dataCallback) {
        apiDataSource.register(param, new ApiInterface.Register(){

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
