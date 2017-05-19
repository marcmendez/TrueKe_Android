package trigues.com.trueke.presenter;

import com.trigues.entity.Product;
import com.trigues.exception.ErrorBundle;
import com.trigues.usecase.GetProductUseCase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import trigues.com.trueke.view.ChatListActivity;

/**
 * Created by marc on 19/05/17.
 */

public class ProductPresenter {


    private ChatListActivity view;
    private GetProductUseCase getProductUseCase;


    @Inject
    public ProductPresenter(ChatListActivity view,
                                       GetProductUseCase getProductUseCase) {
        this.view = view;
        this.getProductUseCase = getProductUseCase;

    }

    public void getProduct(int productId) {

            getProductUseCase.execute(productId, new GetProductUseCase.GetProductCallback() {
                @Override
                public void onError(ErrorBundle errorBundle) {
                    view.hideProgress();
                    view.onError(errorBundle.getErrorMessage());
                }

                @Override
                public void onSuccess(Product returnParam) {
                    view.productTitle(returnParam);

                }
            });
        }

    }



