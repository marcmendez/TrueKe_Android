package trigues.com.trueke.presenter;

import android.util.Log;

import com.trigues.entity.Product;
import com.trigues.exception.ErrorBundle;
import com.trigues.usecase.GetImagesProductUseCase;
import com.trigues.usecase.GetImagesUseCase;
import com.trigues.usecase.GetUserProductsUseCase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import trigues.com.trueke.view.UserProductsListActivity;

/**
 * Created by Marc on 22/03/2017.
 */

public class ShowProductsPresenter {

    private UserProductsListActivity view;
    private GetUserProductsUseCase showProductsUseCase;
    private GetImagesUseCase getImagesUseCase;
    private GetImagesProductUseCase getImagesProductUseCase;
    private List<String> images_base64;
    private int count_images;
    private List<Product> products;
    private List<Product> aux_returnParam;
    private int count_products;
    private int size_products;
    private int count_i;

    @Inject
    public ShowProductsPresenter(UserProductsListActivity view,
                                 GetUserProductsUseCase showProductsUseCase,
                                 GetImagesUseCase getImagesUseCase,
                                 GetImagesProductUseCase getImagesProductUseCase) {

        this.view = view;
        this.showProductsUseCase = showProductsUseCase;
        this.getImagesProductUseCase = getImagesProductUseCase;
        this.getImagesUseCase = getImagesUseCase;
    }


    public void getUserProducts() {
        view.showProgress("Cargando productos...");
        showProductsUseCase.execute(null, new GetUserProductsUseCase.UserProductsListCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {
                view.hideProgress();
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(final List<Product> returnParam) {
                products = new ArrayList<>();
                size_products = returnParam.size();
                count_products = 0;
                aux_returnParam = new ArrayList<>();
                aux_returnParam = returnParam;
                count_i = 0;
                if(returnParam.size() != 0) getImagesProductcallback();
                else {
                    view.hideProgress();
                    view.generateProds(returnParam);
                }
            }
        });
    }

   public void getImagesProductcallback() {
        getImagesProduct(aux_returnParam.get(count_i),aux_returnParam.get(count_i).getId());
    }

    public void getImagesProduct(final Product p, int prod_id) {
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
                   // Log.i("images presenter", "images ret: "+ret);
                    //Log.i("images", "gip product: "+p.getId());
                    getImage(p, ret);
                    try { //delay entre llamadas
                        TimeUnit.MILLISECONDS.sleep(5);
                        //TimeUnit.SECONDS.sleep(100);
                    } catch (InterruptedException e) {

                    }
                }
            }
        }) ;
    }

    public void getImage(final Product p, String ret) {
        getImagesUseCase.execute(ret.substring(8), new GetImagesUseCase.GetImagesCallback(){
            @Override
            public void onError(ErrorBundle errorBundle) {
                view.hideProgress();
                view.onError(errorBundle.getErrorMessage());
            }
            @Override
            public void onSuccess(String returnParam) {
                //Log.i("images", "gi product: "+p.getId());
                finalList(p, returnParam);
            }
        });
    }

    public void finalList(Product p, String image) {
        images_base64.add(image);
       // Log.i("images", "MID product: "+p.getId()+" images: "+image);
        if (images_base64.size() == count_images) {
            p.setImages(images_base64);
            products.add(p);
            count_products++;
         //   Log.i("images", "AFT product: "+p.getId()+" images: "+p.getImages());
            if(count_products < size_products) {
                count_i++;
                getImagesProductcallback();
            }
            if(count_products == size_products) {
                view.hideProgress();
                view.generateProds(products);
            }
        }
    }
}

