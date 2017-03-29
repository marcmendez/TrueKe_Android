package trigues.com.data.service;

import com.trigues.entity.Product;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by mbaque on 18/03/2017.
 */

public interface ServerService {

    @GET("/")
    Call<Product> getUserProduct();

}
