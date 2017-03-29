package trigues.com.data.service;

import com.trigues.entity.Product;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import trigues.com.data.entity.ApiDTO;

/**
 * Created by mbaque on 18/03/2017.
 */

public interface ServerService {

    @POST("/")
    Call<ApiDTO<Void>> register();

    @GET("/")
    Call<ApiDTO<Product>> getUserProduct();
}
