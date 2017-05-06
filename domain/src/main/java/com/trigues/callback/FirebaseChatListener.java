package com.trigues.callback;

import com.trigues.entity.ChatMessage;
import com.trigues.exception.ErrorBundle;

import java.util.List;

/**
 * Created by mbaque on 06/05/2017.
 */

public interface FirebaseChatListener {

    void onNewMessage(List<ChatMessage> messages);

    void onError(ErrorBundle bundle);

}
