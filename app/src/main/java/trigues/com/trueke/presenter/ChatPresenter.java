package trigues.com.trueke.presenter;

import com.trigues.entity.ChatMessage;
import com.trigues.entity.TruekeData;
import com.trigues.exception.ErrorBundle;
import com.trigues.usecase.CreateTruekeUseCase;
import com.trigues.usecase.GetChatMessagesUseCase;
import com.trigues.usecase.SendChatMessageUseCase;
import com.trigues.usecase.SetTruekeStatusUseCase;

import javax.inject.Inject;

import trigues.com.trueke.view.ChatListActivity;

/**
 * Created by mbaque on 17/05/2017.
 */

public class ChatPresenter {

    ChatListActivity view;
    private GetChatMessagesUseCase getChatMessagesUseCase;
    private SendChatMessageUseCase sendChatMessageUseCase;
    private SetTruekeStatusUseCase setTruekeStatusUseCase;
    private CreateTruekeUseCase createTruekeUseCase;

    @Inject
    public ChatPresenter(ChatListActivity view, GetChatMessagesUseCase getChatMessagesUseCase,
                         SendChatMessageUseCase sendChatMessageUseCase, SetTruekeStatusUseCase setTruekeStatusUseCase, CreateTruekeUseCase createTruekeUseCase) {
        this.view = view;
        this.getChatMessagesUseCase = getChatMessagesUseCase;
        this.sendChatMessageUseCase = sendChatMessageUseCase;
        this.setTruekeStatusUseCase = setTruekeStatusUseCase;
        this.createTruekeUseCase = createTruekeUseCase;
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
