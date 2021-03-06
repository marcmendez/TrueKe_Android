package trigues.com.data;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by mbaque on 19/03/2017.
 */

public class FakeInterceptor implements Interceptor {

    private final static String TAG = FakeInterceptor.class.getSimpleName();
    private String RESPONSE_STRING = "";
    private boolean isFake = false;
    private HttpLoggingInterceptor loggingInterceptor;

    public FakeInterceptor() {
        loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if(isFake) {
            this.isFake = false;
            Response response = new Response.Builder()
                    .code(200)
                    .message(RESPONSE_STRING)
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_0)
                    .body(ResponseBody.create(MediaType.parse("application/json"), RESPONSE_STRING.getBytes()))
                    .addHeader("content-type", "application/json")
                    .build();

            return response;
        }
        else{
            return loggingInterceptor.intercept(chain);
        }
    }

    public void setResponseString(String response){
        RESPONSE_STRING = response;
        this.isFake = true;
    }

}