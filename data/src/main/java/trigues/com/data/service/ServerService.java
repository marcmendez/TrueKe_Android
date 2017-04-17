package trigues.com.data.service;

import com.trigues.entity.Payment;
import com.trigues.entity.Product;
import com.trigues.entity.Shipment;
import com.trigues.entity.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import trigues.com.data.entity.ApiDTO;

/**
 * Created by mbaque on 18/03/2017.
 */

public interface ServerService {

    @POST("authenticate")
    Call<ApiDTO<Void>> login(@Body User user);

    @POST("users")
    Call<ApiDTO<Void>> register(@Body User user);

    @GET("/")
    Call<List<Product>> getUserProducts(/*@Body  Integer user_id*/);

    @GET("/")
    Call<Product> getUserProductDetails();

    @GET("users/:id") //canviar a phone o mail
    Call<User> getUserProfile();

    @GET("paymentmethods/:user_id")
    Call<Payment> getPaymentInfo();

    @GET("shipmentmethods/:user_id")
    Call<Shipment> getShipmentInfo();

    @PUT("users/:id")
    Call<ApiDTO<Void>> changeProfile(@Body User user);
}
