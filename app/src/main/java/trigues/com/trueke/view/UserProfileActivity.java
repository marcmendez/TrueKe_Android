package trigues.com.trueke.view;

import com.trigues.entity.User;

/**
 * Created by mbaque on 09/04/2017.
 */

public interface UserProfileActivity extends MenuActivity {
    void onProfileRetrieved(User user);
}
