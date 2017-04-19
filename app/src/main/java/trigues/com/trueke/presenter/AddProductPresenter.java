package trigues.com.trueke.presenter;

import com.trigues.entity.Product;
import com.trigues.usecase.AddProductUseCase;
import com.trigues.exception.ErrorBundle;
import javax.inject.Inject;

import trigues.com.trueke.view.AddProductActivity;

/**
 * Created by Alba on 22/03/2017.
 */

public class AddProductPresenter {
    private AddProductActivity view;

    @Inject
    public AddProductPresenter(AddProductActivity view) {
        this.view = view;
    }

    public void addProduct(String title, String description,String priceMin, String priceMax, String category, String wants_categories) {

        //pasar usuario y categorias que quiere
        Product product = new Product(user_id,title,description,category,priceMin, priceMax, wants_categories);
        AddProductUseCase.execute(product, new AddProductUseCase.AddProductCallback(){
            @Override
            public void onError(ErrorBundle errorBundle) {
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(Boolean returnParam) {
                if(returnParam){
                    view.goToShowProductList();
                }
                else{
                    view.onError("No se ha añadido el producto correctamente");
                }
            }
        });
    }
}