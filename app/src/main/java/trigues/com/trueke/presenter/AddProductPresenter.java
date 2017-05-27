package trigues.com.trueke.presenter;

import android.util.Log;

import com.trigues.entity.Product;
import com.trigues.usecase.AddCategoryToProductUseCase;
import com.trigues.usecase.AddImagesProductUseCase;
import com.trigues.usecase.AddImagesUseCase;
import com.trigues.usecase.AddProductUseCase;
import com.trigues.exception.ErrorBundle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import trigues.com.data.datasource.InternalStorageInterface;
import trigues.com.data.entity.ProductDTO;
import trigues.com.trueke.view.AddProductActivity;

/**
 * Created by Alba on 22/03/2017.
 */

public class AddProductPresenter {
    private AddProductActivity view;
    private AddProductUseCase addProductUseCase;
    private InternalStorageInterface internalStorage;
    private AddImagesUseCase addImagesUseCase;
    private AddImagesProductUseCase addImagesProductUseCase;
    private List<String> images_base64;
    private int count_images;
    private int count_i;

    @Inject
    public AddProductPresenter(AddProductActivity view,AddProductUseCase addProductUseCase, InternalStorageInterface internalStorage,
                               AddImagesUseCase addImagesUseCase, AddImagesProductUseCase addImagesProductUseCase) {
        this.view = view;
        this.addProductUseCase = addProductUseCase;
        this.internalStorage = internalStorage;
        this.addImagesUseCase = addImagesUseCase;
        this.addImagesProductUseCase = addImagesProductUseCase;
    }

    public void addProduct(String title, String description, final List<String> images, String productCategory, final List<String> desiredCategories, int priceMin, int priceMax) {

        //pasar usuario y categorias que quiere
        int userId = Integer.valueOf(internalStorage.getUser().getId());
        Product product = new Product(-1, userId, title, description, images, productCategory, desiredCategories, priceMin, priceMax);
        view.showProgress("Creando producto...");
        addProductUseCase.execute(product, new AddProductUseCase.AddProductCallback(){
            @Override
            public void onError(ErrorBundle errorBundle) {
                view.hideProgress();
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(Boolean returnParam) {
                if(returnParam){
                    images_base64 = images;
                    count_images = images.size();
                    count_i = 0;
                    if(count_images != 0) addImagesCallback();
                    else {
                        view.hideProgress();
                        view.goToShowProductList();
                    }
                }
                else{
                    view.onError("No se ha añadido el producto correctamente");
                }
            }
        });
    }

    public void addImagesCallback() {
        if(images_base64.get(count_i) != "") addImage(images_base64.get(count_i));
        else {
            count_i++;
            if(count_i < count_images) addImagesCallback();
            else {
                view.hideProgress();
                view.goToShowProductList();
            }
        }
    }

    public void addImage(String image) {
        //para cada imagen
        addImagesUseCase.execute(image, new AddImagesUseCase.AddImagesCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(String returnParam) {
                //Log.i("image_md5", "returnParam: " + returnParam);
                //Log.i("image_md5", "simplificado: " + returnParam.substring(8));
                //returnParam: "/image/aeosnfaoei"
                //substring returnPAram: "aeosnfaoei"
                addImagesProduct(returnParam.substring(8));
            }
        });
    }

    public void addImagesProduct(String image) {
        addImagesProductUseCase.execute(image, new AddImagesProductUseCase.AddImagesProductCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(Boolean returnParam) {
                count_i++;
                if(count_i < count_images) addImagesCallback();
                else {
                    view.hideProgress();
                    view.goToShowProductList();
                }
            }
        });
    }
}