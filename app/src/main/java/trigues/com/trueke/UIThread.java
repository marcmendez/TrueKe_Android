package trigues.com.trueke;

import android.os.Handler;
import android.os.Looper;

import com.trigues.executor.PostExecutionThread;

/**
 * Created by mbaque on 15/03/2017.
 */

public class UIThread implements PostExecutionThread {

    private final Handler handler;

    public UIThread() {
        this.handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void post(Runnable runnable) {
        handler.post(runnable);
    }

}
