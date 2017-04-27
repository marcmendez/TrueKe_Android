package trigues.com.trueke.presenter;

import android.content.Intent;

import com.trigues.entity.Product;
import com.trigues.exception.ErrorBundle;
import com.trigues.usecase.DeleteProductUseCase;
import com.trigues.usecase.GetUserProductDetailsUseCase;

import javax.inject.Inject;

import trigues.com.trueke.view.UserProductDetailsActivity;
import trigues.com.trueke.view.impl.UserProductsListActivityImpl;

/**
 * Created by mbaque on 24/03/2017.
 */

public class UserProductDetailsPresenter {

    private UserProductDetailsActivity view;
    private GetUserProductDetailsUseCase getUserProductDetailsUseCase;
    private DeleteProductUseCase deleteProduct;

    @Inject
    public UserProductDetailsPresenter(UserProductDetailsActivity view,
                                       GetUserProductDetailsUseCase getUserProductDetailsUseCase,
                                       DeleteProductUseCase deleteProduct) {
        this.view = view;
        this.getUserProductDetailsUseCase = getUserProductDetailsUseCase;
        this.deleteProduct = deleteProduct;
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

    public void addProductCategory(String category){

    }

    public void deleteProduct(int prod_id) {
        deleteProduct.execute(prod_id, new DeleteProductUseCase.DeleteProductUseCaseCallback(){
            @Override
            public void onError(ErrorBundle errorBundle) {
                view.hideProgress();
                view.onError(errorBundle.getErrorMessage());
            }
            @Override
            public void onSuccess(Boolean returnParam) {
                view.hideProgress();
                if(!returnParam){
                    view.goToShowProductList();
                }
                else{
                    view.onError("No se ha eliminado el producto correctamente");
                }
            }
        });
    }

}
