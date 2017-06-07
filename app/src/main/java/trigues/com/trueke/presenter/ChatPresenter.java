package trigues.com.trueke.presenter;

import com.trigues.entity.ChatInfo;
import com.trigues.entity.ChatMessage;
import com.trigues.entity.Product;
import com.trigues.entity.TruekeData;
import com.trigues.entity.TruekePaymentData;
import com.trigues.entity.VoteData;
import com.trigues.exception.ErrorBundle;
import com.trigues.usecase.CreateTruekeUseCase;
import com.trigues.usecase.DeleteChatUseCase;
import com.trigues.usecase.GetChatMessagesUseCase;
import com.trigues.usecase.GetChatsUseCase;
import com.trigues.usecase.GetImagesProductUseCase;
import com.trigues.usecase.GetImagesUseCase;
import com.trigues.usecase.GetProductUseCase;
import com.trigues.usecase.PayTruekeUseCase;
import com.trigues.usecase.SendChatMessageUseCase;
import com.trigues.usecase.SetTruekeStatusUseCase;
import com.trigues.usecase.VoteUseCase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import trigues.com.trueke.view.ChatListActivity;

/**
 * Created by mbaque on 17/05/2017.
 */

public class ChatPresenter {

    ChatListActivity view;
    private GetChatMessagesUseCase getChatMessagesUseCase;
    private SendChatMessageUseCase sendChatMessageUseCase;
    private GetChatsUseCase getChatsUseCase;
    private SetTruekeStatusUseCase setTruekeStatusUseCase;
    private CreateTruekeUseCase createTruekeUseCase;
    private PayTruekeUseCase payTruekeUseCase;
    private GetProductUseCase getProductUseCase;
    private VoteUseCase voteUseCase;
    private DeleteChatUseCase deleteChatUseCase;
    private GetImagesUseCase getImagesUseCase;
    private GetImagesProductUseCase getImagesProductUseCase;
    private int count_images;
    private List<Product> products;
    private List<String> images_base64;
    private List<Product> aux_returnParam;
    private int count_products;
    private int size_products;
    private int count_i;

    @Inject
    public ChatPresenter(ChatListActivity view, GetChatMessagesUseCase getChatMessagesUseCase,
                         SendChatMessageUseCase sendChatMessageUseCase,
                         SetTruekeStatusUseCase setTruekeStatusUseCase,
                         CreateTruekeUseCase createTruekeUseCase,
                         GetChatsUseCase getChatsUseCase,
                         GetProductUseCase getProductUseCase,
                         PayTruekeUseCase payTruekeUseCase, VoteUseCase voteUseCase,
                         DeleteChatUseCase deleteChatUseCase,
                         GetImagesUseCase getImagesUseCase,
                         GetImagesProductUseCase getImagesProductUseCase) {
        this.view = view;
        this.getChatMessagesUseCase = getChatMessagesUseCase;
        this.sendChatMessageUseCase = sendChatMessageUseCase;
        this.setTruekeStatusUseCase = setTruekeStatusUseCase;
        this.createTruekeUseCase = createTruekeUseCase;
        this.getChatsUseCase = getChatsUseCase;
        this.getProductUseCase = getProductUseCase;
        this.payTruekeUseCase = payTruekeUseCase;
        this.voteUseCase = voteUseCase;
        this.deleteChatUseCase = deleteChatUseCase;
        this.getImagesUseCase =getImagesUseCase;
        this.getImagesProductUseCase = getImagesProductUseCase;
    }

    public void getChats() {

        getChatsUseCase.execute(0, new GetChatsUseCase.GetChatsListCallback(){
            @Override
            public void onError(ErrorBundle errorBundle) {
                view.hideProgress();
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(List<ChatInfo> returnParam) {
                if(returnParam.size() == 0) view.onError("No hay chats");
                else try {
                    view.initChatList(returnParam);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getChatMessages(String chatId){
        getChatMessagesUseCase.execute(chatId, new GetChatMessagesUseCase.GetChatMessagesListener() {
            @Override
            public void onError(ErrorBundle errorBundle) {
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(ChatMessage returnParam) {
                view.addChatMessage(returnParam);
            }
        });
    }

    public void sendMessage(ChatMessage chatTextMessage) {
        sendChatMessageUseCase.execute(chatTextMessage, new SendChatMessageUseCase.SendChatMessageCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(Void returnParam) {

            }
        });
    }

    public void setTruekeStatus(int status, String chatID, String truekeID){
        setTruekeStatusUseCase.execute(new TruekeData(status,chatID,truekeID), new SetTruekeStatusUseCase.SetTruekeStatusCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(Void returnParam) {
                view.OnTruekeStatusUpdated();
            }
        });
    }

    public void getProducts(final List<Integer> productId) {

        getProductUseCase.execute(productId, new GetProductUseCase.GetProductCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {

                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(List<Product> returnParam) {

                products = new ArrayList<>();
                size_products = returnParam.size();
                count_products = 0;
                aux_returnParam = new ArrayList<>();
                aux_returnParam = returnParam;
                for (int i = 0; i< returnParam.size(); i++) {
                    aux_returnParam.get(i).setId(productId.get(i));
                }
                count_i = 0;
                view.hideProgress();
                if(returnParam.size() != 0){
                    getImagesProductcallback();
                }
                else {
                    view.setproductTitle(returnParam);
                    view.hideProgress();
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
                view.setproductTitle(products);
                view.hideProgress();
            }
        }
    }

    public void createTrueke(String chatID) {
        createTruekeUseCase.execute(chatID, new CreateTruekeUseCase.CreateTruekeCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(Void returnParam) {
                view.OnTruekeCreated();
            }
        });
    }

    public void PayTrueke(int my_product, String chat_id, int payment_id) {
        payTruekeUseCase.execute(new TruekePaymentData(my_product,chat_id,payment_id), new PayTruekeUseCase.PayTruekeCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(Void returnParam) {
                view.onTruekePaid();
            }
        });
    }

    public void voteTrueke(float rating,int product_id, String chatId, boolean isUser1, int otherProduct){
        voteUseCase.execute(new VoteData(product_id, rating, chatId, isUser1, otherProduct), new VoteUseCase.VoteUseCaseCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(Void returnParam) {
                view.onTruekeVoted();
            }
        });
    }

    //TODO:
    public void deleteChat(String chatId){
        deleteChatUseCase.execute(chatId, new DeleteChatUseCase.VoidCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(Void returnParam) {
                //view.onChatDeleted();
            }
        });
    }
}
