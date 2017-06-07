package trigues.com.trueke.presenter;

import android.util.Log;
import android.widget.Toast;

import com.trigues.entity.ImageOtherUserType;
import com.trigues.entity.Product;
import com.trigues.entity.User;
import com.trigues.exception.ErrorBundle;
import com.trigues.usecase.AcceptMatchUseCase;
import com.trigues.usecase.GetImagesOtherUserUseCase;
import com.trigues.usecase.GetImagesProductUseCase;
import com.trigues.usecase.GetImagesUseCase;
import com.trigues.usecase.GetMatchMakingListUseCase;
import com.trigues.usecase.RejectMatchUseCase;
import com.trigues.usecase.ReportProductUseCase;
import com.trigues.usecase.ShowProfileOtherUserUseCase;
import com.trigues.usecase.ShowProfileUseCase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    private GetImagesUseCase getImagesUseCase;
    private GetImagesProductUseCase getImagesProductUseCase;
    private GetImagesOtherUserUseCase showProfileImageUseCase;
    private ShowProfileOtherUserUseCase showProfileUseCase;
    private List<String> images_base64;
    private int count_images;
    private List<Product> products;
    private List<Product> aux_returnParam;
    private int count_products;
    private int size_products;
    private int count_i;

    @Inject
    public MatchmakingPresenter(MatchmakingActivity view, GetMatchMakingListUseCase showProductsUseCase,
                                AcceptMatchUseCase acceptMatchUseCase, RejectMatchUseCase rejectMatchUseCase,
                                ShowProfileOtherUserUseCase showProfileUseCase,
                                ReportProductUseCase reportProductUseCase,
                                GetImagesUseCase getImagesUseCase,
                                GetImagesProductUseCase getImagesProductUseCase,
                                GetImagesOtherUserUseCase showProfileImageUseCase) {
        this.view = view;
        this.showProfileUseCase = showProfileUseCase;
        this.showProductsUseCase = showProductsUseCase;
        this.acceptMatchUseCase = acceptMatchUseCase;
        this.rejectMatchUseCase = rejectMatchUseCase;
        this.reportProductUseCase = reportProductUseCase;
        this.getImagesProductUseCase = getImagesProductUseCase;
        this.getImagesUseCase = getImagesUseCase;
        this.showProfileImageUseCase = showProfileImageUseCase;
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
                //view.onProductsRetrieved(returnParam);
                //view.hideProgress();
                Log.i("images", "matchmaking: " + returnParam.size());
                products = new ArrayList<>();
                size_products = returnParam.size();
                count_products = 0;
                aux_returnParam = new ArrayList<>();
                aux_returnParam = returnParam;
                count_i = 0;
                if(returnParam.size() != 0){
                    getImagesProductcallback();
                }
                else {
                    view.hideProgress();
                    //view.onProductsRetrieved(returnParam);
                    view.noProducts();
                }
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
                view.onProductsRetrieved(products);
            }
        }
    }


    public void getInfo(final Integer userid) {
        view.showProgress("Cargando Inofrmacion...");
        showProfileUseCase.execute(userid, new ShowProfileOtherUserUseCase.ShowProfileOtherUserUseCaseCallback() {

            @Override
            public void onError(ErrorBundle errorBundle) {
                view.hideProgress();
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(User returnParam) {
                view.setInfo(userid, returnParam);
                view.hideProgress();
            }
        });
    }
    public void getProfileImage(Integer id, String returnParam) {
        ImageOtherUserType imageOtherUserType = new ImageOtherUserType();
        imageOtherUserType.setUserid(id);
        imageOtherUserType.setImagePath(returnParam);
        showProfileImageUseCase.execute(imageOtherUserType, new GetImagesOtherUserUseCase.GetImagesOtherUserCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(String returnParam) {
                view.OnProfileImage(returnParam);
            }
        });
    }
}
