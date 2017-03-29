package trigues.com.data.datasource;

import com.trigues.callback.DefaultCallback;
import com.trigues.entity.User;

/**
 * Created by mbaque on 15/03/2017.
 */

public interface ApiInterface {

    //TODO: Definir aqui les funcions del datasource

    void register(User param, Register datacallback );

    //TODO: Definir aqui els callbacks (interficies) que s'han de passar com a parametre a cada funció del datasource

    interface Register extends DefaultCallback<Boolean> {}
    //Exemple:
    //interface TestDATACallback extends DefaultCallback<Classe que ha de retornar>
}
