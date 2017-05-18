package trigues.com.data.datasource.impl;

import com.google.gson.Gson;
import com.trigues.RepositoryInterface;
import com.trigues.entity.ChatInfo;
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
import trigues.com.data.entity.ChatDTO;
import trigues.com.data.entity.LoginDTO;
import trigues.com.data.entity.Password;
import trigues.com.data.entity.ProductDTO;
import trigues.com.data.entity.ProductsMatchDTO;
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
        initDatabase();
    }

    public void initDatabase(){

        register(new User(1, "000000000", "test", "1234567", "test@test.com", "1996-09-19", 0, 0, 0.0F), new BooleanDataCallback() {
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
                        desiredCategories.add("Deporte y ocio");

                        Product product = new Product(0, user.getId(), "Poupala", "Description", null, "Deporte y ocio", desiredCategories, 15, 20);

                        addProduct("f4493ed183abba6b096f3903a5fc3b64", new ProductDTO(product), new BooleanDataCallback() {
                            @Override
                            public void onError(ErrorBundle errorBundle) {

                            }

                            @Override
                            public void onSuccess(Boolean returnParam) {

                            }
                        });

                        product.setTitle("Poupala2");

                        addProduct("f4493ed183abba6b096f3903a5fc3b64", new ProductDTO(product), new BooleanDataCallback() {
                            @Override
                            public void onError(ErrorBundle errorBundle) {

                            }

                            @Override
                            public void onSuccess(Boolean returnParam) {

                            }
                        });

                        product.setTitle("Poupala3");

                        addProduct("f4493ed183abba6b096f3903a5fc3b64", new ProductDTO(product), new BooleanDataCallback() {
                            @Override
                            public void onError(ErrorBundle errorBundle) {

                            }

                            @Override
                            public void onSuccess(Boolean returnParam) {

                            }
                        });

                        product.setTitle("Poupala4");

                        addProduct("f4493ed183abba6b096f3903a5fc3b64", new ProductDTO(product), new BooleanDataCallback() {
                            @Override
                            public void onError(ErrorBundle errorBundle) {

                            }

                            @Override
                            public void onSuccess(Boolean returnParam) {

                            }
                        });

                        product.setTitle("Poupala5");

                        addProduct("f4493ed183abba6b096f3903a5fc3b64", new ProductDTO(product), new BooleanDataCallback() {
                            @Override
                            public void onError(ErrorBundle errorBundle) {

                            }

                            @Override
                            public void onSuccess(Boolean returnParam) {

                            }
                        });

                        product.setTitle("Poupala6");

                        addProduct("f4493ed183abba6b096f3903a5fc3b64", new ProductDTO(product), new BooleanDataCallback() {
                            @Override
                            public void onError(ErrorBundle errorBundle) {

                            }

                            @Override
                            public void onSuccess(Boolean returnParam) {

                            }
                        });

                        product.setTitle("Poupala7");

                        addProduct("f4493ed183abba6b096f3903a5fc3b64", new ProductDTO(product), new BooleanDataCallback() {
                            @Override
                            public void onError(ErrorBundle errorBundle) {

                            }

                            @Override
                            public void onSuccess(Boolean returnParam) {

                            }
                        });



                    }
                });
            }
        });

        register(new User(2, "111111111", "test", "1234567", "test@test.org", "1992-09-19", 0, 0, 0.0F), new BooleanDataCallback() {
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
                        desiredCategories.add("Deporte y ocio");

                        Product product = new Product(0, user.getId(), "Poupala", "Description", null, "Deporte y ocio", desiredCategories, 15, 20);

                        addProduct("f4493ed183abba6b096f3903a5fc3b64", new ProductDTO(product), new BooleanDataCallback() {
                            @Override
                            public void onError(ErrorBundle errorBundle) {

                            }

                            @Override
                            public void onSuccess(Boolean returnParam) {

                            }
                        });

                        product.setTitle("Poupala2");

                        addProduct("f4493ed183abba6b096f3903a5fc3b64", new ProductDTO(product), new BooleanDataCallback() {
                            @Override
                            public void onError(ErrorBundle errorBundle) {

                            }

                            @Override
                            public void onSuccess(Boolean returnParam) {

                            }
                        });

                        product.setTitle("Poupala3");

                        addProduct("f4493ed183abba6b096f3903a5fc3b64", new ProductDTO(product), new BooleanDataCallback() {
                            @Override
                            public void onError(ErrorBundle errorBundle) {

                            }

                            @Override
                            public void onSuccess(Boolean returnParam) {

                            }
                        });

                        product.setTitle("Poupala4");

                        addProduct("f4493ed183abba6b096f3903a5fc3b64", new ProductDTO(product), new BooleanDataCallback() {
                            @Override
                            public void onError(ErrorBundle errorBundle) {

                            }

                            @Override
                            public void onSuccess(Boolean returnParam) {

                            }
                        });

                        product.setTitle("Poupala5");

                        addProduct("f4493ed183abba6b096f3903a5fc3b64", new ProductDTO(product), new BooleanDataCallback() {
                            @Override
                            public void onError(ErrorBundle errorBundle) {

                            }

                            @Override
                            public void onSuccess(Boolean returnParam) {

                            }
                        });

                        product.setTitle("Poupala6");

                        addProduct("f4493ed183abba6b096f3903a5fc3b64", new ProductDTO(product), new BooleanDataCallback() {
                            @Override
                            public void onError(ErrorBundle errorBundle) {

                            }

                            @Override
                            public void onSuccess(Boolean returnParam) {

                            }
                        });

                        product.setTitle("Poupala7");

                        addProduct("f4493ed183abba6b096f3903a5fc3b64", new ProductDTO(product), new BooleanDataCallback() {
                            @Override
                            public void onError(ErrorBundle errorBundle) {

                            }

                            @Override
                            public void onSuccess(Boolean returnParam) {

                            }
                        });



                    }
                });
            }
        });






    }

    @Override
    public void getUserProductDetails(String token, int productId, final ProductDataCallback dataCallback) {

        server.getUserProductDetails(token, productId).enqueue(new RetrofitErrorHandler<Product>(dataCallback) {
            @Override
            public void onResponse(Product body) {
                dataCallback.onSuccess(body);
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
                booleanDataCallback.onSuccess(false);
            }
        });
        else if (type == "username")
            server.changeUsername(token,id,new UserName(value)).enqueue(new RetrofitErrorHandler<ApiDTO<Void>>(booleanDataCallback){
                @Override
                public void onResponse(ApiDTO<Void> body) {
                    booleanDataCallback.onSuccess(false);
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
                booleanDataCallback.onSuccess(false);
            }
        });
    }

    @Override
    public void deletePayment(String token,int payment_id, final BooleanDataCallback booleanDataCallback) {
        server.deletePayment(token,String.valueOf(payment_id)).enqueue(new RetrofitErrorHandler<ApiDTO<Void>>(booleanDataCallback) {
            @Override
            public void onResponse(ApiDTO<Void> body) {
                booleanDataCallback.onSuccess(false);
            }
        });
    }

    @Override
    public void deleteShipment(String token,int shipment_id, final BooleanDataCallback booleanDataCallback) {
        server.deleteShipment(token, String.valueOf(shipment_id)).enqueue(new RetrofitErrorHandler<ApiDTO<Void>>(booleanDataCallback) {
            @Override
            public void onResponse(ApiDTO<Void> body) {
                booleanDataCallback.onSuccess(false);
            }
        });
    }

    @Override
    public void newShipment(String token,Shipment shipment, final BooleanDataCallback booleanDataCallback) {
        server.newShipment(token,shipment).enqueue(new RetrofitErrorHandler<ApiDTO<Void>>(booleanDataCallback){
            @Override
            public void onResponse(ApiDTO<Void> body) {
                booleanDataCallback.onSuccess(false);
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
    public void addProduct(String token, ProductDTO product, final BooleanDataCallback dataCallback) {
        server.addProduct(token, product).enqueue(new RetrofitErrorHandler<ApiDTO<Void>>(dataCallback) {
            @Override
            public void onResponse(ApiDTO<Void> body) {
                dataCallback.onSuccess(body.getError());
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
    public void getUserChats(String token, int userID, final ChatListDataCallback dataCallback) {

            server.getUserChats(token,userID).enqueue(new RetrofitErrorHandler<ApiDTO<List<ChatDTO>>>(dataCallback) {
            @Override
            public void onResponse(ApiDTO<List<ChatDTO>> body) {
                dataCallback.onSuccess(body);
            }
        });
    }
}
