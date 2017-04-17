package trigues.com.data.datasource.impl;

import android.util.Log;

import com.google.gson.Gson;
import com.trigues.entity.Payment;
import com.trigues.entity.Product;
import com.trigues.entity.Shipment;
import com.trigues.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import trigues.com.data.FakeInterceptor;
import trigues.com.data.datasource.ApiInterface;
import trigues.com.data.entity.ApiDTO;
import trigues.com.data.service.RetrofitErrorHandler;
import trigues.com.data.service.ServerService;

/**
 * Created by mbaque on 15/03/2017.
 */

public class ApiDataSource implements ApiInterface {

    private ServerService server;
    private FakeInterceptor interceptor;

    @Inject
    public ApiDataSource() {
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        interceptor = new FakeInterceptor();
        builder.addInterceptor(interceptor)
                .connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES);
        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("http://10.4.41.147:3000/")
                .baseUrl("http://10.4.41.147:3000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        server = retrofit.create(ServerService.class);

    }

    @Override
    public void getUserProductDetails(int productId, final ProductDataCallback dataCallback) {
        interceptor.setResponseString("{\n" +
                "  \"id\" : 12345,\n" +
                "  \"userId\" : 54321,\n" +
                "  \"title\" : \"Mochila EASTPAK usada\",\n" +
                "  \"description\" : \"Mochila de gran calidad y duración. Capacidad de 23L. Color negro. En buen estado.\",\n" +
                "  \"images\" : [\n" +
                "    \"https://photos6.spartoo.es/photos/231/231523/231523_350_A.jpg\",\n" +
                "    \"https://www.bolsosvandi.com/server/Portal_0001611/img/products/mochila-eastpak-padded-pak-tejano_1037867_29360533.jpg\",\n" +
                "    \"https://asset3.surfcdn.com/mochilas-eastpak-mochila-eastpak-padded-pak-r-negro.jpg?w=1200&h=1200&r=4&q=80&o=5vHKHHorIf3qW0m5E5ULl$0XIx0j&V=uTZf\",\n" +
                "    \"https://photos6.spartoo.es/photos/179/1797422/1797422_1200_A.jpg\"\n" +
                "  ],\n" +
                "  \"productCategory\" : \"Accesorios\",\n" +
                "  \"desiredCategories\" : [\n" +
                "    \"Accesorios\", \"Electrodomesticos\", \"Informatica\", \"Moda\"\n" +
                "  ],\n" +
                "  \"minPrice\" : 100,\n" +
                "  \"maxPrice\" : 200\n" +
                "}");

        server.getUserProductDetails().enqueue(new RetrofitErrorHandler<Product>(dataCallback) {
            @Override
            public void onResponse(Product body) {
                dataCallback.onSuccess(body);
            }
        });
    }
    @Override
    public void showProfile(int userID, final UserDataCallback userDataCallback){
        interceptor.setResponseString("{\n" +
                "  \"id\" : 12345,\n" +
                "  \"phone\" : 612345678,\n" +
                "  \"user\" : \"Esther Colero\",\n" +
                "  \"password\" : \"pouman\",\n" +
                "  \"email\" : \"chetaso@parguela.es\",\n" +
                "  \"birthDate\" : \"1999-12-10\",\n" +
                "  \"products\" : 5,\n" +
                "  \"truekes\" : 2,\n" +
                "  \"rating\" : 4\n" +
                "}");
        server.getUserProfile().enqueue(new RetrofitErrorHandler<User>(userDataCallback) {
            @Override
            public void onResponse(User body) {
                userDataCallback.onSuccess(body);
            }
        });
    }

