package trigues.com.trueke;

/**
 * Created by Eduard on 25/05/2017.
 */

import com.trigues.RepositoryInterface;
import com.trigues.entity.User;
import com.trigues.usecase.LoginUseCase;
import com.trigues.usecase.LogoutUseCase;
import com.trigues.usecase.RegisterUseCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import trigues.com.trueke.presenter.LoginPresenter;
import trigues.com.trueke.view.LoginActivity;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class LoginPresenterTest {

    /*
    private static List<Note> NOTES = Lists.newArrayList(new Note("Title1", "Description1"),

            new Note("Title2", "Description2"));

    private static List<Note> EMPTY_NOTES = new ArrayList<>(0);
    */

    /**
     * {@link ArgumentCaptor} is a powerful Mockito API to capture argument values and use them to
     * perform further actions or assertions on them.
     */
    @Captor
    private ArgumentCaptor<RegisterUseCase.RegisterUseCaseCallback> registerCallbackCaptor;
    @Captor
    private ArgumentCaptor<LoginUseCase.LoginUseCaseCallback> loginCallbackCaptor;




    @Mock
    private LoginActivity view;
    @Mock
    private LoginUseCase loginUseCase;
    @Mock
    private LogoutUseCase logoutUseCase;
    @Mock
    private RegisterUseCase registerUseCase;
    @Mock
    RepositoryInterface repository;

    private LoginPresenter loginPresenter;

    @Before
    public void setupLoginPresenter() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        loginPresenter = new LoginPresenter(view, loginUseCase, logoutUseCase, registerUseCase);
    }

    @Test
    public void register_showsSuccessMessage() {
        // When the presenter is asked to register an account
        loginPresenter.register("Nombre", "Apellidos", "", "", "", "");

        // Callback is captured and invoked with stubbed user
        verify(repository).register(any(User.class), (RepositoryInterface.BooleanCallback) registerCallbackCaptor.capture());

    }

    @Test
    public void login_showsSuccessMessage() {
        // When the presenter is asked to register an account
        loginPresenter.login("Usuari", "Contrasenya");

        // Callback is captured and invoked with stubbed user
        verify(repository).login(any(User.class), (RepositoryInterface.BooleanCallback) registerCallbackCaptor.capture());

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


