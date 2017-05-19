package trigues.com.trueke.view;

import com.trigues.entity.ChatInfo;

import java.util.List;

import com.trigues.entity.ChatMessage;

/**
 * Created by mbaque on 03/05/2017.
 */

public interface ChatListActivity extends MenuActivity{
    void initChatList(List<ChatInfo> p);

    void addChatMessage(ChatMessage messages);

}
