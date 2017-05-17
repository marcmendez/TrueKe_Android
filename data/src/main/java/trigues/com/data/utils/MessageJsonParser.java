package trigues.com.data.utils;

import com.google.firebase.database.DataSnapshot;
import com.trigues.entity.ChatImage;
import com.trigues.entity.ChatLocation;
import com.trigues.entity.ChatMessage;
import com.trigues.entity.ChatTextMessage;
import com.trigues.entity.ChatTrueke;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mbaque on 06/05/2017.
 */

public class MessageJsonParser {

    public static List<ChatMessage> parseMessages(DataSnapshot dataSnapshot){
        HashMap<String, HashMap<String, Object>> firebaseResponse = (HashMap<String, HashMap<String, Object>>) dataSnapshot.getValue();

        List<ChatMessage> result = new ArrayList<>();

        for(Map.Entry<String, HashMap<String, Object>> entry: firebaseResponse.entrySet()){
            result.add(parseMessage(entry.getKey(), entry.getValue()));
        }

        return result;
    }

    public static ChatMessage parseMessage(String key, HashMap<String, Object> message){
        if(message.containsKey("message")){
            return new ChatTextMessage(key, message);
        }
        else if (message.containsKey("latitude")){
            return new ChatLocation(key, message);
        }
        else if (message.containsKey("status")){
            return new ChatTrueke(key, message);
        }
        else{
            return new ChatImage(key, message);
        }
    }

}
