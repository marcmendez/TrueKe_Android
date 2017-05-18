package com.trigues.callback;

import com.trigues.entity.ChatMessage;
import com.trigues.exception.ErrorBundle;

/**
 * Created by mbaque on 06/05/2017.
 */

public interface FirebaseChatListener {

    void onNewMessage(ChatMessage message);

    void onError(ErrorBundle bundle);

}
