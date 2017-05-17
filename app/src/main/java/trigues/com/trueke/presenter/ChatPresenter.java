package trigues.com.trueke.presenter;

import com.trigues.entity.ChatMessage;
import com.trigues.exception.ErrorBundle;
import com.trigues.usecase.GetChatMessagesUseCase;

import java.util.List;

import javax.inject.Inject;

import trigues.com.trueke.view.ChatListActivity;

/**
 * Created by mbaque on 17/05/2017.
 */

public class ChatPresenter {

    ChatListActivity view;
    private GetChatMessagesUseCase getChatMessagesUseCase;

    @Inject
    public ChatPresenter(ChatListActivity view, GetChatMessagesUseCase getChatMessagesUseCase) {
        this.view = view;
        this.getChatMessagesUseCase = getChatMessagesUseCase;
    }

    public void getChatMessages(String chatId){
        getChatMessagesUseCase.execute(chatId, new GetChatMessagesUseCase.GetChatMessagesListener() {
            @Override
            public void onError(ErrorBundle errorBundle) {
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(List<ChatMessage> returnParam) {
                view.setChatMessages(returnParam);
            }
        });
    }
}
