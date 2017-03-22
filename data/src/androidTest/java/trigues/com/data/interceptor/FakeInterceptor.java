package trigues.com.data.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;
import trigues.com.data.test.BuildConfig;

/**
 * Created by mbaque on 19/03/2017.
 */

public class FakeInterceptor implements Interceptor {

    private final static String TAG = FakeInterceptor.class.getSimpleName();
    private String RESPONSE_STRING = "";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response;
        if(BuildConfig.DEBUG) {
            response = new Response.Builder()
                    .code(200)
                    .message(RESPONSE_STRING)
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_0)
                    .body(ResponseBody.create(MediaType.parse("application/json"), RESPONSE_STRING.getBytes()))
                    .addHeader("content-type", "application/json")
                    .build();
        }
        else {
            response = chain.proceed(chain.request());
        }

        return response;
    }

    public void setResponseString(String response){
        RESPONSE_STRING = response;
    }
}