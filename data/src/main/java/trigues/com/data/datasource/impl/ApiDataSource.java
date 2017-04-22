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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import trigues.com.data.FakeInterceptor;
import trigues.com.data.datasource.ApiInterface;
import trigues.com.data.entity.ApiDTO;
import trigues.com.data.entity.LoginDTO;
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
    public void showProfile(String token, String id,final UserDataCallback userDataCallback){
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
        server.getUserProfile(token,id).enqueue(new RetrofitErrorHandler<User>(userDataCallback) {
            @Override
            public void onResponse(User body) {
                userDataCallback.onSuccess(body);
            }
        });
    }

    @Override
    public void showPayments(int id, final PaymentsCallback paymentsCallback) {
        interceptor.setResponseString("[{\n" +
                "  \"id\": 1,\n" +
                "  \"user_id\": 1,\n" +
                "  \"type\": \"Visa/4B/Euro6000\",\n" +
                "  \"number\": \"123456789\",\n" +
                "  \"expireDate\": \"1990-05-06\",\n" +
                "  \"name\": \"Sancho Panza\",\n" +
                "  \"country\": \"España\",\n" +
                "  \"province\": \"Barcelona\",\n" +
                "  \"city\": \"Barcelona\",\n" +
                "  \"postalCode\": 8029,\n" +
                "  \"address\": \"Carrer Diagonal\",\n" +
                "  \"phone\": \"654654654\"\n" +
                "},\n" +
                "{\n" +
                "\"id\": 2,\n" +
                "\"user_id\": 1,\n" +
                "\"type\": \"Visa/4B/Euro6000\",\n" +
                "\"number\": \"987654321\",\n" +
                "\"expireDate\": \"1990-05-06\",\n" +
                "\"name\": \"Sancho Panza\",\n" +
                "\"country\": \"España\",\n" +
                "\"province\": \"Barcelona\",\n" +
                "\"city\": \"Barcelona\",\n" +
                "\"postalCode\": 8029,\n" +
                "\"address\": \"Carrer Diagonal\",\n" +
                "\"phone\": \"654654654\"\n" +
                "}]");
        server.getPaymentInfo().enqueue(new RetrofitErrorHandler<List<Payment>>(paymentsCallback) {
            @Override
            public void onResponse(List<Payment> body) {
                paymentsCallback.onSuccess(body);
            }
        });
    }

    @Override
    public void showShipments(Integer id, final ShipmentsCallback shipmentsCallback) {
        interceptor.setResponseString("[{\n" +
                "  \"id\": 1,\n" +
                "  \"user_id\": 1,\n" +
                "  \"country\": \"Spain\",\n" +
                "  \"province\": \"Barcelona\",\n" +
                "  \"city\": \"Barcelona\",\n" +
                "  \"postalCode\": 8006,\n" +
                "  \"address\": \"Calle Falsa 123\",\n" +
                "  \"name\": \"Pepito Mendizabal\",\n" +
                "  \"idCard\": \"654845616531\",\n" +
                "  \"phone\": \"654654654\"\n" +
                "}, {\n" +
                "  \"id\": 2,\n" +
                "  \"user_id\": 1,\n" +
                "  \"country\": \"Spain\",\n" +
                "  \"province\": \"Barcelona\",\n" +
                "  \"city\": \"Barcelona\",\n" +
                "  \"postalCode\": 8029,\n" +
                "  \"address\": \"Calle Falsa 123\",\n" +
                "  \"name\": \"Pepito Mendizabal\",\n" +
                "  \"idCard\": \"654845616531\",\n" +
                "  \"phone\": \"654654654\"\n" +
                "}]");
        server.getShipmentInfo().enqueue(new RetrofitErrorHandler<List<Shipment>>(shipmentsCallback) {
            @Override
            public void onResponse(List<Shipment> body) {
                shipmentsCallback.onSuccess(body);
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
    public void deleteUser(int user_id, final BooleanDataCallback booleanDataCallback) {
        server.deleteUser(user_id).enqueue(new RetrofitErrorHandler<ApiDTO<Void>>(booleanDataCallback){

            @Override
            public void onResponse(ApiDTO<Void> body) {
                booleanDataCallback.onSuccess(false);
            }
        });
    }

    @Override
    public void changePayment(Payment payment, final BooleanDataCallback booleanDataCallback) {
        server.changePayment(payment).enqueue(new RetrofitErrorHandler<ApiDTO<Void>>(booleanDataCallback){

            @Override
            public void onResponse(ApiDTO<Void> body) {
                booleanDataCallback.onSuccess(false);
            }
        });
    }

    @Override
    public void newPayment(Payment payment, final BooleanDataCallback booleanDataCallback) {
        server.newPayment(payment).enqueue(new RetrofitErrorHandler<ApiDTO<Void>>(booleanDataCallback){

            @Override
            public void onResponse(ApiDTO<Void> body) {
                booleanDataCallback.onSuccess(false);
            }
        });
    }

    @Override
    public void deletePayment(int payment_id, final BooleanDataCallback booleanDataCallback) {
        server.deletePayment(payment_id).enqueue(new RetrofitErrorHandler<ApiDTO<Void>>(booleanDataCallback) {
            @Override
            public void onResponse(ApiDTO<Void> body) {
                booleanDataCallback.onSuccess(false);
            }
        });
    }

    @Override
    public void deleteShipment(int shipment_id, final BooleanDataCallback booleanDataCallback) {
        server.deleteShipment(shipment_id).enqueue(new RetrofitErrorHandler<ApiDTO<Void>>(booleanDataCallback) {
            @Override
            public void onResponse(ApiDTO<Void> body) {
                booleanDataCallback.onSuccess(false);
            }
        });
    }

    @Override
    public void newShipment(Shipment shipment, final BooleanDataCallback booleanDataCallback) {
        Log.i("shipment", "newShipment: ");
        server.newShipment(shipment).enqueue(new RetrofitErrorHandler<ApiDTO<Void>>(booleanDataCallback){
            @Override
            public void onResponse(ApiDTO<Void> body) {
                booleanDataCallback.onSuccess(false);
            }
        });
    }

    @Override
    public void changeShipment(Shipment shipment, final BooleanDataCallback booleanDataCallback) {
        server.changeShipment(shipment).enqueue(new RetrofitErrorHandler<ApiDTO<Void>>(booleanDataCallback) {
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
    public void login(User user, final LoginDataCallback dataCallback) {
        server.login(user).enqueue(new RetrofitErrorHandler<ApiDTO<LoginDTO>>(dataCallback) {
            @Override
            public void onResponse(ApiDTO<LoginDTO> body) {
                dataCallback.onSuccess(body);
            }
        });
    }

    @Override
    public void addProduct(Product product, final BooleanDataCallback dataCallback) {
        server.addProduct(product).enqueue(new RetrofitErrorHandler<ApiDTO<Void>>(dataCallback) {
            @Override
            public void onResponse(ApiDTO<Void> body) {
                dataCallback.onSuccess(!body.getError());
            }
        });
    }

}
