package trigues.com.trueke.presenter;

import com.trigues.entity.ChatInfo;
import com.trigues.entity.ChatMessage;
import com.trigues.entity.TruekeData;
import com.trigues.exception.ErrorBundle;
import com.trigues.usecase.CreateTruekeUseCase;
import com.trigues.usecase.GetChatMessagesUseCase;
import com.trigues.usecase.GetChatsUseCase;
import com.trigues.usecase.SendChatMessageUseCase;
import com.trigues.usecase.SetTruekeStatusUseCase;

import java.util.List;

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

    @Inject
    public ChatPresenter(ChatListActivity view, GetChatMessagesUseCase getChatMessagesUseCase,
                         SendChatMessageUseCase sendChatMessageUseCase,
                         SetTruekeStatusUseCase setTruekeStatusUseCase,
                         CreateTruekeUseCase createTruekeUseCase,
                         GetChatsUseCase getChatsUseCase) {
        this.view = view;
        this.getChatMessagesUseCase = getChatMessagesUseCase;
        this.sendChatMessageUseCase = sendChatMessageUseCase;
        this.setTruekeStatusUseCase = setTruekeStatusUseCase;
        this.createTruekeUseCase = createTruekeUseCase;
        this.getChatsUseCase = getChatsUseCase;
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
                if(returnParam.size() == 0) view.onError("holis");
                else view.initChatList(returnParam);
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

    public void createTrueke(String chatID) {

    }
}
