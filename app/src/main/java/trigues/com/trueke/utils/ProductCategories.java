package trigues.com.trueke.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mbaque on 19/04/2017.
 */

public class ProductCategories {

    public static final String VEHICULOS = "Vehiculos";
    public static final String ELECTRONICA = "Electronico";
    public static final String DEPORTE_Y_OCIO = "Deporte y ocio";
    public static final String HOGAR_Y_ELECTRODOMESTICOS = "Hogar y electrodomesticos";
    public static final String CONSOLAS_Y_VIDEOJUEGOS = "Consolas y videojuegos";
    public static final String LIBROS_PELICULAS_MUSICA = "Libros, películas o música";
    public static final String MODA_Y_ACCESORIOS = "Moda y accesorios";
    public static final String OTROS = "Otros";

    public static List<String> getAllCategories(){
        List<String> categories = new ArrayList<>();
        categories.add(VEHICULOS);
        categories.add(ELECTRONICA);
        categories.add(DEPORTE_Y_OCIO);
        categories.add(HOGAR_Y_ELECTRODOMESTICOS);
        categories.add(CONSOLAS_Y_VIDEOJUEGOS);
        categories.add(LIBROS_PELICULAS_MUSICA);
        categories.add(MODA_Y_ACCESORIOS);
        categories.add(OTROS);
        return categories;
    }




}
