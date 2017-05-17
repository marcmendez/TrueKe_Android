package trigues.com.trueke.presenter;

import com.trigues.entity.Product;
import com.trigues.exception.ErrorBundle;
import com.trigues.usecase.AcceptMatchUseCase;
import com.trigues.usecase.GetMatchMakingListUseCase;
import com.trigues.usecase.RejectMatchUseCase;
import com.trigues.usecase.ReportProductUseCase;

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
    private ReportProductUseCase reportProductUseCase;

    @Inject
    public MatchmakingPresenter(MatchmakingActivity view, GetMatchMakingListUseCase showProductsUseCase,
                                AcceptMatchUseCase acceptMatchUseCase, RejectMatchUseCase rejectMatchUseCase,
                                ReportProductUseCase reportProductUseCase) {
        this.view = view;
        this.showProductsUseCase = showProductsUseCase;
        this.acceptMatchUseCase = acceptMatchUseCase;
        this.rejectMatchUseCase = rejectMatchUseCase;
        this.reportProductUseCase = reportProductUseCase;

    }

    public void getMatchMakingProducts(int prodID){
        view.showProgress("Cargando productos...");
        showProductsUseCase.execute(prodID, new GetMatchMakingListUseCase.MatchMakingCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {
                view.hideProgress();
                view.onError(errorBundle.getErrorMessage());

            }

            @Override
            public void onSuccess(List<Product> returnParam) {
                view.onProductsRetrieved(returnParam);
                view.hideProgress();
            }
        });
    }

    public void acceptedProduct(Integer[] productID) {
        view.showProgress("Aceptando...");
        acceptMatchUseCase.execute(productID, new AcceptMatchUseCase.AcceptMatchCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {
                view.hideProgress();
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(Void returnParam) {
                view.hideProgress();
            }
        });
    }

    public void rejectedProduct(Integer[] productID) {
        view.showProgress("Rechazando...");
        rejectMatchUseCase.execute(productID, new RejectMatchUseCase.RejectMatchCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {
                view.hideProgress();
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(Void returnParam) {
                view.hideProgress();
            }
        });


    }


    public void report(Integer[] userProdID) {
        view.showProgress("Reportando producto...");
        reportProductUseCase.execute(userProdID, new ReportProductUseCase.ReportProductCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {
                view.hideProgress();
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(Void returnParam) {
                view.hideProgress();
            }
        });
    }
}
