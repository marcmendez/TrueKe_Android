package trigues.com.data.dependencyinjection.qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by inlab on 26/01/2017.
 */

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ForApp {}
