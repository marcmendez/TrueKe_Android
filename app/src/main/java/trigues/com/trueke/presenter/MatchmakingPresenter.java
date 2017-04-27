package trigues.com.trueke.presenter;

import android.support.v4.util.Pair;

import com.trigues.entity.Product;
import com.trigues.exception.ErrorBundle;
import com.trigues.usecase.AcceptMatchUseCase;
import com.trigues.usecase.GetMatchMakingListUseCase;
import com.trigues.usecase.GetUserProductsUseCase;
import com.trigues.usecase.RejectMatchUseCase;

import java.util.List;

import javax.inject.Inject;

import trigues.com.trueke.view.MatchmakingActivity;

/**
 * Created by mbaque on 07/04/2017.
 */

public class MatchmakingPresenter {

    private MatchmakingActivity view;
    private GetMatchMakingListUseCase showProductsUseCase;
    private AcceptMatchUseCase acceptMatchUseCase;
    private RejectMatchUseCase rejectMatchUseCase;

    @Inject
    public MatchmakingPresenter(MatchmakingActivity view, GetMatchMakingListUseCase showProductsUseCase) {
        this.view = view;
        this.showProductsUseCase = showProductsUseCase;
    }

    public void getMatchMakingProducts(int prodID){
        showProductsUseCase.execute(prodID, new GetMatchMakingListUseCase.MatchMakingCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {
                view.onError(errorBundle.getErrorMessage());

            }

            @Override
            public void onSuccess(List<Product> returnParam) {
                view.onProductsRetrieved(returnParam);
            }
        });
    }

    public void acceptedProduct(Integer[] productID) {
        acceptMatchUseCase.execute(productID, new AcceptMatchUseCase.AcceptMatchCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(Void returnParam) {
            }
        });
    }

    public void rejectedProduct(Integer[] productID) {
        rejectMatchUseCase.execute(productID, new RejectMatchUseCase.RejectMatchCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(Void returnParam) {
            }
        });


    }





}
