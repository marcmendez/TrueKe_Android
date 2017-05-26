package trigues.com.data.datasource.impl;

import android.util.Log;

import com.trigues.entity.Payment;
import com.trigues.entity.Product;
import com.trigues.entity.Shipment;
import com.trigues.entity.User;
import com.trigues.exception.ErrorBundle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import trigues.com.data.FakeInterceptor;
import trigues.com.data.datasource.ApiInterface;
import trigues.com.data.entity.ApiDTO;
import trigues.com.data.entity.CategoryDTO;
import trigues.com.data.entity.ImagePath;
import trigues.com.data.entity.ChatDTO;
import trigues.com.data.entity.LoginDTO;
import trigues.com.data.entity.Password;
import trigues.com.data.entity.ProductDTO;
import trigues.com.data.entity.ProductId;
import trigues.com.data.entity.ProductsMatchDTO;
import trigues.com.data.entity.ReportProductDTO;
import trigues.com.data.entity.UserImage;
import trigues.com.data.entity.UserName;
import trigues.com.data.service.ServerService;
import trigues.com.data.utils.RetrofitErrorHandler;

/**
 * Created by mbaque on 15/03/2017.
 */

public class ApiDataSource implements ApiInterface {

    private ServerService server;
    private FakeInterceptor interceptor;

    private static final String ADMIN_TOKEN = "f4493ed183abba6b096f3903a5fc3b64";


