package trigues.com.data.dependencyinjection.qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by mbaque on 15/03/2017.
 */

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ForApp {}
