package trigues.com.data.datasource.impl;

import com.trigues.entity.User;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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

    public ApiDataSource() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
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

    @Override
    public void register(User param, final Register datacallback) {
        interceptor.setResponseString("{\n" +
                "  \"Error\": false, \n" +
                "  \"Message\" : User Added !\" }\n" +
                "}"); //el missatge no és buït si hi ha algun error

        server.register().enqueue(new RetrofitErrorHandler<Boolean>(datacallback) {

            @Override
            public void onResponse(Boolean body) {
                datacallback.onSuccess(body);
            }
        });
    }


}
