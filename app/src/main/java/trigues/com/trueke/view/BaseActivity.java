package trigues.com.trueke.view;

/**
 * Created by mbaque on 15/03/2017.
 */

public interface BaseActivity {

    void showProgress(String message);

    void hideProgress();

    void onError(String error);

}
