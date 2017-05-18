package trigues.com.trueke.presenter;

import android.os.Handler;
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


    public void getUserProducts(int userID) {
        if (userID == 0) {

            showProductsUseCase.execute(userID, new GetUserProductsUseCase.UserProductsListCallback() {
                @Override
                public void onError(ErrorBundle errorBundle) {
                    view.onError(errorBundle.getErrorMessage());
                }

                @Override
                public void onSuccess(final List<Product> returnParam) {
                   /* final int[] i = {0};
                    Handler handler1 = new Handler();
                    for(final Product p: returnParam) {
                        handler1.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getImagesProduct(p, p.getId());*/
                        /*try { //delay entre llamadas
                            TimeUnit.MILLISECONDS.sleep(1);
                        } catch (InterruptedException e) {

                        }*/
                    /*            returnParam.get(i[0]).setProduct(p);
                                i[0]++;
                            }
                        },100* i[0]);
                    }*/
                    //while(returnParam.size() > i){}
                    view.generateProds(returnParam);
                }
            });
        }
        else{
            view.onError("Producto no válido");
        }
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
                    Log.i("images presenter", "images ret: "+ret);
                    getImage(p, ret);
                    try { //delay entre llamadas
                        TimeUnit.MILLISECONDS.sleep(1);
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
                finalList(p, returnParam);
            }
        });
    }

    public void finalList(Product p, String image) {
        images_base64.add(image);
        if (images_base64.size() == count_images) p.setImages(images_base64);
    }
}