    @Inject
    public ApiDataSource() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        interceptor = new FakeInterceptor();
        builder.addInterceptor(interceptor)
                .connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES);
        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.4.41.147:3000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        server = retrofit.create(ServerService.class);
        //initDatabase();
    }

    public void initDatabase(){

        register(new User(1, "000000000", "test", "1234567", "test@test.com", "1996-09-19", "", 0, 0,0, 0), new BooleanDataCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {

            }

            @Override
            public void onSuccess(Boolean returnParam) {
                login(new User("test@test.com", "1234567"), new LoginDataCallback() {
                    @Override
                    public void onError(ErrorBundle errorBundle) {

                    }

                    @Override
                    public void onSuccess(ApiDTO<LoginDTO> returnParam) {
                        User user = returnParam.getContent().getUser();

                        List<String> desiredCategories = new ArrayList<String>();
                        desiredCategories.add("deporte y ocio");
                        List<String> images = new ArrayList<>();
                        images.add("data:image/jpeg;base64,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");
                        images.add("http://www.monapart.com/sites/default/files/styles/scale_960x960_/public/mediabrowser/botellas-diy-hogar_0.jpg?itok=OLgCxFyv");

                        Product product = new Product(0, user.getId(), "Poupala", "Description", images, "deporte y ocio", desiredCategories, 15, 20);

                        addProduct("f4493ed183abba6b096f3903a5fc3b64", new ProductDTO(product), new AddProductDataCallback() {
                            @Override
                            public void onError(ErrorBundle errorBundle) {

                            }

                            @Override
                            public void onSuccess(ApiDTO<ProductId> returnParam) {

                            }
                        });

                        product.setTitle("Poupala2");

                        addProduct("f4493ed183abba6b096f3903a5fc3b64", new ProductDTO(product), new AddProductDataCallback() {
                            @Override
                            public void onError(ErrorBundle errorBundle) {

                            }

                            @Override
                            public void onSuccess(ApiDTO<ProductId> returnParam) {

                            }
                        });


                        product.setTitle("Poupala3");

                        addProduct("f4493ed183abba6b096f3903a5fc3b64", new ProductDTO(product), new AddProductDataCallback() {
                            @Override
                            public void onError(ErrorBundle errorBundle) {

                            }

                            @Override
                            public void onSuccess(ApiDTO<ProductId> returnParam) {

                            }
                        });


                        product.setTitle("Poupala4");

                        addProduct("f4493ed183abba6b096f3903a5fc3b64", new ProductDTO(product), new AddProductDataCallback() {
                            @Override
                            public void onError(ErrorBundle errorBundle) {

                            }

                            @Override
                            public void onSuccess(ApiDTO<ProductId> returnParam) {

                            }
                        });


                        product.setTitle("Poupala5");

                        addProduct("f4493ed183abba6b096f3903a5fc3b64", new ProductDTO(product), new AddProductDataCallback() {
                            @Override
                            public void onError(ErrorBundle errorBundle) {

                            }

                            @Override
                            public void onSuccess(ApiDTO<ProductId> returnParam) {

                            }
                        });


                        product.setTitle("Poupala6");

                        addProduct("f4493ed183abba6b096f3903a5fc3b64", new ProductDTO(product), new AddProductDataCallback() {
                            @Override
                            public void onError(ErrorBundle errorBundle) {

                            }

                            @Override
                            public void onSuccess(ApiDTO<ProductId> returnParam) {

                            }
                        });


                        product.setTitle("Poupala7");

                        addProduct("f4493ed183abba6b096f3903a5fc3b64", new ProductDTO(product), new AddProductDataCallback() {
                            @Override
                            public void onError(ErrorBundle errorBundle) {

                            }

                            @Override
                            public void onSuccess(ApiDTO<ProductId> returnParam) {

                            }
                        });
                    }
                });
            }
        });

        register(new User(2, "111111111", "test", "1234567", "test@test.org", "1992-09-19", "", 0, 0, 0,0), new BooleanDataCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {

            }

            @Override
            public void onSuccess(Boolean returnParam) {
                login(new User("test@test.org", "1234567"), new LoginDataCallback() {
                    @Override
                    public void onError(ErrorBundle errorBundle) {

                    }

                    @Override
                    public void onSuccess(ApiDTO<LoginDTO> returnParam) {
                        User user = returnParam.getContent().getUser();

                        List<String> desiredCategories = new ArrayList<String>();
                        desiredCategories.add("deporte y ocio");

                        List<String> images = new ArrayList<>();
                        images.add("https://www.google.es/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=0ahUKEwj5tKK_y43UAhVIQBQKHdKbA4IQjRwIBw&url=https%3A%2F%2Fes.pinterest.com%2Fexplore%2Fmochilas-de-lona%2F&psig=AFQjCNGNWTi40Uss9HnuOJB_Ld9cfmbuhA&ust=1495889152891277");
                        images.add("https://www.google.es/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=0ahUKEwjslr_Gy43UAhXCbhQKHQ8LAOsQjRwIBw&url=https%3A%2F%2Fes.pinterest.com%2Fpin%2F7318418124195994%2F&psig=AFQjCNGNWTi40Uss9HnuOJB_Ld9cfmbuhA&ust=1495889152891277");

                        Product product = new Product(0, user.getId(), "Poupala", "Description", images, "deporte y ocio", desiredCategories, 15, 20);

                       addProduct("f4493ed183abba6b096f3903a5fc3b64", new ProductDTO(product), new AddProductDataCallback() {
                           @Override
                           public void onError(ErrorBundle errorBundle) {

                           }

                           @Override
                           public void onSuccess(ApiDTO<ProductId> returnParam) {

                           }
                       });


                        product.setTitle("Poupala2");

                        addProduct("f4493ed183abba6b096f3903a5fc3b64", new ProductDTO(product), new AddProductDataCallback() {
                            @Override
                            public void onError(ErrorBundle errorBundle) {

                            }

                            @Override
                            public void onSuccess(ApiDTO<ProductId> returnParam) {

                            }
                        });


                        product.setTitle("Poupala3");

                        addProduct("f4493ed183abba6b096f3903a5fc3b64", new ProductDTO(product), new AddProductDataCallback() {
                            @Override
                            public void onError(ErrorBundle errorBundle) {

                            }

                            @Override
                            public void onSuccess(ApiDTO<ProductId> returnParam) {

                            }
                        });


                        product.setTitle("Poupala4");

                        addProduct("f4493ed183abba6b096f3903a5fc3b64", new ProductDTO(product), new AddProductDataCallback() {
                            @Override
                            public void onError(ErrorBundle errorBundle) {

                            }

                            @Override
                            public void onSuccess(ApiDTO<ProductId> returnParam) {

                            }
                        });


                        product.setTitle("Poupala5");

                        addProduct("f4493ed183abba6b096f3903a5fc3b64", new ProductDTO(product), new AddProductDataCallback() {
                            @Override
                            public void onError(ErrorBundle errorBundle) {

                            }

                            @Override
                            public void onSuccess(ApiDTO<ProductId> returnParam) {

                            }
                        });


                        product.setTitle("Poupala6");

                        addProduct("f4493ed183abba6b096f3903a5fc3b64", new ProductDTO(product), new AddProductDataCallback() {
                            @Override
                            public void onError(ErrorBundle errorBundle) {

                            }

                            @Override
                            public void onSuccess(ApiDTO<ProductId> returnParam) {

                            }
                        });


                        product.setTitle("Poupala7");

                        addProduct("f4493ed183abba6b096f3903a5fc3b64", new ProductDTO(product), new AddProductDataCallback() {
                            @Override
                            public void onError(ErrorBundle errorBundle) {

                            }

                            @Override
                            public void onSuccess(ApiDTO<ProductId> returnParam) {

                            }
                        });

                    }
                });
            }
        });






    }


    @Override
    public void showProfile(String token, int id, final UserDataCallback userDataCallback){
        server.getUserProfile(token,String.valueOf(id)).enqueue(new RetrofitErrorHandler<ApiDTO<List<User>>>(userDataCallback) {
            @Override
            public void onResponse(ApiDTO<List<User>> body) {
                userDataCallback.onSuccess(body);
            }
        });
    }

    @Override
    public void showPayments(String token,int id, final PaymentsCallback paymentsCallback) {
        /**  interceptor.setResponseString("[{\n" +
         "  \"id\": 1,\n" +
         "  \"user_id\": 1,\n" +
         "  \"type\": \"Visa/4B/Euro6000\",\n" +
         "  \"number\": \"123456789\",\n" +
         "  \"expireDate\": \"1990-05-06\",\n" +
         "  \"name\": \"Sancho Panza\",\n" +
         "  \"country\": \"España\",\n" +
         "  \"province\": \"Barcelona\",\n" +
         "  \"city\": \"Barcelona\",\n" +
         "  \"postalCode\": 8029,\n" +
         "  \"address\": \"Carrer Diagonal\",\n" +
         "  \"phone\": \"654654654\"\n" +
         "},\n" +
         "{\n" +
         "\"id\": 2,\n" +
         "\"user_id\": 1,\n" +
         "\"type\": \"Visa/4B/Euro6000\",\n" +
         "\"number\": \"987654321\",\n" +
         "\"expireDate\": \"1990-05-06\",\n" +
         "\"name\": \"Sancho Panza\",\n" +
         "\"country\": \"España\",\n" +
         "\"province\": \"Barcelona\",\n" +
         "\"city\": \"Barcelona\",\n" +
         "\"postalCode\": 8029,\n" +
         "\"address\": \"Carrer Diagonal\",\n" +
         "\"phone\": \"654654654\"\n" +
         "}]"); **/
        server.getPaymentInfo(token,String.valueOf(id)).enqueue(new RetrofitErrorHandler<ApiDTO<List<Payment>>>(paymentsCallback) {
            @Override
            public void onResponse(ApiDTO<List<Payment>> body) {
                paymentsCallback.onSuccess(body);
            }
        });
    }

    @Override
    public void showShipments(String token,int id, final ShipmentsCallback shipmentsCallback) {
        /**   interceptor.setResponseString("[{\n" +
         "  \"id\": 1,\n" +
         "  \"user_id\": 1,\n" +
         "  \"country\": \"Spain\",\n" +
         "  \"province\": \"Barcelona\",\n" +
         "  \"city\": \"Barcelona\",\n" +
         "  \"postalCode\": 8006,\n" +
         "  \"address\": \"Calle Falsa 123\",\n" +
         "  \"name\": \"Pepito Mendizabal\",\n" +
         "  \"idCard\": \"654845616531\",\n" +
         "  \"phone\": \"654654654\"\n" +
         "}, {\n" +
         "  \"id\": 2,\n" +
         "  \"user_id\": 1,\n" +
         "  \"country\": \"Spain\",\n" +
         "  \"province\": \"Barcelona\",\n" +
         "  \"city\": \"Barcelona\",\n" +
         "  \"postalCode\": 8029,\n" +
         "  \"address\": \"Calle Falsa 123\",\n" +
         "  \"name\": \"Pepito Mendizabal\",\n" +
         "  \"idCard\": \"654845616531\",\n" +
         "  \"phone\": \"654654654\"\n" +
         "}]"); **/

        server.getShipmentInfo(token,String.valueOf(id)).enqueue(new RetrofitErrorHandler<ApiDTO<List<Shipment>>>(shipmentsCallback) {
            @Override
            public void onResponse(ApiDTO<List<Shipment>> body) {
                shipmentsCallback.onSuccess(body);
            }
        });
    }

    @Override
    public void changeProfile(String token, String id,String type, String value, final BooleanDataCallback booleanDataCallback) {
        if(type == "password")
        server.changePassword(token,id,new Password(value)).enqueue(new RetrofitErrorHandler<ApiDTO<Void>>(booleanDataCallback){
            @Override
            public void onResponse(ApiDTO<Void> body) {
                booleanDataCallback.onSuccess(body.getError());
            }
        });
        else if (type == "username")
            server.changeUsername(token,id,new UserName(value)).enqueue(new RetrofitErrorHandler<ApiDTO<Void>>(booleanDataCallback){
                @Override
                public void onResponse(ApiDTO<Void> body) {
                    booleanDataCallback.onSuccess(body.getError());
                }
            });
    }

    @Override
    public void deleteUser(int user_id, final BooleanDataCallback booleanDataCallback) {
        server.deleteUser(user_id).enqueue(new RetrofitErrorHandler<ApiDTO<Void>>(booleanDataCallback){

            @Override
            public void onResponse(ApiDTO<Void> body) {
                booleanDataCallback.onSuccess(false);
            }
        });
    }

    @Override
    public void changePayment(Payment payment, final BooleanDataCallback booleanDataCallback) {
        server.changePayment(payment).enqueue(new RetrofitErrorHandler<ApiDTO<Void>>(booleanDataCallback){

            @Override
            public void onResponse(ApiDTO<Void> body) {
                booleanDataCallback.onSuccess(false);
            }
        });
    }

    @Override
    public void newPayment(String token,Payment payment, final BooleanDataCallback booleanDataCallback) {
        server.newPayment(token,payment).enqueue(new RetrofitErrorHandler<ApiDTO<Void>>(booleanDataCallback){

            @Override
            public void onResponse(ApiDTO<Void> body) {
                booleanDataCallback.onSuccess(body.getError());
            }
        });
    }

    @Override
    public void deletePayment(String token,int payment_id, final BooleanDataCallback booleanDataCallback) {
        server.deletePayment(token,String.valueOf(payment_id)).enqueue(new RetrofitErrorHandler<ApiDTO<Void>>(booleanDataCallback) {
            @Override
            public void onResponse(ApiDTO<Void> body) {
                booleanDataCallback.onSuccess(body.getError());
            }
        });
    }

    @Override
    public void deleteShipment(String token,int shipment_id, final BooleanDataCallback booleanDataCallback) {
        server.deleteShipment(token, String.valueOf(shipment_id)).enqueue(new RetrofitErrorHandler<ApiDTO<Void>>(booleanDataCallback) {
            @Override
            public void onResponse(ApiDTO<Void> body) {
                booleanDataCallback.onSuccess(body.getError());
            }
        });
    }

    @Override
    public void newShipment(String token,Shipment shipment, final BooleanDataCallback booleanDataCallback) {
        server.newShipment(token,shipment).enqueue(new RetrofitErrorHandler<ApiDTO<Void>>(booleanDataCallback){
            @Override
            public void onResponse(ApiDTO<Void> body) {
                booleanDataCallback.onSuccess(body.getError());
            }
        });
    }

    @Override
    public void changeShipment(Shipment shipment, final BooleanDataCallback booleanDataCallback) {
        server.changeShipment(shipment).enqueue(new RetrofitErrorHandler<ApiDTO<Void>>(booleanDataCallback) {
            @Override
            public void onResponse(ApiDTO<Void> body) {
                booleanDataCallback.onSuccess(false);
            }
        });
    }

    @Override
    public void getMatchmakingProducts(String token, int prodID, final ProductListDataCallback dataCallback) {
        server.getMatchMakingProducts(token, prodID).enqueue(new RetrofitErrorHandler< ApiDTO<List<Product>> /*List<Product> */>(dataCallback) {
            @Override
            public void onResponse(ApiDTO<List<Product>>/*List<Product>*/ body) {
                dataCallback.onSuccess(body);
            }
        });

    }

    @Override
    public void acceptMatch(String token, Integer[] productsID, final VoidDataCallback voidDataCallback) {
        ProductsMatchDTO dto = new ProductsMatchDTO(productsID[0], productsID[1], 1);
        server.acceptMatch(token, dto).enqueue(new RetrofitErrorHandler<ApiDTO<Void>>(voidDataCallback) {

            @Override
            public void onResponse(ApiDTO<Void> body) {
                voidDataCallback.onSuccess(null);
            }
        });
    }

    @Override
    public void rejectMatch(String token, Integer[] productsID, final VoidDataCallback voidDataCallback) {
        ProductsMatchDTO dto = new ProductsMatchDTO(productsID[0], productsID[1], 0);
        server.rejectMatch(token, dto).enqueue(new RetrofitErrorHandler<ApiDTO<Void>>(voidDataCallback) {

            @Override
            public void onResponse(ApiDTO<Void> body) {
                voidDataCallback.onSuccess(null);
            }
        });
    }

    @Override
    public void reportProduct(String token, Integer[] userProdID, final VoidDataCallback voidDataCallback) {
        ReportProductDTO dto = new ReportProductDTO(userProdID[0], userProdID[1]);
        server.reportProduct(token, dto).enqueue(new RetrofitErrorHandler<ApiDTO<Void>>(voidDataCallback) {

            @Override
            public void onResponse(ApiDTO<Void> body) {
                voidDataCallback.onSuccess(null);
            }
        });
    }



    @Override
    public void showProducts(String token, int userID, final ProductListDataCallback dataCallback) {
        /*List<String> llista = new ArrayList<>();
        llista.add("https://photos6.spartoo.es/photos/231/231523/231523_350_A.jpg");
        List<String> llista2 = new ArrayList<>();
                llista2.add("Electrodomesticos");
        Product prod = new Product(12345, 54321, "Mochilla", "Mochila basura",llista ,
                "Accesorios", llista2, 100, 200);
        List<Product> llistaProd= new ArrayList<>();
        llistaProd.add(prod);
        llistaProd.add(prod);
        llistaProd.add(prod);
        llistaProd.add(prod);
        Gson gson = new Gson();
        interceptor.setResponseString(gson.toJson(llistaProd));*/

        server.getUserProducts(token, userID).enqueue(new RetrofitErrorHandler< ApiDTO<List<Product>> /*List<Product> */>(dataCallback) {
            @Override
            public void onResponse(ApiDTO<List<Product>>/*List<Product>*/ body) {
                dataCallback.onSuccess(body);
            }
        });

    }

    @Override
    public void register(User user, final BooleanDataCallback dataCallback) {
        server.register(user).enqueue(new RetrofitErrorHandler<ApiDTO<Void>>(dataCallback) {
            @Override
            public void onResponse(ApiDTO<Void> body) {
                dataCallback.onSuccess(body.getError());
            }
        });
    }

    @Override
    public void login(User user, final LoginDataCallback dataCallback) {
        server.login(user).enqueue(new RetrofitErrorHandler<ApiDTO<LoginDTO>>(dataCallback) {
            @Override
            public void onResponse(ApiDTO<LoginDTO> body) {
                dataCallback.onSuccess(body);
            }
        });
    }

    @Override
    public void addProduct(String token, ProductDTO product, final AddProductDataCallback dataCallback) {
        server.addProduct(token, product).enqueue(new RetrofitErrorHandler<ApiDTO<ProductId>>(dataCallback) {
            @Override
            public void onResponse(ApiDTO<ProductId> body) {
                dataCallback.onSuccess(body);
            }
        });
    }

    @Override
    public void deleteProduct(String token, int product_id, final BooleanDataCallback dataCallback) {
        server.deleteProduct(token, String.valueOf(product_id)).enqueue(new RetrofitErrorHandler<ApiDTO<Void>>(dataCallback) {
            @Override
            public void onResponse(ApiDTO<Void> body) {
                dataCallback.onSuccess(false);
            }
        });
    }

    @Override
    public void addProductCategory(String token,List<String> category1, final BooleanDataCallback dataCallback) {

        int product_id = Integer.parseInt(category1.get(1));
        String category = category1.get(0);
        CategoryDTO desiredCat = new CategoryDTO();
        desiredCat.setProduct_id(product_id);
        desiredCat.setCategory(category);

        server.addProductCategory(token, desiredCat).enqueue(new RetrofitErrorHandler<ApiDTO<Void>>(dataCallback) {
            @Override
            public void onResponse(ApiDTO<Void> body) {
                dataCallback.onSuccess(false);
            }
        });
    }

    @Override
    public void deleteProductCategory(String token,List<String> category1, final BooleanDataCallback dataCallback) {
        int product_id = Integer.parseInt(category1.get(1));
        String category = category1.get(0);
        CategoryDTO desiredCat = new CategoryDTO();
        desiredCat.setProduct_id(product_id);
        desiredCat.setCategory(category);

        server.deleteProductCategory(token, desiredCat).enqueue(new RetrofitErrorHandler<ApiDTO<Void>>(dataCallback) {
            @Override
            public void onResponse(ApiDTO<Void> body) {
                dataCallback.onSuccess(false);
            }
        });
    }

    @Override
    public void getDesiredCategories(String token, int productID, final StringListDataCallback dataCallback) {

        server.getDesiredCategories(token,productID).enqueue(new RetrofitErrorHandler<ApiDTO<List<CategoryDTO>>>(dataCallback) {
            @Override
            public void onResponse(ApiDTO<List<CategoryDTO>> body) {
                dataCallback.onSuccess(body);
            }
        });
    }

    @Override
    public void addImagesProduct(String token, int product_id, String image_md5, final BooleanDataCallback dataCallback) {
        Log.i("image_md5", "server returnParam: "+image_md5);
        Log.i("image_md5", "server returnParam product: "+product_id);
        server.addImagesProduct(token, product_id, image_md5).enqueue(new RetrofitErrorHandler<ApiDTO<Void>>(dataCallback) {
            @Override
            public void onResponse(ApiDTO<Void> body) {
                dataCallback.onSuccess(false);
            }
        });
    }

    @Override
    public void addImages(String image, final ImagesDataCallback dataCallback) {
        server.addImages(image).enqueue(new RetrofitErrorHandler<ApiDTO<String>>(dataCallback) {
            @Override
            public void onResponse(ApiDTO<String> body) {
                dataCallback.onSuccess(body);
            }
        });
    }

    @Override
    public void getImagesProduct(int product_id, final GetImagesProductDataCallback dataCallback) {
        server.getImagesProduct(product_id).enqueue(new RetrofitErrorHandler<ApiDTO<List<ImagePath>>>(dataCallback) {
            @Override
            public void onResponse(ApiDTO<List<ImagePath>> body) {
                List<String> returnparam = new ArrayList();
                for(ImagePath b : body.getContent()){
                    returnparam.add(b.getImagePath());
                }
                ApiDTO<List<String>> bod = new ApiDTO(body.getError(),body.getMessage(),returnparam);
                dataCallback.onSuccess(bod);
            }
        });
    }

    @Override
    public void getImages(String md5, final ImagesDataCallback dataCallback) {
        Log.i("imagepath", "getImages: "+md5);
        server.getImages(md5).enqueue(new RetrofitErrorHandler<ApiDTO<String>>(dataCallback) {
            @Override
            public void onResponse(ApiDTO<String> body) {
                dataCallback.onSuccess(body);
            }
        });
    }

    @Override
    public void changeUserImageProfile(String token, String id, String image_path, final BooleanDataCallback booleanDataCallback) {
        server.changeProfileUserImage(token,id,new UserImage(image_path)).enqueue(new RetrofitErrorHandler<ApiDTO<Void>>(booleanDataCallback) {
            @Override
            public void onResponse(ApiDTO<Void> body) {
                booleanDataCallback.onSuccess(body.getError());
            }
        });
    }

    @Override
    public void getUserChats(String token, int userID, final ChatListDataCallback dataCallback) {

            server.getUserChats(token,userID).enqueue(new RetrofitErrorHandler<ApiDTO<List<ChatDTO>>>(dataCallback) {
            @Override
            public void onResponse(ApiDTO<List<ChatDTO>> body) {
                dataCallback.onSuccess(body);
            }
        });
    }

    @Override
    public void getProductInfo(int producID, final ProductDataCallback dataCallback) {

        server.getProductInfo(producID).enqueue(new RetrofitErrorHandler<ApiDTO<ProductDTO>>(dataCallback) {
            @Override
            public void onResponse(ApiDTO<ProductDTO> body) {

                dataCallback.onSuccess(body);
            }
        });
    }


    @Override
    public void createTrueke(String chatID, String admintoken, VoidDataCallback voidDataCallback) {
        server.createTrueke(admintoken,chatID);
    }
}
