package comics.marvel;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.mockito.Mock;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import android.content.Context;
import android.content.SharedPreferences;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {

    @Mock
    CommunicationsUtils mCommunicationsUtils;

    static String hash = "957b569e820f8b2521afdc1fada8da97";
    // http://gateway.marvel.com:80/v1/public/characters/1009220/comics?format=comic&formatType=comic&orderBy=title&ts=1487363956&apikey=6a7ed890b4b941a925202a5630d5b162&hash=957b569e820f8b2521afdc1fada8da97

    @Test
    public void validateMD5() {
        // Given a mocked Context injected into the object under test...
        CommunicationsUtils mock = org.mockito.Mockito.mock(CommunicationsUtils.class);

        //CommunicationsUtils myObjectUnderTest = new CommunicationsUtils();

        assertThat(mock.md5_mock(), is(hash));
    }




}