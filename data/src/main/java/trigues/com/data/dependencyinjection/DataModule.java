package trigues.com.data.dependencyinjection;

import com.trigues.RepositoryInterface;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import trigues.com.data.datasource.ApiInterface;
import trigues.com.data.datasource.impl.ApiDataSource;
import trigues.com.data.repository.AppRepository;

/**
 * Created by mbaque on 15/03/2017.
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
