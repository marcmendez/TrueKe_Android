package com.trigues;


import com.trigues.callback.DefaultCallback;
import com.trigues.entity.User;
import com.trigues.usecase.LoginUseCase;

import java.util.ArrayList;

/**
 * Created by mbaque on 15/03/2017.
 */

public interface RepositoryInterface {
    //TODO: Definir aqui les funcions del repositori

    void register(User param, LoginUseCaseCallback dataCallback);

    //TODO: Definir aqui els callbacks (interficies) que s'han de passar com a parametre a cada funció del repositori
    interface LoginUseCaseCallback extends DefaultCallback<Boolean>{}
    //Exemple:
    //interface TestCallback extends DefaultCallback<Classe que ha de retornar>
}
