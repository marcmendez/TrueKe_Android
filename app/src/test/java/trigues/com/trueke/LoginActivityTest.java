package trigues.com.trueke;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;


import org.junit.Rule;
import org.junit.runner.RunWith;

import trigues.com.trueke.presenter.LoginPresenter;
import trigues.com.trueke.view.LoginActivity;
import trigues.com.trueke.view.impl.LoginActivityImpl;

import static org.mockito.Mockito.verify;

/**
 * Created by Eduard on 06/06/2017.
 */


public class LoginActivityTest {

        private LoginActivityImpl activity;
        @Mock
        LoginPresenter presenter;

        @Before
        public void setUp() throws Exception {
            MockitoAnnotations.initMocks(this);
            activity = new LoginActivityImpl();
            //activity.setPresenter(presenter);
        }


        @Test
        public void onLoginPressedTest() throws Exception {
            activity.onLoginPressed("A", "B");
            // No puc comprovar que es fa la crida a presenter perque presenter mai es crea en
            // loginACtivityImpl;
            verify(presenter).login("A", "B");
        }

        /*
        @Test
        public void testLeaveView() throws Exception {
            profileView.onDetachedFromWindow();
            verify(presenter).detachView();
        }

        @Test
        public void testReturnToView() throws Exception {
            reset(presenter);
            profileView.onAttachedToWindow();
            verify(presenter).attachView(profileView);
        }

        @Test
        public void testDisplay() throws Exception {
            UserProfile user = new UserProfile(USER);
            profileView.display(user);
            asserThat(profileView.textUsername.getText().toString()).isEqualTo(USER);
        }
    }*/

}
