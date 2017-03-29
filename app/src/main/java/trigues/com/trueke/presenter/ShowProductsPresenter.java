package trigues.com.trueke.presenter;

import com.trigues.entity.Product;
import com.trigues.entity.User;
import com.trigues.exception.ErrorBundle;
import com.trigues.usecase.GetUserProductDetailsUseCase;
import com.trigues.usecase.ShowProductsUseCase;

import javax.inject.Inject;

import trigues.com.trueke.adapter.ShowProductsAdapter;
import trigues.com.trueke.view.ShowProductsActivity;
import trigues.com.trueke.view.UserProductDetailsActivity;

/**
 * Created by Marc on 22/03/2017.
 */

public class ShowProductsPresenter {

    private ShowProductsAdapter view;
    private ShowProductsUseCase showProductsUseCase;


    public ShowProductsPresenter(ShowProductsAdapter view,
                                 ShowProductsUseCase showProductsUseCase) {

        this.view = view;
        this.showProductsUseCase = showProductsUseCase;
    }


    public void getUserProducts(int userID) {
        if (userID == -1) {

            showProductsUseCase.execute(userID, new ShowProductsUseCase.showProductsCallback() {
                @Override
                public void onError(ErrorBundle errorBundle) {
                    view.onError(errorBundle.getErrorMessage());
                }

                @Override
                public void onSuccess(Product returnParam) { view.generateProd(returnParam);
                }
            });
        }
        else{
            view.onError("Producto no válido");
        }



    }
}

