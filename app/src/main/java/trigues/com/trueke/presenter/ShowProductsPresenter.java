package trigues.com.trueke.presenter;

import com.trigues.entity.Product;
import com.trigues.exception.ErrorBundle;
import com.trigues.usecase.GetUserProductsUseCase;

import java.util.List;

import javax.inject.Inject;

import trigues.com.trueke.view.UserProductsListActivity;

/**
 * Created by Marc on 22/03/2017.
 */

public class ShowProductsPresenter {

    private UserProductsListActivity view;
    private GetUserProductsUseCase showProductsUseCase;

    @Inject
    public ShowProductsPresenter(UserProductsListActivity view,
                                 GetUserProductsUseCase showProductsUseCase) {

        this.view = view;
        this.showProductsUseCase = showProductsUseCase;
    }


    public void getUserProducts(int userID) {
        if (userID == 54321) {

            showProductsUseCase.execute(userID, new GetUserProductsUseCase.UserProductsListCallback() {
                @Override
                public void onError(ErrorBundle errorBundle) {
                    view.onError(errorBundle.getErrorMessage());
                }

                @Override
                public void onSuccess(List<Product> returnParam) { view.generateProds(returnParam);
                }
            });
        }
        else{
            view.onError("Producto no válido");
        }



    }
}

