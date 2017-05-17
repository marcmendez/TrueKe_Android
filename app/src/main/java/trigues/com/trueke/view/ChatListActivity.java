package trigues.com.trueke.view;

import com.trigues.entity.ChatMessage;

import java.util.List;

/**
 * Created by mbaque on 03/05/2017.
 */

public interface ChatListActivity extends MenuActivity{

    void setChatMessages(List<ChatMessage> messages);

}
