package trigues.com.data.datasource;

/**
 * Created by mbaque on 05/04/2017.
 */

public interface InternalStorageInterface {

    boolean isUserLogged();

    void saveToken(String token);

    String getToken();

    void onLogOut();

}
