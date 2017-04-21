package trigues.com.data.service;

import com.trigues.entity.Product;
import com.trigues.entity.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import trigues.com.data.entity.ApiDTO;
import trigues.com.data.entity.LoginDTO;

/**
 * Created by mbaque on 18/03/2017.
 */

public interface ServerService {

    @POST("authenticate")
    Call<ApiDTO<LoginDTO>> login(@Body User user);

    @POST("users")
    Call<ApiDTO<Void>> register(@Body User user);

    @GET("/")
    Call<List<Product>> getUserProducts(/*@Body  Integer user_id*/);

    @GET("/")
    Call<Product> getUserProductDetails();

    @POST("")
    Call<ApiDTO<Void>> addProduct(@Body Product product);
}
