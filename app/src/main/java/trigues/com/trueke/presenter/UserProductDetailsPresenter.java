package trigues.com.trueke.presenter;

import android.util.Log;

import com.trigues.entity.Product;
import com.trigues.exception.ErrorBundle;
import com.trigues.usecase.AddCategoryToProductUseCase;
import com.trigues.usecase.DeleteCategoryToProductUseCase;
import com.trigues.usecase.DeleteProductUseCase;
import com.trigues.usecase.GetDesiredCategoriesUseCase;
import com.trigues.usecase.GetImagesUseCase;
import com.trigues.usecase.GetUserProductDetailsUseCase;
import com.trigues.usecase.GetImagesProductUseCase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import trigues.com.trueke.view.UserProductDetailsActivity;

/**
 * Created by mbaque on 24/03/2017.
 */

public class UserProductDetailsPresenter {

    private UserProductDetailsActivity view;
    private GetUserProductDetailsUseCase getUserProductDetailsUseCase;
    private DeleteProductUseCase deleteProduct;
    private AddCategoryToProductUseCase addCategoryUseCase;
    private DeleteCategoryToProductUseCase deleteCategoryUseCase;
    private GetDesiredCategoriesUseCase getDesiredCategoriesUseCase;
    private GetImagesProductUseCase getImagesProductUseCase;
    private GetImagesUseCase getImagesUseCase;
    private List<String> images_base64;
    private int count_images;

    @Inject
    public UserProductDetailsPresenter(UserProductDetailsActivity view,
                                       GetUserProductDetailsUseCase getUserProductDetailsUseCase,
                                       DeleteProductUseCase deleteProduct,
                                       AddCategoryToProductUseCase addProductCategory,
                                       DeleteCategoryToProductUseCase deleteProductCategory,
                                       GetDesiredCategoriesUseCase desiredCategories,
                                       GetImagesProductUseCase getImagesProductUseCase,
                                       GetImagesUseCase getImagesUseCase) {
        this.view = view;
        this.getUserProductDetailsUseCase = getUserProductDetailsUseCase;
        this.deleteProduct = deleteProduct;
        this.addCategoryUseCase = addProductCategory;
        this.deleteCategoryUseCase = deleteProductCategory;
        this.getDesiredCategoriesUseCase = desiredCategories;
        this.getImagesProductUseCase = getImagesProductUseCase;
        this.getImagesUseCase = getImagesUseCase;
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

    public void getDesiredCategories(int productId) {
        if(productId != -1){
            getDesiredCategoriesUseCase.execute(productId, new GetDesiredCategoriesUseCase.GetDesiredCategoriesCallback() {
                @Override
                public void onError(ErrorBundle errorBundle) {
                    view.onError(errorBundle.getErrorMessage());
                }

                @Override
                public void onSuccess(List<String> returnParam) {
                    view.setUpDesiredCategoriesList(returnParam);
                }
            });
        }
        else{
            view.onError("Producto no válido");
        }
    }

    public void onCategoryDeleteButtonClick(String category, final int productID) {


        List<String> list = new ArrayList<>();
        list.add(category);
        list.add(String.valueOf(productID));
        deleteCategoryUseCase.execute(list, new DeleteCategoryToProductUseCase.BooleanCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(Boolean returnParam) {
               getDesiredCategories(productID);
            }
        });
    }

    public void addProductCategory(String category, final int productID){
        List<String> list = new ArrayList<>();
        list.add(category);
        list.add(String.valueOf(productID));
        addCategoryUseCase.execute(list, new AddCategoryToProductUseCase.BooleanCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(Boolean returnParam) {
                getDesiredCategories(productID);
            }
        });
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

    public void getImagesProduct(int prod_id) {
        getImagesProductUseCase.execute(prod_id, new GetImagesProductUseCase.GetImagesProductCallback(){
            @Override
            public void onError(ErrorBundle errorBundle) {
                view.hideProgress();
                view.onError(errorBundle.getErrorMessage());
            }
            @Override
            public void onSuccess(List<String> returnParam) {
                count_images = returnParam.size();
                images_base64 = new ArrayList();
                for(String ret: returnParam) {
                    Log.i("images presenter", "images ret: "+ret);
                    getImage(ret);
                }
            }
        }) ;
    }

    public void getImage(String ret) {
        getImagesUseCase.execute(ret.substring(8), new GetImagesUseCase.GetImagesCallback(){
            @Override
            public void onError(ErrorBundle errorBundle) {
                view.hideProgress();
                view.onError(errorBundle.getErrorMessage());
            }
            @Override
            public void onSuccess(String returnParam) {
                finalList(returnParam);
            }
        });
    }

    public void finalList(String image) {
        images_base64.add(image);
        if (images_base64.size() == count_images) view.setUpViewPager(images_base64);
    }
}
