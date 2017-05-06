package trigues.com.data.utils;

import com.google.firebase.database.DataSnapshot;
import com.trigues.entity.ChatImage;
import com.trigues.entity.ChatLocation;
import com.trigues.entity.ChatMessage;
import com.trigues.entity.ChatTextMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mbaque on 06/05/2017.
 */

public class MessageJsonParser {

    public static List<ChatMessage> parseJson(DataSnapshot dataSnapshot){
        HashMap<String, HashMap<String, Object>> firebaseResponse = (HashMap<String, HashMap<String, Object>>) dataSnapshot.getValue();

        List<ChatMessage> result = new ArrayList<>();

        for(HashMap<String, Object> entry : firebaseResponse.values()){
            if(entry.containsKey("message")){
                result.add(new ChatTextMessage(entry));
            }
            else if (entry.containsKey("latitude")){
                result.add(new ChatLocation(entry));
            }
            else{
                result.add(new ChatImage(entry));
            }
        }

        return result;
    }

}
