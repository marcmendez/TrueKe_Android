package trigues.com.data.datasource.impl;

import com.trigues.entity.Product;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import trigues.com.data.FakeInterceptor;
import trigues.com.data.datasource.ApiInterface;
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
                .baseUrl("http://www.google.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        server = retrofit.create(ServerService.class);

    }

    @Override
    public void getUserProductDetails(int productId, final GetUserProductDataDetails dataCallback) {
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

        server.getUserProduct().enqueue(new RetrofitErrorHandler<Product>(dataCallback) {
            @Override
            public void onResponse(Product body) {
                dataCallback.onSuccess(body);
            }
        });
    }

    @Override
    public void showProducts(int userID, final showProducts dataCallback) {
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

        server.getUserProduct().enqueue(new RetrofitErrorHandler<Product>(dataCallback) {
            @Override
            public void onResponse(Product body) {
                dataCallback.onSuccess(body);
            }
        });

    }
}
