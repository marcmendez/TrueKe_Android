package trigues.com.data;

import android.support.test.runner.AndroidJUnit4;

import com.trigues.entity.Product;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import trigues.com.data.interceptor.FakeInterceptor;
import trigues.com.data.service.ServerService;

/**
 * Instrumentation getUserProductDetails, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ApiDataSourceTest{

    private ServerService server;
    private FakeInterceptor interceptor;

    @Before
    public void setUp(){
        interceptor = new FakeInterceptor();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(interceptor)
                .connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES);
        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.4.41.147:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        server = retrofit.create(ServerService.class);
    }

    @Test
    public void getUserProductDetails() throws URISyntaxException, FileNotFoundException {
        //Abans de fer una crida al servidor fer interceptor.setResponseString(response)
        //  on response es la resposta que retornarà el servidor.
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

        server.getUserProduct().enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {

            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });

    }
}
