package trigues.com.trueke.utils;

/**
 * Created by Alba on 05/04/2017.
 */

public class ProductChecker {
    public final static void checkTitle(String target) throws Exception {
        if(target.length()<3 || target.length()>50) throw new Exception("Titulo entre 3 y 50 caracteres");
    }
    public final static void checkDescription(String target) throws Exception {
        if(target.length()<3 || target.length()>150) throw new Exception("Descripcion entre 3 y 150 caracteres");
    }
    public final static void checkPrice(String price1, String price2) throws Exception {
        if(Integer.valueOf(price1)>= Integer.valueOf(price2)) throw new Exception("El precio minimo debe ser menor al precio maximo");
        if (price1.isEmpty() || price2.isEmpty()) throw new Exception("Debe poner un precio minimo y maximo al producto");
    }
    public final static void checkCategory(String target) throws Exception {
        if(target.length()<3 || target.length()>50) throw new Exception("Categoria entre 3 y 50 caracteres");
    }
}
