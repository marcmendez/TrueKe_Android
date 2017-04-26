package trigues.com.trueke.utils;

/**
 * Created by Alba on 05/04/2017.
 */

public class ProductChecker {
    public final static void checkTitle(String target) throws Exception {
        if(target.length()<3 || target.length()>50) throw new Exception("Título entre 3 y 50 caracteres");
    }
    public final static void checkDescription(String target) throws Exception {
        if(target.length()<3 || target.length()>150) throw new Exception("Descripcion entre 3 y 150 caracteres");
    }
    public final static void checkCategory(String target) throws Exception {
        if(target.isEmpty()) throw new Exception("Debes introducir una categoria a tu producto");
    }
    public final static void checkPrice(String price1, String price2) throws Exception {
        if(price1.isEmpty() || price2.isEmpty()) throw new Exception("Debe poner un precio mínimo y máximo al producto");
        if(Integer.valueOf(price1)>= Integer.valueOf(price2)) throw new Exception("El precio mínimo debe ser menor al precio máximo");
    }
    public final static void checkImages(String photo1, String photo2, String photo3, String photo4) throws Exception {
        if(photo1.isEmpty() && photo2.isEmpty() && photo3.isEmpty() && photo4.isEmpty())
            throw new Exception("Debes subir al menos una fototografia del producto");
    }
}
