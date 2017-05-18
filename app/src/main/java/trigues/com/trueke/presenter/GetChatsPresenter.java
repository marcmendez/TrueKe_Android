package trigues.com.trueke.presenter;

import com.trigues.entity.ChatInfo;
import com.trigues.exception.ErrorBundle;
import com.trigues.usecase.GetChatsUseCase;

import java.util.List;

import javax.inject.Inject;

import trigues.com.trueke.view.ChatListActivity;

/**
 * Created by Alba on 22/03/2017.
 */

public class GetChatsPresenter {
    private ChatListActivity view;
    private GetChatsUseCase getChatsUseCase;

    @Inject
    public GetChatsPresenter(ChatListActivity view, GetChatsUseCase getChatsUseCase) {
        this.view = view;
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
}