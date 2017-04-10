package trigues.com.trueke.view;

import com.trigues.entity.User;

/**
 * Created by Albert on 07/04/2017.
 */

public interface UserProfile extends BaseActivity {
    void onProfileRetrieved(User user);
}
