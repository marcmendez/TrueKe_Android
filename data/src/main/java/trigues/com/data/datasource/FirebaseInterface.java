package trigues.com.data.datasource;

import com.trigues.callback.DefaultCallback;
import com.trigues.callback.FirebaseChatListener;
import com.trigues.entity.ChatMessage;

/**
 * Created by mbaque on 06/05/2017.
 */

public interface FirebaseInterface {

    void getChatMessages(String chatId, FirebaseChatListener dataCallback);

    void newMessage(String chatId, ChatMessage message, FirebaseVoidCallback dataCallback);

    void removeListeners(String chatId);


    interface FirebaseVoidCallback extends DefaultCallback<Void> {}

}
