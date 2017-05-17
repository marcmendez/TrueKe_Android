package trigues.com.data.datasource;

import com.trigues.entity.Product;
import com.trigues.entity.User;

/**
 * Created by mbaque on 05/04/2017.
 */

public interface InternalStorageInterface {

    boolean isUserLogged();

    void saveToken(String token);

    void saveUser(User user);

    User getUser();

    String getToken();

    void onLogOut();

    void saveProductId (Integer id);

    int getProductId();

}
