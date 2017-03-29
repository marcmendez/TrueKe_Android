package trigues.com.trueke.view.impl;

import android.util.Patterns;

/**
 * Created by Albert on 29/03/2017.
 */

public class FormatChecker {
    public final static boolean isValidEmail(String target) {
        if (target == null)
            return false;

        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    public final static boolean isValidPhone(String target) {
        if (target == null)
            return false;

        return Patterns.PHONE.matcher(target).matches();
    }
    public final static boolean isValidUser(String target){
        return true;
    }
}

