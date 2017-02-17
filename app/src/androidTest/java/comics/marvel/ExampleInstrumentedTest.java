package comics.marvel;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import comics.marvel.Utils.CommunicationsUtils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    static String PUBLIC_KEY = "6a7ed890b4b941a925202a5630d5b162";
    static String PRIVATE_KEY = "0f1d0fdf46a0bf32f962b0b9997233c0395cdf8e";
    static String TS = "1487363956";
    static String hash = "957b569e820f8b2521afdc1fada8da97";


    private CommunicationsUtils mCommunications;


    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("comics.marvel", appContext.getPackageName());
    }


    @Before
    public void createCommunicationsUtils() {
        mCommunications = new CommunicationsUtils();
    }

    @Test
    public void validateMD5() {

        assertThat(mCommunications.md5(TS+""+PRIVATE_KEY+""+PUBLIC_KEY), is(hash));

    }

}
