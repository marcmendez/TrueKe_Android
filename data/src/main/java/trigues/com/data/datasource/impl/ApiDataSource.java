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
import trigues.com.data.entity.ChatDTO;
import trigues.com.data.entity.ImagePath;
import trigues.com.data.entity.LoginDTO;
import trigues.com.data.entity.Password;
import trigues.com.data.entity.ProductDTO;
import trigues.com.data.entity.ProductId;
import trigues.com.data.entity.ProductsMatchDTO;
import trigues.com.data.entity.ReportProductDTO;
import trigues.com.data.entity.TruekeChat;
import trigues.com.data.entity.UserImage;
import trigues.com.data.entity.UserName;
import trigues.com.data.entity.VoteDTO;
import trigues.com.data.service.ServerService;
import trigues.com.data.utils.RetrofitErrorHandler;

/**
 * Created by mbaque on 15/03/2017.
 */

public class ApiDataSource implements ApiInterface {

    private ServerService server;
    private FakeInterceptor interceptor;
    private InternalStorageDataSource internalStorage;

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
                        final List<String> images = new ArrayList<>();
                        images.add("data:image/jpeg;base64,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");

                        Product product = new Product(0, user.getId(), "Llapis", "Description", images, "deporte y ocio", desiredCategories, 15, 20);

//                        addProduct("f4493ed183abba6b096f3903a5fc3b64", new ProductDTO(product), new AddProductDataCallback() {
//                            @Override
//                            public void onError(ErrorBundle errorBundle) {
//
//                            }
//
//                            @Override
//                            public void onSuccess(ApiDTO<ProductId> returnParam) {
//                                addImagesProduct("f4493ed183abba6b096f3903a5fc3b64", returnParam.getContent().getProductId(), images.get(0), new BooleanDataCallback() {
//                                    @Override
//                                    public void onError(ErrorBundle errorBundle) {
//
//                                    }
//
//                                    @Override
//                                    public void onSuccess(Boolean returnParam) {
//
//                                    }
//                                });
//                            }
//                        });
//
//                        product.setTitle("Poupala2");
//
//                        addProduct("f4493ed183abba6b096f3903a5fc3b64", new ProductDTO(product), new AddProductDataCallback() {
//                            @Override
//                            public void onError(ErrorBundle errorBundle) {
//
//                            }
//
//                            @Override
//                            public void onSuccess(ApiDTO<ProductId> returnParam) {
//                                addImagesProduct("f4493ed183abba6b096f3903a5fc3b64", returnParam.getContent().getProductId(), images.get(0), new BooleanDataCallback() {
//                                    @Override
//                                    public void onError(ErrorBundle errorBundle) {
//
//                                    }
//
//                                    @Override
//                                    public void onSuccess(Boolean returnParam) {
//
//                                    }
//                                });
//                            }
//                        });
//
//
//                        product.setTitle("Poupala3");
//
//                        addProduct("f4493ed183abba6b096f3903a5fc3b64", new ProductDTO(product), new AddProductDataCallback() {
//                            @Override
//                            public void onError(ErrorBundle errorBundle) {
//
//                            }
//
//                            @Override
//                            public void onSuccess(ApiDTO<ProductId> returnParam) {
//                                addImagesProduct("f4493ed183abba6b096f3903a5fc3b64", returnParam.getContent().getProductId(), images.get(0), new BooleanDataCallback() {
//                                    @Override
//                                    public void onError(ErrorBundle errorBundle) {
//
//                                    }
//
//                                    @Override
//                                    public void onSuccess(Boolean returnParam) {
//
//                                    }
//                                });
//                            }
//                        });
//
//
//                        product.setTitle("Poupala4");
//
//                        addProduct("f4493ed183abba6b096f3903a5fc3b64", new ProductDTO(product), new AddProductDataCallback() {
//                            @Override
//                            public void onError(ErrorBundle errorBundle) {
//
//                            }
//
//                            @Override
//                            public void onSuccess(ApiDTO<ProductId> returnParam) {
//                                addImagesProduct("f4493ed183abba6b096f3903a5fc3b64", returnParam.getContent().getProductId(), images.get(0), new BooleanDataCallback() {
//                                    @Override
//                                    public void onError(ErrorBundle errorBundle) {
//
//                                    }
//
//                                    @Override
//                                    public void onSuccess(Boolean returnParam) {
//
//                                    }
//                                });
//                            }
//                        });
//
//
//                        product.setTitle("Poupala5");
//
//                        addProduct("f4493ed183abba6b096f3903a5fc3b64", new ProductDTO(product), new AddProductDataCallback() {
//                            @Override
//                            public void onError(ErrorBundle errorBundle) {
//
//                            }
//
//                            @Override
//                            public void onSuccess(ApiDTO<ProductId> returnParam) {
//                                addImagesProduct("f4493ed183abba6b096f3903a5fc3b64", returnParam.getContent().getProductId(), images.get(0), new BooleanDataCallback() {
//                                    @Override
//                                    public void onError(ErrorBundle errorBundle) {
//
//                                    }
//
//                                    @Override
//                                    public void onSuccess(Boolean returnParam) {
//
//                                    }
//                                });
//                            }
//                        });
//
//
//                        product.setTitle("Poupala6");
//
//                        addProduct("f4493ed183abba6b096f3903a5fc3b64", new ProductDTO(product), new AddProductDataCallback() {
//                            @Override
//                            public void onError(ErrorBundle errorBundle) {
//
//                            }
//
//                            @Override
//                            public void onSuccess(ApiDTO<ProductId> returnParam) {
//                                addImagesProduct("f4493ed183abba6b096f3903a5fc3b64", returnParam.getContent().getProductId(), images.get(0), new BooleanDataCallback() {
//                                    @Override
//                                    public void onError(ErrorBundle errorBundle) {
//
//                                    }
//
//                                    @Override
//                                    public void onSuccess(Boolean returnParam) {
//
//                                    }
//                                });
//                            }
//                        });
//
//
//                        product.setTitle("Poupala7");
//
//                        addProduct("f4493ed183abba6b096f3903a5fc3b64", new ProductDTO(product), new AddProductDataCallback() {
//                            @Override
//                            public void onError(ErrorBundle errorBundle) {
//
//                            }
//
//                            @Override
//                            public void onSuccess(ApiDTO<ProductId> returnParam) {
//                                addImagesProduct("f4493ed183abba6b096f3903a5fc3b64", returnParam.getContent().getProductId(), images.get(0), new BooleanDataCallback() {
//                                    @Override
//                                    public void onError(ErrorBundle errorBundle) {
//
//                                    }
//
//                                    @Override
//                                    public void onSuccess(Boolean returnParam) {
//
//                                    }
//                                });
//                            }
//                        });
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
                        final List<String> images = new ArrayList<>();
                        images.add("data:image/jpeg;base64,/9j/4AAQSkZJRgABAgAAAQABAAD/2wBDAAUDBAQEAwUEBAQFBQUGBwwIBwcHBw8KCwkMEQ8SEhEPERATFhwXExQaFRARGCEYGhwdHx8fExciJCIeJBweHx7/2wBDAQUFBQcGBw4ICA4eFBEUHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh7/wAARCABkAGQDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD50jkLwhvXkVXlm6jmmW8oWJlY9OlX9C0aTVJBNLIIbcsQCTy/qAO9XJ21ZzJX0INOs7rUbgQWkTO3r2Feg+GfDdlpZSafbc3fqfuqas6Xa2en2ywWsXlrjk45arcs0cEDTSt5caDLMewrJwdT4tiJ1lD4N+/+RpeZkgucnipPtUEZwXGcdK8g8UeNb64d7fSN8EQOPNP3m+npXPwXPiMt5y314p9WmbmtNIoiOGqT1Z9A/bIupZcelRy3duVwGXNeNaf4h1q1UC8DXMfc5w3511Gl3serWxksZ2JH30J+ZfqKhyvsKVCUNzrLq/tkUhTufsB2+tZNxfAnBO9zzj0qgbO5AxtwM96b9jnAwAR796hEpJEk0+Xy7ZPfHaojI75A4FOW1ZTkgk1N5LsoXovUj1NN3KK6RsRldx56g4oq4tu+0chfY0VItTsP2b/hZ4b+J+k6nZajLLZ3lhcLKJ4hlpImBBU546jrXLax4Cfw54rv7OO43PZXUsEZkTcBtYjPX2r1L9hK58nxdrVoxwZ7MED12t/9etT9oDSTpvxQvZEiPkXsaXI44DH5Wx+K5P1rpqSadjoknbQ8ha01RYy/2q2bH/TI/wCNY+r22qX8g015FMbr5j+WpHy5+tds1txwvy+mOKj8IQxXF9re5BvVkVD6KAf61zVptU20aYGip10pLQ8/XRYLRNqQqp9cZJpJrWMAgoM1u61dacmoPCtyruDztORWfqu8RItooaST7rdq8hTm3qfWulSUfd6GN9mj3j5cVz91JdaDrBubUlAxyMdCO4rom0ydSj3WrKs7HhVxj/69L4j08zaMS4V5oxkFe9ddCpyT3PMxNH2kHpsXrHWtRu7SO7ithLG/Ax+tWYtRvpHEY092bp8ozWX8Mrj/AEG6tn5COGUE9ARz/Ku6S5jtrAyOBuweO59BXpch8zNcsrWOYk1hYpminhaN1OGGM4P4VYt9VsH+9OF74YYqvYJ/pM11MhkkkYkRgZJ/Ctvw78P/ABh4k1EPD4d1FbYfMD9mYB/bJAFN05FIijurNlytzGR7NRXqel/BHxmbNSNFjhz/AAyOgNFRysLPse3+Dfgz4f8AB3jlPEnhu6urOHyXjksmPmK27HRjyBx3zXTfELwZpvjHSxbXWILqI5t7lVy0Z7j3B7iuo7UlJzk3dnZyo+avFXwk8QaU5aztv7RhzndB1H1U15nZaFe2mva/pt3FPZyTWquokQowUkAkfqM19xV4t8f9Lsv+Ei0rUVmhjvLi2ktZEJwzr95T+Bz+dZ1EnBm+G92qj5G1m2i07UBHpmmAxqxUySJndj68n61F4luXV7cqvlsIgGCjgGu28R2zRXzMzFgrcA9q5nxJq9kxuoVjgSWFR5fG4kY5yOMH8a8aMryWl7H1LpKMG72TOZt7eKQq00IlODhj7/zrTsbV2+QZZf7p9PSs7SdesJpSjxiIMeABxn1rpkkgtbZ5sgjGV962m5J2aOSMIOPMncxvCtrBptxNcYLgyFXTjGA3evuDwV8J/hwdGsNUXwzHcPcW6S/6a7zEbgD91jt/Svj34XeEte8ZXyRaVaTzj7QfOZU+VAx6s3bvX6E2MH2exgt+P3Uapx7DFehRlUV22eRjI0uWPKlcpaX4e0HS126bounWQ/6YWyR/yFaQAAwAAPalorVtvc4kktgooopAKOlJSjpSUwFHavKv2jfD0974eg1/T4i13prHODzsP/1wK9WHUU2aKOaF4pUWSNwVZWGQQexFE4KcWmbUajpzUkfB3jW9+36XJeaedsrHLKeqE9cj61xVvpEdrbtcSQrLJJ96WV8Z+gr2/wDab8Cf8Ihr39v6JaCHRNQTZKik7Yp8YP0DDn65r5+1fVXudN+zEAlOFOccV5ToTpycVsz6CjjKc4Kb3SHTWkLQtLIlkyoDjy48Efjmq9/cgW9sI3OzYMD+lY8d5cJbm2ZhsJPFJZlpH2s2VQEgV0xpNPVnHWrqey3Ptr9iKzji+HepXwUCSa+8pjnqEUEf+hmvfq8B/YqaSPwVqlm7HCXEcoGOhZSP5KK9+rrTuro8mompO4lFFFBmFFFFACjpSUEgAknAHesvUvEOhabafa77V7KCDs7TLg/T1pgaw6ilrzPxb8a/BegiBYbp9UlmOAlrg7R6sTjFcLd/tFy3ls0Om+HltpZjthme58woCcBimzBPtmq5kty1FvZHsPxN0O38Q+BNY0q6QMsts5XjJVgMgj34r86vF3hrU9OuHSON7iLccOgzj8K9fuPjH8RX1G9s7nX5limbbtWNR8nPAOODyc46/lUCbbnEjc7hmvNxmL9k046nsYHAucXzHgYsr+R/ktrhm6EBCMV03hXQZYQtzep85OVjI6c969NvrGIKWCjkc1jSxAHgYrmePdSNkrHXHL1B3bue1fsteI9N0S51a11S9htIbhEZGlbau5c8Z+hNfSdndW95bR3VrNHPBIoZJEbcrD1Br8+dXkYaTLbo2HlwBzitLwz458X+HrMW2ka/fW1vGgVIkkOzIGN208dq7sNVvCzPKxtB+0bR98UV8xeDv2ktQt7WOPxNpUN4BKVa4gfy2CADkrghj17ivWPD/wAafh7rEKSLrS2bNn5bpTGRj36d66k09jgcWtz0WisWy8V+G7y1juoNbsWikXcpMyjI+hNFOzJPI/iX8U5tQlTS/D7PDZscTTEYeUf3R6CvneS6nksYY5pHfypGwGbOCTXWPn7QCcjn+HoK5y8tNoJ35UnkHtXE8VfRHoxwyiY11FI2XLNuJyKl0mZ1mA27SD696valp0x8vyQWwvNVUsJ1JOD9MZNRz83U1so9B+qRQ3RO4+WzdHA6Gr2l3s1tEIb+PayjiReVb/CqS6deyAgRnaex9asW9nqSrs2HHTBFc9alGorNnTQrypPQ05tQtGT5pk/Osa8vIMfucyHoABSvpGoSOSBHGPUio5dDmwTNeLGc8Ad/1rnhh4R3Z0zx02tIlDYzv5ty43dlHQVUu5lBKRvnsa2ZdBDAH7SXHtSR+HCxBKt+D5J/TiuyM4pWR5kozk7swGX/AEYg84OcetLaxLLEAhII9+tbtxokkS/c2p7mqcFhHA5bf17DpWqqq25Hs9dUEa3SoFE0mAMDnpRWgiNt4zj2X/69FT7aQ/Yx7HZp8yEnqkmAfqKo3qqJSu0YJI6elFFch2MWJRvROxArQgsoQGbnIFFFTIERp8oYADvUSR+YzEu457HFFFRHcJbEUtnEJQC0jAgjlzVy3sbXgmFSfeiitTnu7jJ4YQGURIBnsKqNDGiEqCCCcc0UUBdmfM5fUI7ZgDGyZIqqLaISPgEbW4ooqg6kkXMYwcfSiiikUf/Z");
                        
