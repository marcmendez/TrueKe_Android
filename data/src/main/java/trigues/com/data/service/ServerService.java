package trigues.com.data.service;

import com.trigues.entity.Payment;
import com.trigues.entity.Product;
import com.trigues.entity.Shipment;
import com.trigues.entity.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import trigues.com.data.entity.ApiDTO;
import trigues.com.data.entity.LoginDTO;
import trigues.com.data.entity.Password;
import trigues.com.data.entity.UserName;
import trigues.com.data.entity.ProductDTO;

/**
 * Created by mbaque on 18/03/2017.
 */

public interface ServerService {

    @POST("authenticate")
    Call<ApiDTO<LoginDTO>> login(@Body User user);

    @POST("users")
    Call<ApiDTO<Void>> register(@Body User user);

    //products
    @GET("products/{userid}")
    Call<ApiDTO<List<Product>>> getUserProducts(@Header("token") String token, @Path("userid") Integer userID);

    @GET("")
    Call<Product> getUserProductDetails(String token, int productID);

    @POST("products")
    Call<ApiDTO<Void>> addProduct(@Header("token") String token, @Body ProductDTO product);

    @DELETE("products/{product_id}")
    Call<ApiDTO<Void>> deleteProduct(@Header("token") String token,  @Path("product_id") String product_id);

    //user info
    @GET("users/{id}")
    Call<ApiDTO<List<User>>> getUserProfile(@Header("token") String token, @Path("id") String id);

    @PUT("users/{id}")
    Call<ApiDTO<Void>> changePassword(@Header("token") String token, @Path("id") String id, @Body Password password);

    @PUT("users/{id}")
    Call<ApiDTO<Void>> changeUsername(@Header("token") String token, @Path("id") String id, @Body UserName username);

    @DELETE("users/{id}")
    Call<ApiDTO<Void>> deleteUser(int id);

    //payments
    @GET("paymentmethods/{id}")
    Call<ApiDTO<List<Payment>>> getPaymentInfo(@Header("token") String token, @Path("id") String id);

    @PUT("paymentmethods/:id")
    Call<ApiDTO<Void>> changePayment(@Body Payment payment);

    @POST("paymentmethods")
    Call<ApiDTO<Void>> newPayment(@Header("token") String token,@Body Payment payment);

    @DELETE("paymentmethods/{id}")
    Call<ApiDTO<Void>> deletePayment(@Header("token")String token, @Path("id") String payment_id);

    //shipments
    @GET("shipmentmethods/{id}")
    Call<ApiDTO<List<Shipment>>> getShipmentInfo(@Header("token") String token, @Path("id") String id);

    @PUT("shipmentmethods/:id")
    Call<ApiDTO<Void>> changeShipment(@Body Shipment shipment);

    @POST("shipmentmethods")
    Call<ApiDTO<Void>> newShipment(@Header("token") String token,@Body Shipment shipment);

    @DELETE("shipmentmethods/:id")
    Call<ApiDTO<Void>> deleteShipment(int shipment_id);

    //Matches
    @POST("matches")
    Call<ApiDTO<Void>> acceptMatch(@Header("token") String token, Integer product_id1, Integer product_id2, Integer wants);

    @POST("matches")
    Call<ApiDTO<Void>> rejectMatch(@Header("token") String token, Integer product_id1, Integer product_id2, Integer wants);

    @DELETE("shipmentmethods/{id}")
    Call<ApiDTO<Void>> deleteShipment(@Header("token")String token, @Path("id") String shipment_id);

    @GET("products/matchmaking/{id}")
    Call<ApiDTO<List<Product>>> getMatchMakingProducts(@Header("token") String token,@Path("id") int prodID);
}
