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
    public final static boolean isValidUser(String target){ // Nom i Cognom?
        return true;
    }
    public final static boolean isValidPassword(String target){
        return target.length()>=5 && target.length()<=20;
    }
    public final static boolean isValidDate(String target){
        return true; //fem Date o String per a data naixement?
    }
}

