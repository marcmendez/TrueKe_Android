package trigues.com.trueke.utils;

import android.util.Patterns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

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
        if(target.length()<3 || target.length()>30) throw new Exception("Nombre entre 3 i 30 caracteres");
    }
    public final static void CheckUser(String target) throws Exception { // Nom i Cognom?
        if(target.length()<=0 || target.length()>30) throw new Exception("Nombre mas Apellido(s) máximo 30 caracteres");
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

    public static void CheckPasswordActual(String target, String target2) throws Exception {
        if(!target.equals(target2)) throw new Exception("Esta no es tu contraseña actual!");
    }
    public final static void CheckPlace(String target) throws Exception { // Provincia, Ciudad
        if(target.length()<=2 || target.length()>20) throw new Exception("La província y la ciudad entre 3 y 30 caracteres");
    }
    public final static void CheckPostalCode(String target) throws Exception {
        if(target.length()!=5) throw new Exception("El código postal es de 5 dígitos");
    }
    public final static void CheckExpirationDate(String target) throws Exception {
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
        if(date.before(today)) throw new Exception("Tarjeta caducada");
    }
    public final static void CheckCard(String target) throws Exception {
        if(target.length()<=14 || target.length()>=17) throw new Exception("Tarjeta de crédito inválida");
    }
    public final static void CheckDNI(String target) throws Exception{
        String snum = target.substring(0,target.length()-1);
        if (Pattern.matches("[a-zA-Z]+", snum)) throw new Exception ("DNI inválido");
        char[] letraDni = {
                'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D',  'X',  'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'
        };
        int num = Integer.parseInt(snum) % 23;
        if (target.charAt(target.length()-1) != letraDni[num]) throw new Exception ("DNI inválido");
    }

    public final static void CheckDirecció(String target) throws Exception {
        if(target.length()==0) throw new Exception("No has llenado todos los campos");
        if(target.length()<5) throw new Exception("Dirección mínimo 5 caracteres");
        if(target.length()>40) throw new Exception ("Dirección demasiado larga");
    }


}

