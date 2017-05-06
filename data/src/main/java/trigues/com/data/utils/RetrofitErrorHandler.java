package trigues.com.data.utils;

import com.trigues.callback.DefaultCallback;
import com.trigues.exception.ErrorBundle;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mbaque on 19/03/2017.
 */

public abstract class RetrofitErrorHandler<ReturnType> implements Callback<ReturnType> {

    DefaultCallback callback;

    public RetrofitErrorHandler(DefaultCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onResponse(Call<ReturnType> call, final Response<ReturnType> response) {
        //Not successfull
        if(!response.isSuccessful()){
            callback.onError(new ErrorBundle() {
                @Override
                public Exception getException() {
                    return new Exception();
                }

                @Override
                public String getErrorMessage() {
                    return "Se ha producido un error con el servidor";
                }
            });
        }
        else onResponse(response.body());
    }

    public abstract void onResponse(ReturnType body);

    @Override
    public void onFailure(Call<ReturnType> call, final Throwable t) {
        if(t instanceof IOException){
            callback.onError(new ErrorBundle() {
                @Override
                public Exception getException() {
                    return (IOException) t;
                }

                @Override
                public String getErrorMessage() {
                    return "No hay conexión a internet";
                }
            });
        }
        else{
            callback.onError(new ErrorBundle() {
                @Override
                public Exception getException() {
                    if(t instanceof Exception) {
                        return (Exception) t;
                    }
                    else return new Exception(t);
                }

                @Override
                public String getErrorMessage() {
                    return "Se ha producido un error desconocido";
                }
            });
        }
    }
}