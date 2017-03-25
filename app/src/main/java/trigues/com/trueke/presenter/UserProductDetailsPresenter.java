package trigues.com.trueke.presenter;

import com.trigues.entity.Product;
import com.trigues.exception.ErrorBundle;
import com.trigues.usecase.GetUserProductDetailsUseCase;

import javax.inject.Inject;

import trigues.com.trueke.view.UserProductDetailsActivity;

/**
 * Created by mbaque on 24/03/2017.
 */

public class UserProductDetailsPresenter {

    private UserProductDetailsActivity view;
    private GetUserProductDetailsUseCase getUserProductDetailsUseCase;

    @Inject
    public UserProductDetailsPresenter(UserProductDetailsActivity view,
                                       GetUserProductDetailsUseCase getUserProductDetailsUseCase) {
        this.view = view;
        this.getUserProductDetailsUseCase = getUserProductDetailsUseCase;
    }

    public void getProductDetails(int productId) {
        if(productId == -1){
            getUserProductDetailsUseCase.execute(productId, new GetUserProductDetailsUseCase.GetUserProductDetailsCallback() {
                @Override
                public void onError(ErrorBundle errorBundle) {
                    view.onError(errorBundle.getErrorMessage());
                }

                @Override
                public void onSuccess(Product returnParam) {
                    view.onDetailsRetrieved(returnParam);
                }
            });
        }
        else{
            view.onError("Producto no válido");
        }
    }

    public void onCategoryDeleteButtonClick(String category) {

    }
}
