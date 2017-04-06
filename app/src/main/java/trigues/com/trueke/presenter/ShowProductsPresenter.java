package trigues.com.trueke.presenter;

import com.trigues.entity.Product;
import com.trigues.exception.ErrorBundle;
import com.trigues.usecase.ShowProductsUseCase;

import java.util.List;

import javax.inject.Inject;

import trigues.com.trueke.view.ShowProductsActivity;

/**
 * Created by Marc on 22/03/2017.
 */

public class ShowProductsPresenter {

    private ShowProductsActivity view;
    private ShowProductsUseCase showProductsUseCase;

    @Inject
    public ShowProductsPresenter(ShowProductsActivity view,
                                 ShowProductsUseCase showProductsUseCase) {

        this.view = view;
        this.showProductsUseCase = showProductsUseCase;
    }


    public void getUserProducts(int userID) {
        if (userID == 54321) {

            showProductsUseCase.execute(userID, new ShowProductsUseCase.showProductsCallback() {
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

