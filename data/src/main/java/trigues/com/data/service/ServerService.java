package trigues.com.data.service;

/**
 * Created by mbaque on 18/03/2017.
 */

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by mbaque on 18/03/2017.
 */

public interface ServerService {

    @POST("/Users")
    Call<Boolean> register();
}