                        Product product = new Product(0, user.getId(), "Poupala", "Description", images, "deporte y ocio", desiredCategories, 15, 20);

//                       addProduct("f4493ed183abba6b096f3903a5fc3b64", new ProductDTO(product), new AddProductDataCallback() {
//                           @Override
//                           public void onError(ErrorBundle errorBundle) {
//
//                           }
//
//                           @Override
//                           public void onSuccess(ApiDTO<ProductId> returnParam) {
//                           }
//                       });
//
//
//                        product.setTitle("Poupala2");
//
//                        addProduct("f4493ed183abba6b096f3903a5fc3b64", new ProductDTO(product), new AddProductDataCallback() {
//                            @Override
//                            public void onError(ErrorBundle errorBundle) {
//
//                            }
//
//                            @Override
//                            public void onSuccess(ApiDTO<ProductId> returnParam) {
//                                addImagesProduct("f4493ed183abba6b096f3903a5fc3b64", returnParam.getContent().getProductId(), images.get(0), new BooleanDataCallback() {
//                                    @Override
//                                    public void onError(ErrorBundle errorBundle) {
//
//                                    }
//
//                                    @Override
//                                    public void onSuccess(Boolean returnParam) {
//
//                                    }
//                                });
//                            }
//                        });
//
//
//                        product.setTitle("Poupala3");
//
//                        addProduct("f4493ed183abba6b096f3903a5fc3b64", new ProductDTO(product), new AddProductDataCallback() {
//                            @Override
//                            public void onError(ErrorBundle errorBundle) {
//
//                            }
//
//                            @Override
//                            public void onSuccess(ApiDTO<ProductId> returnParam) {
//                                addImagesProduct("f4493ed183abba6b096f3903a5fc3b64", returnParam.getContent().getProductId(), images.get(0), new BooleanDataCallback() {
//                                    @Override
//                                    public void onError(ErrorBundle errorBundle) {
//
//                                    }
//
//                                    @Override
//                                    public void onSuccess(Boolean returnParam) {
//
//                                    }
//                                });
//                            }
//                        });
//
//
//                        product.setTitle("Poupala4");
//
//                        addProduct("f4493ed183abba6b096f3903a5fc3b64", new ProductDTO(product), new AddProductDataCallback() {
//                            @Override
//                            public void onError(ErrorBundle errorBundle) {
//
//                            }
//
//                            @Override
//                            public void onSuccess(ApiDTO<ProductId> returnParam) {
//                                addImagesProduct("f4493ed183abba6b096f3903a5fc3b64", returnParam.getContent().getProductId(), images.get(0), new BooleanDataCallback() {
//                                    @Override
//                                    public void onError(ErrorBundle errorBundle) {
//
//                                    }
//
//                                    @Override
//                                    public void onSuccess(Boolean returnParam) {
//
//                                    }
//                                });
//                            }
//                        });
//
//
//                        product.setTitle("Poupala5");
//
//                        addProduct("f4493ed183abba6b096f3903a5fc3b64", new ProductDTO(product), new AddProductDataCallback() {
//                            @Override
//                            public void onError(ErrorBundle errorBundle) {
//
//                            }
//
//                            @Override
//                            public void onSuccess(ApiDTO<ProductId> returnParam) {
//                                addImagesProduct("f4493ed183abba6b096f3903a5fc3b64", returnParam.getContent().getProductId(), images.get(0), new BooleanDataCallback() {
//                                    @Override
//                                    public void onError(ErrorBundle errorBundle) {
//
//                                    }
//
//                                    @Override
//                                    public void onSuccess(Boolean returnParam) {
//
//                                    }
//                                });
//                            }
//                        });
//
//
//                        product.setTitle("Poupala6");
//
//                        addProduct("f4493ed183abba6b096f3903a5fc3b64", new ProductDTO(product), new AddProductDataCallback() {
//                            @Override
//                            public void onError(ErrorBundle errorBundle) {
//
//                            }
//
//                            @Override
//                            public void onSuccess(ApiDTO<ProductId> returnParam) {
//                                addImagesProduct("f4493ed183abba6b096f3903a5fc3b64", returnParam.getContent().getProductId(), images.get(0), new BooleanDataCallback() {
//                                    @Override
//                                    public void onError(ErrorBundle errorBundle) {
//
//                                    }
//
//                                    @Override
//                                    public void onSuccess(Boolean returnParam) {
//
//                                    }
//                                });
//                            }
//                        });
//
//
//                        product.setTitle("Poupala7");
//
//                        addProduct("f4493ed183abba6b096f3903a5fc3b64", new ProductDTO(product), new AddProductDataCallback() {
//                            @Override
//                            public void onError(ErrorBundle errorBundle) {
//
//                            }
//
//                            @Override
//                            public void onSuccess(ApiDTO<ProductId> returnParam) {
//                                addImagesProduct("f4493ed183abba6b096f3903a5fc3b64", returnParam.getContent().getProductId(), images.get(0), new BooleanDataCallback() {
//                                    @Override
//                                    public void onError(ErrorBundle errorBundle) {
//
//                                    }
//
//                                    @Override
//                                    public void onSuccess(Boolean returnParam) {
//
//                                    }
//                                });
//                            }
//                        });


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
               // initDatabase();
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
    public void payTrueke(int product_id, String chat_id, int payment_id, String token, final VoidDataCallback voidDataCallback) {
        server.paytrueke(product_id,chat_id,payment_id,token).enqueue(new RetrofitErrorHandler<ApiDTO<Void>>(voidDataCallback) {
            @Override
            public void onResponse(ApiDTO<Void> body) {
                voidDataCallback.onSuccess(null);
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
    public void createTrueke(String chatID, String admintoken, final VoidDataCallback voidDataCallback) {
        server.createTrueke(admintoken,new TruekeChat(chatID)).enqueue(new RetrofitErrorHandler<ApiDTO<Void>>(voidDataCallback) {
            @Override
            public void onResponse(ApiDTO<Void> body) {
                voidDataCallback.onSuccess(null);
            }
        });
    }

    @Override
    public void voteTrueke(float rating,int product_id, String token, final VoidDataCallback voidDataCallback) {
        server.voteTrueke(token,product_id,new VoteDTO(rating)).enqueue(new RetrofitErrorHandler<ApiDTO<Void>>(voidDataCallback) {
            @Override
            public void onResponse(ApiDTO<Void> body) {
                voidDataCallback.onSuccess(null);
            }
        });
    }

    @Override
    public void deleteChat(String chatId, String token, final VoidDataCallback voidDataCallback) {
        server.deleteChat(chatId, token).enqueue(new RetrofitErrorHandler<ApiDTO<Void>>(voidDataCallback) {
            @Override
            public void onResponse(ApiDTO<Void> body) {
                voidDataCallback.onSuccess(null);
            }
        });
    }

}