    @Override
    public void showPaymentInfo(int id, final PaymentDataCallback paymentDataCallback) {
        interceptor.setResponseString("{\n" +
                "  \"id\" : \"12\",\n" +
                "  \"user_id\" : \"1234\",\n" +
                "  \"type\" : \"MasterCard/4B/Euro6000\",\n" +
                "  \"number\" : \"1234123412341234\",\n" +
                "  \"expireDate\" : \"2020-02-01\",\n" +
                "  \"name\" : \"Homer\",\n" +
                "  \"country\" : \"USA\",\n" +
                "  \"province\" : \"Barcelona\",\n" +
                "  \"city\" : \"Springfield\",\n" +
                "  \"postalCode\" : \"11101\",\n" +
                "  \"address\" : \"Calle del general Comilla\",\n" +
                "  \"phone\" : \"619703921\"\n" +
                "}");
        server.getPaymentInfo().enqueue(new RetrofitErrorHandler<Payment>(paymentDataCallback) {
            @Override
            public void onResponse(Payment body) {
                paymentDataCallback.onSuccess(body);
            }
        });
    }

    @Override
    public void showShipmentInfo(Integer id, final ShipmentDataCallback shipmentDataCallback) {
        interceptor.setResponseString("{\n" +
                "  \"id\" : \"12\",\n" +
                "  \"user_id\" : \"1234\",\n" +
                "  \"country\" : \"USA\",\n" +
                "  \"province\" : \"Barcelona\",\n" +
                "  \"city\" : \"Springfield\",\n" +
                "  \"postalCode\" : \"11101\",\n" +
                "  \"address\" : \"Calle del general Comilla\",\n" +
                "  \"name\" : \"Homer\",\n" +
                "  \"idCard\" : \"12931230\",\n" +
                "  \"phone\" : \"619703921\"\n" +
                "}");
        server.getShipmentInfo().enqueue(new RetrofitErrorHandler<Shipment>(shipmentDataCallback) {
            @Override
            public void onResponse(Shipment body) {
                shipmentDataCallback.onSuccess(body);
            }
        });
    }

    @Override
    public void changeProfile(User user, final BooleanDataCallback booleanDataCallback) {
        server.changeProfile(user).enqueue(new RetrofitErrorHandler<ApiDTO<Void>>(booleanDataCallback){
            @Override
            public void onResponse(ApiDTO<Void> body) {
                booleanDataCallback.onSuccess(false);
            }
        });
    }

    @Override
    public void showProducts(int userID, final ProductListDataCallback dataCallback) {
        List<String> llista = new ArrayList<>();
        llista.add("https://photos6.spartoo.es/photos/231/231523/231523_350_A.jpg");
        List<String> llista2 = new ArrayList<>();
                llista2.add("Electrodomesticos");
        Product prod = new Product(12345, 54321, "Mochilla", "Mochila basura",llista ,
                "Accesorios", llista2, 100, 200);
        List<Product> llistaProd= new ArrayList<>();

        llistaProd.add(prod);
        llistaProd.add(prod);
        llistaProd.add(prod);
        llistaProd.add(prod);

        Gson gson = new Gson();

        interceptor.setResponseString(gson.toJson(llistaProd));

        server.getUserProducts(/*userID*/).enqueue(new RetrofitErrorHandler< /*ApiDTO<Void>*/ List<Product> >(dataCallback) {
            @Override
            public void onResponse(/*ApiDTO<Void>*/List<Product> body) {
                dataCallback.onSuccess(body);
            }
        });

    }

    @Override
    public void register(User user, final BooleanDataCallback dataCallback) {
        server.register(user).enqueue(new RetrofitErrorHandler<ApiDTO<Void>>(dataCallback) {
            @Override
            public void onResponse(ApiDTO<Void> body) {
                dataCallback.onSuccess(body.getError());
            }
        });
    }

    @Override
    public void login(User user, final BooleanDataCallback dataCallback) {
        server.login(user).enqueue(new RetrofitErrorHandler<ApiDTO<Void>>(dataCallback) {
            @Override
            public void onResponse(ApiDTO<Void> body) {
                dataCallback.onSuccess(!body.getError());
            }
        });
    }
}
