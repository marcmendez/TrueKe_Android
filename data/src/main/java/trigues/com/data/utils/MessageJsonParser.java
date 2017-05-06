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

    public static List<ChatMessage> parseJson(DataSnapshot dataSnapshot){
        HashMap<String, HashMap<String, Object>> firebaseResponse = (HashMap<String, HashMap<String, Object>>) dataSnapshot.getValue();

        List<ChatMessage> result = new ArrayList<>();

        for(Map.Entry<String, HashMap<String, Object>> entry: firebaseResponse.entrySet()){
            if(entry.getValue().containsKey("message")){
                result.add(new ChatTextMessage(entry.getValue()));
            }
            else if (entry.getValue().containsKey("latitude")){
                result.add(new ChatLocation(entry.getValue()));
            }
            else if (entry.getValue().containsKey("status")){
                result.add(new ChatTrueke(entry.getValue(), entry.getKey()));
            }
            else{
                result.add(new ChatImage(entry.getValue()));
            }
        }

        return result;
    }

}
