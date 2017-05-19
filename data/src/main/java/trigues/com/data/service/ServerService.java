package trigues.com.data.service;

import com.trigues.entity.Payment;
import com.trigues.entity.Product;
import com.trigues.entity.Shipment;
import com.trigues.entity.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import trigues.com.data.entity.ApiDTO;
import trigues.com.data.entity.CategoryDTO;
import trigues.com.data.entity.ImagePath;
import trigues.com.data.entity.ChatDTO;
import trigues.com.data.entity.LoginDTO;
import trigues.com.data.entity.Password;
import trigues.com.data.entity.ProductId;
import trigues.com.data.entity.ProductsMatchDTO;
import trigues.com.data.entity.ReportProductDTO;
import trigues.com.data.entity.UserImage;
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
    @GET("products/byuser/{userid}")
    Call<ApiDTO<List<Product>>> getUserProducts(@Header("token") String token, @Path("userid") Integer userID);

    @GET("products/{product_id}")
    Call<ApiDTO<Product>> getUserProductDetails(@Header("token")String token, @Path("product_id") int productID);

    @POST("products")
    Call<ApiDTO<ProductId>> addProduct(@Header("token") String token, @Body ProductDTO product);

    @DELETE("products/{product_id}")
    Call<ApiDTO<Void>> deleteProduct(@Header("token") String token,  @Path("product_id") String product_id);

    @POST("report/product")
    Call<ApiDTO<Void>> reportProduct(@Header("token") String token, @Body ReportProductDTO dto);

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
    Call<ApiDTO<Void>> acceptMatch(@Header("token") String token, @Body ProductsMatchDTO dto);

    @POST("matches")
    Call<ApiDTO<Void>> rejectMatch(@Header("token") String token, @Body ProductsMatchDTO dto);



    @DELETE("shipmentmethods/{id}")
    Call<ApiDTO<Void>> deleteShipment(@Header("token")String token, @Path("id") String shipment_id);

    @GET("products/matchmaking/{id}")
    Call<ApiDTO<List<Product>>> getMatchMakingProducts(@Header("token") String token,@Path("id") int prodID);

    @HTTP(method = "POST", path = "productwantscategory", hasBody = true)
    Call<ApiDTO<Void>> addProductCategory(@Header("token") String token, @Body CategoryDTO category);

    @HTTP(method = "DELETE", path = "productwantscategory", hasBody = true)
    Call<ApiDTO<Void>> deleteProductCategory(@Header("token") String token,  @Body CategoryDTO category);

    @GET("productwantscategory/{product_id}")
    Call<ApiDTO<List<CategoryDTO>>> getDesiredCategories(@Header("token") String token, @Path("product_id") int prodID);

    //images
    @FormUrlEncoded
    @POST("images")
    Call<ApiDTO<String>> addImages(@Field("image") String image);

    @FormUrlEncoded
    @POST("products/{id}/images")
    Call<ApiDTO<Void>> addImagesProduct(@Header("token") String token, @Path("id") int id, @Field("image_md5") String image_md5);

    @GET("products/{id}/images")
    Call<ApiDTO<List<ImagePath>>> getImagesProduct(@Path("id") int product_id);

    @GET("images/{md5}")
    Call<ApiDTO<String>> getImages(@Path("md5") String md5);

    @PUT("users/{id}")
    Call<ApiDTO<Void>> changeProfileUserImage(@Header("token") String token, @Path("id") String id, @Body UserImage userImage);

    @GET("chats/byuser/{user_id}")
    Call<ApiDTO<List<ChatDTO>>> getUserChats(@Header("token") String token, @Path("user_id") int userID);

    @GET("products/{product_id}")
    Call<ApiDTO<ProductDTO>> getProductInfo(@Header("token") String token, @Path("product_id") int prodID);
    @POST("truekes")
    Call<ApiDTO<Void>> createTrueke(@Header("token")String admintoken,@Path("chat_id") String chatID);
}
