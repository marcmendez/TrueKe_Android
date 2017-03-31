package trigues.com.trueke.view.impl;

import android.util.Patterns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Albert on 29/03/2017.
 */

public class FormatChecker {
    public final static void CheckEmail(String target) throws Exception {
        if (target == null)
            throw new Exception("mail nulo");
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches())
            throw new Exception("Formato mail incorrecto");
    }
    public final static void CheckPhone(String target) throws Exception {
        if (target == null)
            throw new Exception("Teléfono nul");
        if (!Patterns.PHONE.matcher(target).matches())
            throw new Exception("Formato teléfono incorrecto");
    }
    public final static void CheckName(String target) throws Exception { // Nom i Cognom?
        if(target.length()<3 || target.length()>20) throw new Exception("Nombre entre 3 i 20 caracteres");
    }
    public final static void CheckUser(String target) throws Exception { // Nom i Cognom?
        if(target.length()<=0 || target.length()>20) throw new Exception("Nombre mas Apellido(s) máximo 20 caracteres");
    }
    public final static void CheckPassword(String target,String target2) throws Exception {
        if(target.length()<5 || target.length()>20) throw new Exception("Contraseña entre 5 i 20 caracteres");
        if(!target.equals(target2)) throw new Exception("Contraseñas diferentes");
    }
    public final static void CheckDate(String target) throws Exception {
        Date date=null;
        SimpleDateFormat sdf = null;
        try {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(target);
        } catch (ParseException e) {
            throw new Exception("Formato fecha incorrecto");
        }
            if(!target.equals(sdf.format(date))) throw new Exception("Formato fecha incorrecto");
            Date today = new Date();
            if(date.after(today)) throw new Exception("Fecha incorrecta");
    }
}

