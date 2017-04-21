package trigues.com.trueke.presenter;

import com.trigues.entity.Product;
import com.trigues.exception.ErrorBundle;
import com.trigues.usecase.AcceptMatchUseCase;
import com.trigues.usecase.GetUserProductsUseCase;

import java.util.List;

import javax.inject.Inject;

import trigues.com.trueke.view.MatchmakingActivity;

/**
 * Created by mbaque on 07/04/2017.
 */

public class MatchmakingPresenter {

    private MatchmakingActivity view;
    private GetUserProductsUseCase showProductsUseCase;
    private AcceptMatchUseCase acceptMatchUseCase;

    @Inject
    public MatchmakingPresenter(MatchmakingActivity view, GetUserProductsUseCase showProductsUseCase) {
        this.view = view;
        this.showProductsUseCase = showProductsUseCase;
    }

    public void getTestProducts(){
        showProductsUseCase.execute(54321, new GetUserProductsUseCase.UserProductsListCallback() {
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

    public void acceptedProduct(String usuari, String password) {
        EncaraNoSeQuinUseCaseEs.execute(new loquesea, new EncaraNoSeQuinUseCaseEs.EncaraNoSeQuinUseCaseEsCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(Boolean returnParam) {
                if(returnParam){
                    //view.goToShowProductList();


                }
                else{
                    //view.onError("Login incorrecto");


                }
            }
        });
    }

    public void refusedProduct() {

    }





}
