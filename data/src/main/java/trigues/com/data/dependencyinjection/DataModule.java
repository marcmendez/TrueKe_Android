package trigues.com.data.dependencyinjection;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import trigues.com.data.datasource.ApiInterface;
import trigues.com.data.datasource.impl.ApiDataSource;
import com.trigues.RepositoryInterface;
import trigues.com.data.repository.AppRepository;

/**
 * Created by inlab on 02/02/2017.
 */

@Module
public class DataModule {

    @Provides
    @Singleton
    public RepositoryInterface providesRepository(AppRepository repository){
        return repository;
    }


    @Provides
    @Singleton
    public ApiInterface providesTMDBDataSource(ApiDataSource apiDataSource){
        return apiDataSource;
    }
}
