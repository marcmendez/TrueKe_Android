package trigues.com.trueke;

import com.trigues.RepositoryInterface;
import com.trigues.entity.ChatMessage;
import com.trigues.entity.Payment;
import com.trigues.entity.Product;
import com.trigues.entity.Shipment;
import com.trigues.entity.TruekeData;
import com.trigues.entity.User;
import com.trigues.exception.ErrorBundle;
import com.trigues.executor.ThreadExecutor;
import com.trigues.usecase.AcceptMatchUseCase;
import com.trigues.usecase.GetMatchMakingListUseCase;
import com.trigues.usecase.LoginUseCase;
import com.trigues.usecase.LogoutUseCase;
import com.trigues.usecase.RegisterUseCase;
import com.trigues.usecase.RejectMatchUseCase;
import com.trigues.usecase.ReportProductUseCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.List;

import trigues.com.trueke.presenter.LoginPresenter;
import trigues.com.trueke.presenter.MatchmakingPresenter;
import trigues.com.trueke.view.LoginActivity;
import trigues.com.trueke.view.MatchmakingActivity;
import trigues.com.trueke.view.impl.MatchmakingActivityImpl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * Created by Eduard on 26/05/2017.
 */

public class MatchmakingPresenterTest {

    /**
     * {@link ArgumentCaptor} is a powerful Mockito API to capture argument values and use them to
     * perform further actions or assertions on them.
     */





    @Captor
    private ArgumentCaptor<AcceptMatchUseCase.AcceptMatchCallback> acceptMatchCallbackCaptor;
    @Captor
    private ArgumentCaptor<RejectMatchUseCase.RejectMatchCallback> rejectMatchCallbackCaptor;
    @Captor
    private ArgumentCaptor<ReportProductUseCase.ReportProductCallback> reportProductCallbackCaptor;


    @Mock
    private MatchmakingActivity view;
    @Mock
    private GetMatchMakingListUseCase showProductsUseCase;
    @Mock
    private AcceptMatchUseCase acceptMatchUseCase;
    @Mock
    private RejectMatchUseCase rejectMatchUseCase;
    @Mock
    private ReportProductUseCase reportProductUseCase;
    @Mock
    private RepositoryInterface.VoidCallback voidCallback;
    @Mock
    private RepositoryInterface repository;
    @Mock
    private AcceptMatchUseCase.AcceptMatchCallback acceptMatchCallback;

    private  ThreadExecutor executor;

    private MatchmakingPresenter matchmakingPresenter;




    @Before
    public void setupMatchmakingPresenter() throws Exception {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);


        // Get a reference to the class under test
        matchmakingPresenter = new MatchmakingPresenter(view, showProductsUseCase,
                acceptMatchUseCase, rejectMatchUseCase, reportProductUseCase);

    /*
        when(interactor.getUserProfile()).thenReturn(Observable.just(new UserProfile()));
        presenter = new ProfilePresenter(interactor);
        presenter.attachView(view);
     */

    }


/*
    @Test
    public void acceptMatch_callsCallback() {
        Integer[] productID = new Integer[2];
        //productes[0] = any(Product.class).getId();
        //productes[1] = any(Product.class).getId();
        productID[0] = 1;
        productID[1] = 2;

        //acceptMatchUseCase.execute(productID, acceptMatchCallback);
        //when(interactor.getUserProfile()).thenReturn(Observable.just(new UserProfile()));
        //verify(acceptMatchCallback, times(1)).(null);
    }*/


    @Test
    public void acceptMatch_showsSuccessMessage() {
        // When the presenter is asked to register an accepted match
        Integer[] productID = new Integer[2];
        //productes[0] = any(Product.class).getId();
        //productes[1] = any(Product.class).getId();
        productID[0] = 1;
        productID[1] = 2;
        matchmakingPresenter.acceptedProduct(productID);

        // Callback is captured and invoked once
        //verify(repository, times(1)).acceptMatch(productes, voidCallback);
        verify(acceptMatchUseCase,times(1)).execute(productID, acceptMatchCallback);
        verify(acceptMatchUseCase,times(1)).execute(productID, new AcceptMatchUseCase.AcceptMatchCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {}
            @Override
            public void onSuccess(Void returnParam) {}
        });

        /*acceptMatchCallbackCaptor.getValue();
        if(acceptMatchCallbackCaptor.getValue() == null) {

        }
        else {
            assertEquals(acceptMatchCallbackCaptor.getValue(), true);
        }*/


    }

    @Test
    public void rejectMatch_showsSuccessMessage() {
        // When the presenter is asked to register a rejected match
        Integer[] productes = new Integer[2];
        //productes[0] = any(Product.class).getId();
        //productes[1] = any(Product.class).getId();
        productes[0] = 1;
        productes[1] = 2;
        matchmakingPresenter.rejectedProduct(productes);

        // Callback is captured and invoked
        // Potser la liem perque el test no sé si té manera de saber quins son els users propietaris dels productes.
        verify(repository, times(1)).rejectMatch(productes, (RepositoryInterface.VoidCallback) rejectMatchCallbackCaptor.capture());

        rejectMatchCallbackCaptor.getValue();

    }

    @Test
    public void reportProduct_showsSuccessMessage() {
        // When the presenter is asked to register a report;
        Integer[] userProdID = new Integer[2];
        //userProdID[0] = any(User.class).getId();
        //userProdID[1] = any(Product.class).getId();
        userProdID[0] = 1;
        userProdID[1] = 2;
        matchmakingPresenter.report(userProdID);

        // Callback is captured and invoked
        verify(repository, times(1)).reportProduct(userProdID, (RepositoryInterface.VoidCallback) reportProductCallbackCaptor.capture());

        reportProductCallbackCaptor.getValue();

    }


    /*
    ----- MÉS EXEMPLES -----
    @Test
    public void saveNote_emptyNoteShowsErrorUi() {
        // When the presenter is asked to save an empty note
        mAddNotesPresenter.saveNote("", "");

        // Then an empty not error is shown in the UI
        verify(mAddNoteView).showEmptyNoteError();
    }

    @Test
    public void takePicture_CreatesFileAndOpensCamera() throws IOException {
        // When the presenter is asked to take an image
        mAddNotesPresenter.takePicture();

        // Then an image file is created snd camera is opened
        verify(mImageFile).create(anyString(), anyString());
        verify(mImageFile).getPath();
        verify(mAddNoteView).openCamera(anyString());
    }

    @Test
    public void imageAvailable_SavesImageAndUpdatesUiWithThumbnail() {
        // Given an a stubbed image file
        String imageUrl = "path/to/file";
        when(mImageFile.exists()).thenReturn(true);
        when(mImageFile.getPath()).thenReturn(imageUrl);

        // When an image is made available to the presenter
        mAddNotesPresenter.imageAvailable();

        // Then the preview image of the stubbed image is shown in the UI
        verify(mAddNoteView).showImagePreview(contains(imageUrl));
    }

    @Test
    public void imageAvailable_FileDoesNotExistShowsErrorUi() {
        // Given the image file does not exist
        when(mImageFile.exists()).thenReturn(false);

        // When an image is made available to the presenter
        mAddNotesPresenter.imageAvailable();

        // Then an error is shown in the UI and the image file is deleted
        verify(mAddNoteView).showImageError();
        verify(mImageFile).delete();
    }

    @Test
    public void noImageAvailable_ShowsErrorUi() {
        // When the presenter is notified that image capturing failed
        mAddNotesPresenter.imageCaptureFailed();

        // Then an error is shown in the UI and the image file is deleted
        verify(mAddNoteView).showImageError();
        verify(mImageFile).delete();
    }*/
}